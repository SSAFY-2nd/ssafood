import json
import pandas as pd
import os
import shutil
import math

DATA_DIR = "..\data"
DATA_FILE = os.path.join(DATA_DIR, "data.json")
DUMP_FILE = os.path.join(DATA_DIR, "dump_1.pkl")


def load_dataframes():
    return pd.read_pickle(DUMP_FILE)

def collaborative_filtering():
    data = load_dataframes()
    print(data)
    collaborative_df = pd.DataFrame(index=data.index, columns=data.index)
    
    # 협업 필터링

    for index_a in data.index:
        for index_b in data.index:
            if index_a != index_b:
                count = 0
                numerator = 0.0
                denominator_a = 0.0
                denominator_b = 0.0
                for columns in data.columns:
                    value_a = data[columns][index_a]
                    value_b = data[columns][index_b]
                    if not math.isnan(value_a) and not math.isnan(value_b):
                        count = count + 1
                        numerator += value_a*value_b
                        denominator_a += ( value_a ** 2 )
                        denominator_b += ( value_b ** 2 )
                if numerator != 0.0 and count > 3:
                    similarity = numerator / (( denominator_a ** 0.5 ) * (denominator_b ** 0.5))
                    print(similarity," : ",numerator," ",(( denominator_a ** 0.5 ) * (denominator_b ** 0.5)), count)
                    

    print(collaborative_df)

if __name__ == "__main__":
    collaborative_filtering()
