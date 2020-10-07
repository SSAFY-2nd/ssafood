from parse import load_dataframes
import pandas as pd
import shutil


def sort_stores_by_score(dataframes, n=20, min_reviews=30):
    """
    Req. 1-2-1 각 음식점의 평균 평점을 계산하여 높은 평점의 음식점 순으로 `n`개의 음식점을 정렬하여 리턴합니다
    Req. 1-2-2 리뷰 개수가 `min_reviews` 미만인 음식점은 제외합니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    
    scores_group = stores_reviews.groupby(["store", "store_name"])
    print(scores_group.mean())
    scores = scores_group.mean()
    
    scores_count = scores_group.count()
    print(scores_count)
    count_idx = scores_count[scores_count["score"] < min_reviews].index

    scores = scores.drop(count_idx)
    scores = scores.sort_values(["score"], ascending=[False])
    
    return scores.head(n=n).reset_index()


def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    
    scores_group = stores_reviews.groupby(["store", "store_name"])
    scores = scores_group.mean()
    count = pd.Series(scores_group.count().sort_values(["score"], ascending=[False])["id_x"], name="review_count")

    max_reviews = pd.concat([scores, count], axis=1).sort_values(["review_count"], ascending=[False])
    return max_reviews.head(n=n).reset_index()


def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    print(stores_reviews)
    # 유저로 그룹화 해줌
    users_group = stores_reviews.groupby(["user"])
    users_group = users_group.size()[users_group.size()>10]
    
    print(users_group)
    # 갯수를 세면 리뷰 갯수가 나옴 id_x를 이용
    users = users_group.count().sort_values(["id_x"], ascending=[True])
    return users.head(n=n).reset_index()


def main():
    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w
##
##    stores_most_scored = sort_stores_by_score(data)

##    print("[최고 평점 음식점]")
##    print(f"{separater}\n")
##    for i, store in stores_most_scored.iterrows():
##        print(
##            "{rank}위: {store}({score}점)".format(
##                rank=i + 1, store=store.store_name, score=store.score
##            )
##        )
##    print(f"\n{separater}\n\n")
##
##
##    stores_most_reviewed = get_most_reviewed_stores(data)
##    
##    print("[최대 리뷰 음식점]")
##    print(f"{separater}\n")
##    for i, store in stores_most_reviewed.iterrows():
##        print(
##            "{rank}위: {store}({count}개)".format(
##                rank=i + 1, store=store.store_name, count=store.review_count
##            )
##        )
##        
##    print(f"\n{separater}\n\n")
##
    users_most_reviewed = get_most_active_users(data)
    
    print("[최대 리뷰 작성자]")
    print(f"{separater}\n")
    for i, store in users_most_reviewed.iterrows():
        print(
            "{rank}위: {user}회원 ({count}개)".format(
                rank=i + 1, user=store.user, count=store.id_x
            )
        )
        
    print(f"\n{separater}\n\n")


if __name__ == "__main__":
    print(4)
    main()
