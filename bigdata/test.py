# python 3.x
import pandas as pd
fruit_list = [ ('Orange', 34, 'Yes' ) ,
             ('Mango', 24, 'No' ) ,
             ('banana', 14, 'No' ) ,
             ('Apple', 44, 'Yes' ) ,
             ('Pineapple', 64, 'No') ,
             ('Kiwi', 84, 'Yes')  ]
  
#Create a DataFrame object
df = pd.DataFrame(fruit_list, columns = 
                  ['Name' , 'Price', 'Stock']) 
# Get names of indexes for which column Stock has value No

indexNames = df[df.count(axis='columns')<5].index
#indexNames = df[ df['Stock'] == 'No' ].index
# Delete these row indexes from dataFrame
df.drop(indexNames , inplace=True)
print(df)
