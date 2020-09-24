import json
import pandas as pd
import os
import shutil
import pymysql

mydb = pymysql.connect(
    
    host = "localhost",
    user = "root", 
    password = "root",
    database = "ssafood",
    charset='utf8mb4'

)

mycursor = mydb.cursor(pymysql.cursors.DictCursor)

DATA_DIR = "..\data"
DATA_FILE = os.path.join(DATA_DIR, "data.json")
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")

def import_data(data_path=DATA_FILE):
    try:
        with open(data_path, encoding="utf-8") as f:
            data = json.loads(f.read())
    except FileNotFoundError as e:
        print(f"`{data_path}` 가 존재하지 않습니다.")
        exit(1)
    
    stores = []  # 음식점 테이블
    reviews = []  # 리뷰 테이블
    
    count = 0
    for d in data:
        category = [c["category"] for c in d["category_list"]]
        menu = [ str(m["menu"])+":"+ str(m["price"]) for m in d["menu_list"]]
        sql = "INSERT INTO store (name, branch, area, tel, address, latitude, longtitude, category, menu) \
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)"
        val =  (d["name"],
                d["branch"],
                d["area"],
                d["tel"],
                d["address"],
                d["latitude"],
                d["longitude"],
                "|".join(category),
                "|".join(menu))
        mycursor.execute(sql,val)
        sql = "INSERT INTO bhour (store_id, type, week_type, mon, tue, wed, thu, fri, sat, sun, start_time, end_time, etc) \
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
        for b in d["bhour_list"]:
            val = (d["id"],
                   b["type"],
                   b["week_type"], 
                   b["mon"], 
                   b["tue"],
                   b["wed"],
                   b["thu"],
                   b["fri"],
                   b["sat"],
                   b["sun"],
                   b["start_time"],
                   b["end_time"],
                   b["etc"])
            mycursor.execute(sql,val)
        sql = "INSERT INTO review_info (store_id, writer_id, gender, born_year, total_score, content, reg_time) \
        VALUES (%s, %s, %s, %s, %s, %s, %s)"
        for review in d["review_list"]:
            r = review["review_info"]
            u = review["writer_info"]
            val = (d["id"], 
                    u["id"],
                    0 if u["gender"] == '남' else 1,
                    u["born_year"],
                    r["score"],  
                    r["content"],
                    r["reg_time"])
            mycursor.execute(sql,val)
        mydb.commit()

if __name__ == "__main__":
    import_data()
    mycursor.close()
    mydb.close()
