import sys
import tushare as ts

__author__ = 'Jian Shen'

def show_stock_history(code):
    return ts.get_hist_data(code)

code = sys.argv[1]
hist_data = show_stock_history(code)

print(hist_data.to_json(orient='split'))
