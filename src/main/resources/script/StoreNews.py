from pymongo import MongoClient
import json
import tushare as ts
import time
from apscheduler.schedulers.blocking import BlockingScheduler

__author__ = 'Jian Shen'

# 连接mongodb数据库
client = MongoClient("mongodb://127.0.0.1/27017")
# 指定数据库名称
db = client.stock

scheduler = BlockingScheduler()

# 周一到周五早8-晚6，每隔十分钟调用次接口
@scheduler.scheduled_job('cron', day_of_week='mon-fri',  hour='8-20',minute='0,10,20,30,40,50')
def store_news():
    # 获取新浪股吧的最新新闻
    sina_news = ts.guba_sina(show_content=True)

    # 防止数据未更新插入重复数据 2018/04/22 0:33
    # TODO 集合为空时的容错处理
    for i in db.news.sina.find().sort([("_id", -1)]).limit(1):
        for j in sina_news[:6][-1:].get("title"):
            if i.get("title") != j:
                db.news.sina.insert(json.loads(sina_news[:6].to_json(orient='records')))

    # 获取前6条最新的即时新闻
    immediate_news = ts.get_latest_news(top=6, show_content=True)

    for i in db.news.immediate.find().sort([("_id", -1)]).limit(1):
        for j in immediate_news[-1:].get("title"):
            if i.get("title") != j:
                db.news.immediate.insert(json.loads(immediate_news.to_json(orient='records')))

    # 获取个股信息地雷数据
    mines_news = ts.get_notices()

    if not mines_news is None:
        db.news.mines.insert(json.loads(mines_news.to_json(orient='records')))

scheduler.start()

