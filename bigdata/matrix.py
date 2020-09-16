import itertools
from collections import Counter
from parse import load_dataframes
import pandas as pd
import numpy as np


def show_user_store_score_matrix(dataframes):
    """
    Req. 1-4-1 유저-음식점 행렬 생성
    """

    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    # 필요한 값들만 추출
    stores_reviews = stores_reviews.loc[:,["user", "store", "score"]]

    # 배열로 변환
    users = stores_reviews["user"].drop_duplicates().to_numpy()
    stores = stores_reviews["store"].drop_duplicates().to_numpy()

    print(users)
    print(stores)

    # 두 배열로 빈 dataframe 만들기
    df = pd.DataFrame(index=stores, columns=users)
    collaborative_df = pd.DataFrame(index=stores, columns=stores)

    print(collaborative_df)
##    # 하나씩 값 넣어주기
##    for i in stores_reviews.index:
##        user = stores_reviews["user"][i]
##        store = stores_reviews["store"][i]
##        score = stores_reviews["score"][i]
##        df[user][store] = score
##
##    # datatype sparse로 변경
##    sdf = df.astype(pd.SparseDtype("int", np.nan))
##    print(sdf)
##
##    # 메모리 비교
##    print('df {:0.2f} bytes'.format(df.memory_usage().sum() / 1e3))
##    print('sdf {:0.2f} bytes'.format(sdf.memory_usage().sum() / 1e3))
    
def show_user_category_aveScore_matrix(dataframes, n = 100):
    """
    Req. 1-4-2 유저-카테고리 행렬 생성
    """

    stores = dataframes["stores"]

    # 모든 카테고리를 1차원 리스트에 저장합니다
    cate = stores.category.apply(lambda c: c.split("|"))
    cate = itertools.chain.from_iterable(cate)

    # 카테고리가 없는 경우 / 상위 카테고리를 추출합니다
    cate = filter(lambda c: c != "", cate)
    categories_count = Counter(list(cate))
    best_categories = categories_count.most_common()
    categroies_df = pd.DataFrame(best_categories, columns=["category", "count"]).sort_values(
        by=["count"], ascending=False
    )
    categories = categroies_df["category"].to_numpy()

    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    # 필요한 값들만 추출
    stores_reviews = stores_reviews.loc[:,["user", "category", "score"]]

    # 배열로 변환
    user_series = stores_reviews["user"].drop_duplicates().to_numpy()
    category_series = stores_reviews["category"].drop_duplicates().to_numpy()

    # 중복 카테고리 제거를 위한 split
    split_categories = stores_reviews["category"].str.split('|')

    idx = 0
    dict = {}

    # 중복 카테고리 제거
    for i in stores_reviews.index:
        user = stores_reviews["user"][i]
        score = stores_reviews["score"][i]
        category = stores_reviews["category"][i].split('|')
        for c in category:
            new_data = {"user":user, "category":c, "score":score}
            dict[idx] = new_data
            idx = idx+1

    category_df = pd.DataFrame.from_dict(dict, "index")
    category_df = category_df.groupby(["user", "category"]).mean().reset_index()

    df = pd.DataFrame(index=user_series, columns=categories)
    
    # 하나씩 값 넣어주기
    for i in category_df.index:
        user = category_df["user"][i]
        score = category_df["score"][i]
        category = category_df["category"][i]
        if category == "":
            continue
        df[category][user] = score

    # datatype sparse로 변경
    sdf = df.astype(pd.SparseDtype("float", np.nan))
    print(sdf)

    # 메모리 비교
    print('df {:0.2f} bytes'.format(df.memory_usage().sum() / 1e3))
    print('sdf {:0.2f} bytes'.format(sdf.memory_usage().sum() / 1e3))
    
    
def main():
    data = load_dataframes()
    show_user_store_score_matrix(data)
    # show_user_category_aveScore_matrix(data)


if __name__ == "__main__":
    main()
