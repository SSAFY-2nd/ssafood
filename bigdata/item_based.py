import pandas as pd
import surprise
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
import numpy as np
 

def makestoredump(dataframes):
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    store_group = stores_reviews.groupby(["store"])
    
    # 리뷰가 10개 이상인 요소만 사용
    review_10 = store_group.size()[store_group.size() > 10]
    review_10_store = review_10.to_frame().reset_index()['store']

    df = pd.DataFrame(columns=["store", "user", "score"])

    user_list = []
    store_list = []
    score_list = []

    for i in review_10_store:
        for k in range(stores_reviews[stores_reviews['store'] == i].id_x.count()):
            store_list.append(i)
            user_list.append(stores_reviews[stores_reviews['store'] == i].iloc[k].user)
            score_list.append(stores_reviews[stores_reviews['store'] == i].iloc[k].score)

    df["user"] = user_list
    df["store"] = store_list
    df["score"] = score_list

    return df.sort_values(by=['store'])

def recur_dictify(frame):
    if len(frame.columns) == 1:
        if frame.values.size == 1:
            return frame.values[0][0]
        return frame.values.squeeze()
    grouped = frame.groupby(frame.columns[0])
    data = {k: recur_dictify(g.iloc[:, 1:]) for k, g in grouped}
    return data


def dic_to_train(data):
    from surprise.model_selection import KFold

    bsl_options = {
        'method': 'als',
        'n_epochs': 5,
        'reg_u': 12,
        'reg_i': 5
    }
    algo = surprise.BaselineOnly(bsl_options)
    
    ax1 = data.plot.scatter(x='store',y='user',s=1,c='score')
    print(ax1)
    
    df_to_dict = recur_dictify(data)

    store_list = []  # 음식점 목록을 담을 리스트
    user_set = set()  # 유저 목록을 담을 set

    # store 수 만큼 반복
    for store_key in df_to_dict:
        store_list.append(store_key)

        for user_key in df_to_dict[store_key]:
            user_set.add(user_key)

    user_list = list(user_set)

    pd.to_pickle(pd.Series(user_list).to_frame(),
                 "../data/Item_based_user_list.pkl")
    pd.to_pickle(pd.Series(store_list).to_frame(),
                 "../data/Item_based_store_list.pkl")

    rating_dic = {
        "store_id": [],
        "user_id": [],
        "score": []
    }

    # store 수 만큼 반복
    for store_key in df_to_dict:
        for name_key in df_to_dict[store_key]:
            a1 = store_list.index(store_key)
            a2 = user_list.index(name_key)
            a3 = df_to_dict[store_key][name_key]

            rating_dic["store_id"].append(a1)
            rating_dic["user_id"].append(a2)
            rating_dic["score"].append(a3)

        df = pd.DataFrame(rating_dic)
    return df.sort_values(by=['store_id'])


def train(where, k):

    df = pd.read_pickle("../data/store_user_score.pkl")
    reader = surprise.Reader(rating_scale=(1, 5))

    col_list = ['store_id', 'user_id', 'score']
    data = surprise.Dataset.load_from_df(df[col_list], reader)
     

    # Train
    trainset = data.build_full_trainset()
    option = {'name': 'pearson'}
    algo = surprise.KNNBasic(sim_options=option)

    algo.fit(trainset)

    # 사용자의 음식점을 추천한다.
    # where = input('store id : ')
    print("\n")

    user_list = pd.read_pickle(
        "../data/Item_based_user_list.pkl")[0].tolist()
    store_list = pd.read_pickle(
        "../data/Item_based_store_list.pkl")[0].tolist()
    # user_list = dff.user.unique().tolist()
    # store_list = dff.store.unique().tolist()

    index = store_list.index(int(where))
    print('store_idx : ', index)
    print("\n")

    result = algo.get_neighbors(index, k=k)  # k=10
    print(where, "와 유사한 음식점은?")
    print(result)
    print("\n")

    # 음식점에 대한 유저를 추천한다.
    print(where, "를 평가한 당신에게 추천하는 친구:", "\n")
    recommend_user_list = []
    for r1 in result:
        max_rating = data.df[data.df["store_id"] == r1]["score"].max()
        user_id = data.df[(data.df["score"] == max_rating) & (
            data.df["store_id"] == r1)]["user_id"].values

        for user in user_id:
            recommend_user_list.append(user_list[user])
            # print(user_list[user])
    return recommend_user_list


def main():

    # 여기부터
    print("make dump file")
    #reviews = pd.read_pickle("../data/dump.pkl")
    #df = makestoredump(reviews)
    #pd.to_pickle(df, "../data/10_review_stores.pkl")
    #print("end of make dump file")

    print("dic to train")
    data = pd.read_pickle("../data/10_review_stores.pkl")
    frame = dic_to_train(data)
    pd.to_pickle(frame, "../data/store_user_score.pkl")
    print("end of dic to train")
    # 여기까지는 주기적으로 한번만 생성해주면 됨.

    recommend_user = train(149, 10)
    print(recommend_user)


if __name__ == "__main__":
    main()
