import itertools
from collections import Counter
from parse import load_dataframes
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
import folium
from datetime import datetime


def set_config():
    # 폰트, 그래프 색상 설정
    font_list = fm.findSystemFonts(fontpaths=None, fontext="ttf")
    if any(["notosanscjk" in font.lower() for font in font_list]):
        plt.rcParams["font.family"] = "Noto Sans CJK JP"
    else:
        if not any(["malgun" in font.lower() for font in font_list]):
            raise Exception(
                "Font missing, please install Noto Sans CJK or Malgun Gothic. If you're using ubuntu, try `sudo apt install fonts-noto-cjk`"
            )

        plt.rcParams["font.family"] = "Malgun Gothic"

    sns.set_palette(sns.color_palette("Spectral"))
    plt.rc("xtick", labelsize=6)


def show_store_categories_graph(dataframes, n=100):
    """
    Tutorial: 전체 음식점의 상위 `n`개 카테고리 분포를 그래프로 나타냅니다.
    """

    stores = dataframes["stores"]

    print(stores)
    # 모든 카테고리를 1차원 리스트에 저장합니다
    categories = stores.category.apply(lambda c: c.split("|"))
    categories = itertools.chain.from_iterable(categories)

    # 카테고리가 없는 경우 / 상위 카테고리를 추출합니다
    categories = filter(lambda c: c != "", categories)
    categories_count = Counter(list(categories))
    best_categories = categories_count.most_common(n=n)
    
    df = pd.DataFrame(best_categories, columns=["category", "count"]).sort_values(
        by=["count"], ascending=False
    )

    # 그래프로 나타냅니다
    chart = sns.barplot(x="category", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 카테고리 분포")
    plt.show()


def show_store_review_distribution_graph(dataframes, n=100):
    """
    Req. 1-3-1 전체 음식점의 리뷰 개수 분포를 그래프로 나타냅니다. 
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )

    # 필요한 값들만 추출
    stores_reviews = stores_reviews.loc[:,["store", "store_name", "score"]]
    scores_group = stores_reviews.groupby(["store", "store_name"])

    count = pd.Series(scores_group.count().sort_values(["score"], ascending=[False])["score"], name="review_count")

    # n개만큼 추출
    max_reviews = count.head(n=n).reset_index()

    df = pd.DataFrame(max_reviews, columns=["store_name", "review_count"]).sort_values(
        by=["review_count"], ascending=False
    )
    # 그래프로 나타냅니다
    chart = sns.barplot(x="store_name", y="review_count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 카테고리 분포")
    plt.show()


def show_store_average_ratings_graph(dataframes, n=100, min_reviews=30):
    """
    Req. 1-3-2 각 음식점의 평균 평점을 그래프로 나타냅니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )

    # 필요한 값들만 추출
    stores_reviews = stores_reviews.loc[:,["store", "store_name", "score"]]
    scores_group = stores_reviews.groupby(["store", "store_name"])

    # 그룹 평균 계산
    scores = scores_group.mean()

    # 최소 리뷰갯수보다 작은 index 삭제
    scores_count = scores_group.count()
    count_idx = scores_count[scores_count["score"] < min_reviews].index
    scores = scores.drop(count_idx)

    # n개만큼 추출
    scores = scores.sort_values(["score"], ascending=[False])
    scores = scores.head(n=n).reset_index()

    df = pd.DataFrame(scores, columns=["store_name", "score"]).sort_values(
        by=["score"], ascending=False
    )
    # 그래프로 나타냅니다
    chart = sns.barplot(x="store_name", y="score", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 카테고리 분포")
    plt.show()


def show_user_review_distribution_graph(dataframes, n=100):
    """
    Req. 1-3-3 전체 유저의 리뷰 개수 분포를 그래프로 나타냅니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    # 필요한 값들만 추출
    stores_reviews = stores_reviews.loc[:,["user", "score"]]

    # 한 유저의 리뷰 갯수 구하기
    users_group = stores_reviews.groupby(["user"]).count().reset_index()

    # score(여기서는 리뷰 갯수)를 한번더 그룹화 하여 리뷰갯수별 유저수를 구한다
    review_count = users_group.groupby(["score"]).count().head(n=n).reset_index()
    review_count.rename(columns = {"user":"인원 수", "score":"리뷰 갯수"}, inplace=True)

    df = pd.DataFrame(review_count, columns=["리뷰 갯수", "인원 수"]).sort_values(
        by=["리뷰 갯수"], ascending=True
    )
    # 그래프로 나타냅니다
    chart = sns.barplot(x="인원 수", y="리뷰 갯수", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("리뷰 분포")
    plt.show()


def calculate_age(x):
    now_year = datetime.today().year
    return now_year-x+1 

def show_user_age_gender_distribution_graph(dataframes):
    """
    Req. 1-3-4 전체 유저의 성별/나이대 분포를 그래프로 나타냅니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    print(stores_reviews[stores_reviews["id_x"] == 121492])



def show_stores_distribution_graph(dataframes):
    """
    Req. 1-3-5 각 음식점의 위치 분포를 지도에 나타냅니다.
    """
    stores = dataframes["stores"]
    print(stores)
    
    center = [37.541, 126.986]

    m = folium.Map(location=center, zoom_start=15)

    for i in stores.index:
        store_name = stores["store_name"][i]
        latitude = stores["latitude"][i]
        longitude = stores["longitude"][i]
        if(latitude == None):
           continue
        folium.Marker(location=[float(latitude),float(longitude)],popup=store_name,icon=folium.Icon(color='red',icon='star')).add_to(m)
        # 저장 및 로딩이 시간이 오래걸려서 200개만 저장
        if(i == 200):
            print(i)
            m.save('map.html')
            break
    m.save('map.html')
    print("save success")


def main():
    set_config()
    data = load_dataframes()
#    show_store_categories_graph(data)
#    show_store_review_distribution_graph(data)
#    show_store_average_ratings_graph(data)
#    show_user_review_distribution_graph(data)
    show_user_age_gender_distribution_graph(data)
#    show_stores_distribution_graph(data)



if __name__ == "__main__":
    main()
