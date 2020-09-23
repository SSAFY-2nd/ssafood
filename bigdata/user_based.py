import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import os
import surprise
from sqlalchemy import create_engine

DATA_DIR = "..\data"
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")


def dump_dataframes(dataframes):
    pd.to_pickle(dataframes, DUMP_FILE)


def makeuserdump(dataframes):
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    df_reviews = stores_reviews
    print(df_reviews)
    df_r_group = df_reviews.groupby(["user"])
    review_10 = df_r_group.size()[df_r_group.size() > 10]
    review_10_user = review_10.to_frame().reset_index()['user']

    df = pd.DataFrame(columns=["user", "store", "score"])

    user_list = []
    store_list = []
    score_list = []

    for i in review_10_user:
        for k in range(df_reviews[df_reviews['user'] == i].id_x.count()):
            user_list.append(i)
            store_list.append(
                df_reviews[df_reviews['user'] == i].iloc[k].store)
            score_list.append(
                df_reviews[df_reviews['user'] == i].iloc[k].score)

    df["user"] = user_list
    df["store"] = store_list
    df["score"] = score_list

    return df.sort_values(by=['user'])


def dic_to_train(data):
    df_to_dict = recur_dictify(data)

    name_list = []  # user list
    store_set = set()  # store set

    # Iterate as # of users
    for user_key in df_to_dict:
        name_list.append(user_key)

        for sto_key in df_to_dict[user_key]:
            store_set.add(sto_key)

    store_list = list(store_set)
    pd.to_pickle(pd.Series(name_list).to_frame(),
                 "../data/user_based_name_list.pkl")
    pd.to_pickle(pd.Series(store_list).to_frame(),
                 "../data/user_based_store_list.pkl")

    rating_dic = {
        "user_id": [],
        "store_id": [],
        "score": []
    }
    # Iterate as # of users
    for name_key in df_to_dict:
        for sto_key in df_to_dict[name_key]:
            a1 = name_list.index(name_key)
            a2 = store_list.index(sto_key)
            a3 = df_to_dict[name_key][sto_key]

            rating_dic["user_id"].append(a1)
            rating_dic["store_id"].append(a2)
            rating_dic["score"].append(a3)

    df = pd.DataFrame(rating_dic)
    return df.sort_values(by=['user_id'])


# Change to dictionary
def recur_dictify(frame):
    if len(frame.columns) == 1:
        if frame.values.size == 1:
            return frame.values[0][0]
        return frame.values.squeeze()
    grouped = frame.groupby(frame.columns[0])
    d = {k: recur_dictify(g.iloc[:, 1:]) for k, g in grouped}
    return d


def train(who, k):

    df = pd.read_pickle("../data/dic_to_train.pkl")
    reader = surprise.Reader(rating_scale=(1, 5))
    col_list = ['user_id', 'store_id', 'score']
    data = surprise.Dataset.load_from_df(df[col_list], reader)
    
    # benchmark = []
    # from surprise import SVD, SVDpp, SlopeOne, NMF, NormalPredictor, KNNBasic, KNNBaseline, KNNWithMeans, KNNWithZScore, BaselineOnly, CoClustering
    # # from sklearn.model_selection import cross_validate 사이킷런의 크로스벨리데이션이 아니다.
    # from surprise.model_selection import cross_validate
    
    
    # benchmark = []
    # # 모든 알고리즘을 literate화 시켜서 반복문을 실행시킨다.
    # for algorithm in [SVD(), SVDpp(), SlopeOne(), NMF(), NormalPredictor(), KNNBaseline(), KNNBasic(), KNNWithMeans(), KNNWithZScore(), BaselineOnly(), CoClustering()]:
        
    #     # 교차검증을 수행하는 단계.
    #     results = cross_validate(algorithm, data, measures=['RMSE'], cv=3, verbose=False)
        
    #     # 결과 저장과 알고리즘 이름 추가.
    #     tmp = pd.DataFrame.from_dict(results).mean(axis=0)
    #     tmp = tmp.append(pd.Series([str(algorithm).split(' ')[0].split('.')[-1]], index=['Algorithm']))
    #     benchmark.append(tmp)
        
    # print(pd.DataFrame(benchmark).set_index('Algorithm').sort_values('test_rmse') )
    
    # Train
    trainset = data.build_full_trainset()
    option = {'name': 'pearson'}
        
    algo = surprise.KNNBasic(sim_options=option)
    
    algo.fit(trainset)

    name_list = pd.read_pickle(
        "../data/user_based_name_list.pkl")[0].tolist()
    store_list = pd.read_pickle(
        "../data/user_based_store_list.pkl")[0].tolist()
    # name_list = dff.user.unique().tolist()
    # store_list = dff.store.unique().tolist()

    index = name_list.index(int(who))

    neighbors = algo.get_neighbors(index, k=k)  # k=5

    # Recommend store to user
    recommend_store_list = []
    
    for i in neighbors:
        max_rating = data.df[data.df["user_id"] == i]["score"].max()
        
        store = data.df[(data.df["score"] == max_rating) & (
            data.df["user_id"] == i)]["store_id"].values
        for idx in store:
            recommend_store_list.append(store_list[idx])
    return recommend_store_list

def dump_dataframes(dataframes):
    pd.to_pickle(dataframes, DUMP_FILE)
def load_dataframes():
    return pd.read_pickle(DUMP_FILE)

def main():

    dataframes = load_dataframes()
#    df = makeuserdump(dataframes)
#    pd.to_pickle(df, "../data/over_10_review_peoples.pkl")

#    data = pd.read_pickle("../data/over_10_review_peoples.pkl")
#    frame = dic_to_train(data)
#    pd.to_pickle(frame, "../data/dic_to_train.pkl")

    recommend_store = train(345, 5)
    
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    print(stores_reviews[stores_reviews["user"]==345])
    scores_group = stores_reviews.groupby(["store", "store_name"]).mean()
    recommend_df = scores_group.loc[recommend_store].sort_values(["score"], ascending=[False]).head(10)
    print(recommend_df[["score"]])
    # for idx in recommend_store:
    #     print(scores_group.loc[idx])


if __name__ == "__main__":
    main()