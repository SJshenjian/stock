import tushare as ts
from sqlalchemy import create_engine
from apscheduler.schedulers.blocking import BlockingScheduler

__author__ = 'Jian Shen'

scheduler = BlockingScheduler()

# 周一到周五的凌晨更新成分股
@scheduler.scheduled_job('cron', day_of_week='mon-fri',  hour='0',minute='0')
def save_component_stock():
    # 沪深300成份及权重
    hs300_classified = ts.get_hs300s()

    # 上证50成份股
    sz50_classified = ts.get_sz50s()

    # 中证500成份股
    zz500_classified = ts.get_zz500s()

    # 存入mysql数据库
    engine = create_engine('mysql://root:852100@127.0.0.1/stock?charset=utf8')

    # 数据定时存入mysql
    hs300_classified.to_sql('hs300_classify', engine, if_exists='replace')
    sz50_classified.to_sql('sz50_classify', engine, if_exists='replace')
    zz500_classified.to_sql('zz500_classify', engine, if_exists='replace')

scheduler.start()