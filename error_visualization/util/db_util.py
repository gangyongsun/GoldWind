# coding:utf-8

import os
import sys

curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
# 先引入目录的上级
sys.path.append(rootPath)

from config.config import SQL_FOLDER
from util.common_util import mkdir


def generate_sql(dataframe, table_name, table_column):
    """
    根据dataframe生成SQL脚本
    :param dataframe: 数据源
    :param table_name: 表名
    :param table_column: 列名数组
    :return:
    """
    # DataFrame列编号数组
    column_index_range = range(0, dataframe.shape[1])
    # 最终数据部分
    items = ''
    for index, row in dataframe.iterrows():
        item = '(\''
        for i in column_index_range:
            item = item + row[i] + '\',\''
        item = item[:-3] + '\'),\n'
        items = items + item

    # 处理列数组，生成insert字段属性
    connector = ','
    columns = connector.join(table_column)

    # insert句子
    insert_stat = 'insert into ' + table_name + ' (' + columns + ') values\n'
    # 最终SQL脚本
    logic_exp_combine_1500_info_insert_data = insert_stat + items[:-2]
    mkdir(SQL_FOLDER)
    # 写SQL文件
    with open(SQL_FOLDER + table_name + '_insert_data.sql', 'w', encoding='utf-8') as f:
        f.write(str(logic_exp_combine_1500_info_insert_data))
        f.close()
