# coding:utf-8

from util.common_util import walk_file
from util.db_util import generate_sql
from config.config import MAIN_FOLDER_2

from util.excel_util import excel_data_process

from config.config import CSV_FOLDER


def process_data_1(excel_files):
    """
    生成【关系】表入库SQL脚本数据
    :param excel_files: excel文件名(全路径)数组
    :return:
    """
    # 列名称列表
    columns_name = ['机型', '风水冷类别', '主控程序版本号', '变流程序版本号', '中央监控版本', '故障代码', '需要文件类型', '变量逻辑表达式编号', '变量表达式对应故障原因编号', '变量表达式对应处理建议编号']
    # Excel的sheet名
    sheet_name = 'main'
    # 执行处理逻辑
    result_data = excel_data_process(excel_files, columns_name, sheet_name)

    # 删除空列、指定空值的行,重排序
    result_data = result_data.dropna(axis=1, how='all').dropna(axis=0, subset=['变量逻辑表达式编号', '变量表达式对应故障原因编号', '变量表达式对应处理建议编号']).reset_index(drop=True)

    # 表名
    table_name = 'logic_exp_combine_1500_info'
    # 表列名
    table_column = ['model_type', 'control_version', 'err_no', 'logic_exp_combine_no', 'err_cause_no', 'advise_no']
    # 调用函数生成SQL入库脚本
    generate_sql(result_data, table_name, table_column)
    # 生成csv文件
    result_data.to_csv(CSV_FOLDER + table_name + '.csv')


def process_data_2(excel_files):
    """
    生成【故障】表入库SQL脚本数据
    :param excel_files: excel文件名(全路径)数组
    :return:
    """
    # 列名称列表
    columns_name = ['机型', '故障原因编号ALL', '故障原因ALL']
    # Excel的sheet名
    sheet_name = 'cause'
    # 执行处理逻辑
    result_data = excel_data_process(excel_files, columns_name, sheet_name)

    # 删除有空值的行,重排序
    result_data = result_data.dropna(axis=0, how='any').reset_index(drop=True)

    # 表名
    table_name = 'errdata_cause_detailed_info'
    # 表列名
    table_column = ['model_type', 'err_cause_no', 'err_cause_exp_cn']
    # 调用函数生成SQL入库脚本
    generate_sql(result_data, table_name, table_column)
    # 生成csv文件
    result_data.to_csv(CSV_FOLDER + table_name + '.csv')


def process_data_3(excel_files):
    """
    生成【处理建议】表入库SQL脚本数据
    :param excel_files: excel文件名(全路径)数组
    :return:
    """
    # 列名称列表
    columns_name = ['机型', '处理建议编号ALL', '处理建议ALL']
    # Excel的sheet名
    sheet_name = 'advise'
    # 执行处理逻辑
    result_data = excel_data_process(excel_files, columns_name, sheet_name)
    # 删除有空值的行,重排序
    result_data = result_data.dropna(axis=0, how='any').reset_index(drop=True)

    # 表名
    table_name = 'errdata_advise_detailed_info'
    # 表列名
    table_column = ['model_type', 'advise_no', 'advise_exp_cn']
    # 调用函数生成SQL入库脚本
    generate_sql(result_data, table_name, table_column)
    # 生成csv文件
    result_data.to_csv(CSV_FOLDER + table_name + '.csv')


def process_data_4(excel_files):
    """
    生成【表达式】表入库SQL脚本数据
    :param excel_files: excel文件名(全路径)数组
    :return:
    """
    # 列名称列表
    columns_name = ['ID', 'Code']
    # Excel的sheet名
    sheet_name = 'lisp'
    # 执行处理逻辑
    result_data = excel_data_process(excel_files, columns_name, sheet_name)
    # 删除有空值的行,重排序
    result_data = result_data.dropna(axis=0, how='any').reset_index(drop=True)

    # 表名
    table_name = 'logic_exp_unit_1500_info'
    # 表列名
    table_column = ['logic_exp_combine_no', 'logic_exp_combine']
    # 调用函数生成SQL入库脚本
    generate_sql(result_data, table_name, table_column)
    # 生成csv文件
    result_data.to_csv(CSV_FOLDER + table_name + '.csv')


if __name__ == '__main__':
    # 遍历主文件夹
    model_excel_files = walk_file(MAIN_FOLDER_2, 'model', '.xlsx')

    # =======处理model excel文件：start=======
    # process_data_1(model_excel_files)
    # process_data_2(model_excel_files)
    # process_data_3(model_excel_files)
    # process_data_4(model_excel_files)
    # =======处理model excel文件：end=======
