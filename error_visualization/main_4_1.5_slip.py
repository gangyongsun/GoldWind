# coding:utf-8

import os
import re
import csv
import pandas as pd

from util.common_util import get_file_content
from util.common_util import mkdir
from util.common_util import walk_file
from util.common_util import write_file
from util.common_util import change_ext

from util.db_util import generate_sql

from config.config import TMP_FOLDER
from config.config import MAIN_FOLDER
from config.config import CSV_FOLDER


def change_line_and_add_comma():
    """
    对以"(exps_code"开头的进行换行处理，对以"(EQ\n|(OR\n"开头的进行删除换行处理，去掉空行
    :return:
    """
    # 遍历主文件夹
    slip_files = walk_file(path=MAIN_FOLDER, start_flag=None, end_flag='.txt.tmp1')

    # 遍历经过shell处理后的文件并处理
    for file in slip_files:
        content = get_file_content(file)
        # 对以"(exps_code"开头的进行换行处理
        content = content.replace('(exps_code', '\n(exps_code')
        # 对以"(EQ\n|(OR\n"开头的进行删除换行处理
        content = content.replace('(EQ \n', '(EQ').replace('(OR \n', '(OR').replace('(AND \n', '(AND')
        # # 对OR|AND|EQ表达式末尾的逗号进行处理,不起作用？？？
        # content = content.replace('), )', '))').replace(',\n', '')
        # 去掉空行
        content = content.strip()

        # 正则表达式
        p1 = r"\(exps_code\s* [A-Za-z]*[0-9]*\)"
        # 编译这段正则表达式
        pattern1 = re.compile(p1)

        # 匹配结果
        result_list = pattern1.findall(content)
        # 去重
        result_list = list(set(result_list))

        for item in result_list:
            item_pattern = str(item).replace('(', '\(').replace(')', '\)')
            content = re.sub(item_pattern, item + ',', content)

        # 新文件名
        newfile = change_ext(file, '.tmp2')
        # 写入新文件
        write_file(newfile, content, 'utf-8')


def redefine_ext(new_ext):
    """
    对文件的扩展名进行替换
    :param new_ext:新扩展名
    :return:
    """
    # 遍历主文件夹，获取扩展名为.txt.tmp2的文件列表
    dealed_files = walk_file(path=MAIN_FOLDER, start_flag=None, end_flag='.txt.tmp2')
    # 遍历经过shell处理后的文件并处理
    for file in dealed_files:
        # 文件夹名
        folder_name = os.path.dirname(file)
        # 文件名
        file_name = os.path.basename(file)
        # 去掉扩展名的文件名
        file_name_without_ext = os.path.splitext(file_name)[0]
        # 新文件名（全路径名）
        new_file = folder_name + '/' + file_name_without_ext + new_ext
        os.rename(file, new_file)


def merge_csv_files():
    """
    合并中间csv文件(包括对第一列进行代码提取）
    :return:
    """
    # 遍历主文件夹，获取扩展名为.csv的文件列表
    csv_files = walk_file(path=MAIN_FOLDER, start_flag=None, end_flag='.csv')
    mkdir(TMP_FOLDER)
    # 最终合并完的文件名，追加方式打开
    final_csv_file = TMP_FOLDER + 'temp.csv'
    if os.path.exists(final_csv_file):
        # 删除原始文件，防止累计旧数据
        os.remove(final_csv_file)
    result_csv_file_handler = open(final_csv_file, 'a+')
    for csv_file in csv_files:
        with open(csv_file, 'r') as csv_file_handler:
            reader = csv.reader(csv_file_handler)
            for row in reader:
                tmp = re.split('[ )]', row[0])
                new_row = tmp[1] + ',' + str(row[1]).strip() + '\n'
                result_csv_file_handler.write(new_row)
    result_csv_file_handler.close()


def generate_sql_from_csv(csv_file):
    """
    读取csv文件生成SQL脚本
    :param csv_file: CSV文件
    :return:
    """
    columns_values = ['code_index', 'code_content']
    tmp_lst = []
    with open(csv_file, 'r') as f:
        reader = csv.reader(f)
        for row in reader:
            tmp_lst.append(row)
    df = pd.DataFrame(tmp_lst, columns=columns_values)
    # 表名
    table_name = 'logic_exp_unit_1500_info'
    # 表列名
    table_column = ['logic_exp_combine_no', 'logic_exp_combine']
    generate_sql(df, table_name, table_column)
    # 生成csv文件
    df.to_csv(CSV_FOLDER + table_name + '.csv')


# 2:通过Python程序处理中间文件生成中间文件*.txt.tmp2
# change_line_and_add_comma()

# 5:对所有文件进行扩展名替换
# redefine_ext('.csv')

# 6:csv文件并合并
merge_csv_files()

# 8：读取CSV生成SQL脚本
generate_sql_from_csv(TMP_FOLDER + 'temp.csv')
