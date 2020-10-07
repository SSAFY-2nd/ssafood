#!/usr/bin/env python
# -*- coding: utf-8 -*-

import numpy as np
import pandas as pd
import konlpy
from konlpy.tag import Hannanum
from konlpy.tag import Okt
import random
import math
import copy
from sklearn.decomposition import TruncatedSVD
from sklearn.preprocessing import Normalizer
from sklearn.feature_extraction.text import TfidfVectorizer
import pickle
from sklearn.cluster import KMeans
import re


def sub_special(s):
    return re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣0-9a-zA-Z ]','',s)

STOP_WORDS = ['의','가','이','은','들','는','좀','잘','걍','과','도','를','으로','자','에','와','한','하다']
def morph_and_stopword(s):
    okt = Okt()
    token_ls = []
    #형태소 분석
    tmp = okt.morphs(s, stem=True)
    
    #불용어 처리
    for token in tmp:
      if token not in STOP_WORDS:
        token_ls.append(token)
    return s

def getStore(id):
    with open("../data/cosine_sim_data.pickle","rb") as fr:
        data = pickle.load(fr)
    
    dataframes = pd.read_pickle("../data/dump.pkl")
    review_df = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    review_df = review_df[review_df['category'].str.contains('소고기')]
    review_df = review_df[['store_name','content', 'id_x']]
    
    cosine_sim = data
    # 모든 영화에 대해서 해당 영화와의 유사도를 구하기
    sim_scores = list(enumerate(cosine_sim[id]))

    # 유사도에 따라 영화들을 정렬
    sim_scores = sorted(sim_scores, key=lambda x:x[1], reverse = True)

    # 가장 유사한 10개의 영화를 받아옴
    sim_scores = sim_scores[1:11]
    
    # 가장 유사한 10개 영화의 인덱스 받아옴
    store_indices = [i[0] for i in sim_scores]
    print(store_indices)
    
    #기존에 읽어들인 데이터에서 해당 인덱스의 값들을 가져온다. 그리고 스코어 열을 추가하여 코사인 유사도도 확인할 수 있게 한다.
    result_df = review_df.iloc[store_indices].copy()
    print(result_df)
    result_df['score'] = [i[1] for i in sim_scores]
    
    # 읽어들인 데이터에서 줄거리 부분만 제거, 제목과 스코어만 보이게 함
    del result_df['content']
    ret = []
    
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    store_group = stores_reviews.groupby(["store"]).mean()
    for i in result_df.index:
        tmp = {}
        idx = result_df.loc[i][1]
        tmp['store_id'] = str(idx)
        tmp['name'] = str(result_df.loc[i][0])
        tmp['score'] = str(store_group.loc[idx][3])
        ret.append(tmp)
    return ret

def main():
    dataframes = pd.read_pickle("../data/dump.pkl")
    review_df = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    print(review_df['store_name'])
    print(review_df[review_df['category'].str.contains('소고기')])
    review_df = review_df[review_df['category'].str.contains('소고기')]
    review_df = review_df[['store_name','content', 'id_x']]

    for i in review_df.index:
        s = sub_special(review_df['content'][i])
        review_df['content'][i] = morph_and_stopword(s)
    
    tfidf = TfidfVectorizer()
    
    tfidf_matrix = tfidf.fit_transform(review_df.content)
    # 줄거리에 대해서 tf-idf 수행
    tfidf_dict = tfidf.get_feature_names()
    """코사인 유사도 구하기"""
    from sklearn.metrics.pairwise import linear_kernel
    cosine_sim = linear_kernel(tfidf_matrix, tfidf_matrix)
    print(cosine_sim)
    with open("../data/cosine_sim_data.pickle","wb") as fw:
        pickle.dump(cosine_sim, fw)
    
    
if __name__ == "__main__":
    main()
    print(getStore(360467))