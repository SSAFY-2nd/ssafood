from bs4 import BeautifulSoup as bs
from urllib.request import urlopen
from urllib.parse import quote_plus
import json
import pandas as pd
import os
import shutil
import pymysql


DATA_DIR = "../data"
DATA_FILE = os.path.join(DATA_DIR, "data.json")
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")

baseUrl = 'https://search.naver.com/search.naver?where=image&sm=tab_jum&query='

def import_data(data_path=DATA_FILE):
    try:
        with open(data_path, encoding="utf-8") as f:
            data = json.loads(f.read())
    except FileNotFoundError as e:
        print(f"`{data_path}` 가 존재하지 않습니다.")
        exit(1)
    

    count = 0
    for d in data:
        if '/' in d["name"] :
            continue
        elif '\\' in d["name"] :
            continue
        elif '%' in d["name"] :
            continue
        
        else :
            print(d["name"])
            plusUrl = d["name"]
            crawl_num = 3
            
            url = baseUrl + quote_plus(plusUrl) # 한글 검색 자동 변환
            html = urlopen(url)
            soup = bs(html, "html.parser")
            img = soup.find_all(class_='_img')
            
            n = 1
            for i in img:
                print(n)
                imgUrl = i['data-source']
                with urlopen(imgUrl) as f:
                    with open('./images/'+d["name"] + str(n)+'.jpg','wb') as h: # w - write b - binary
                        img = f.read()
                        h.write(img)
                n += 1
                if n > crawl_num:
                    break

            count = count +1
            if count == 2 :
                break

if __name__ == "__main__":
    import_data()