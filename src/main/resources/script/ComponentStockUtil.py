import sys
import tushare as ts

__author__ = 'Jian Shen'

def  list_component_stock(component_name):
    if 'hs300' == component_name:
        return ts.get_hs300s()

    if 'zz500' == component_name:
        return ts.get_zz500s()

    if 'sz50' == component_name:
        return ts.get_sz50s()

component_name = sys.argv[1]

df = list_component_stock(component_name)

print(df.to_json(orient='records'))