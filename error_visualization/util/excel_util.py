# coding=utf-8

import pandas as pd
import xlrd


def get_excel_table_by_sheet_index(file_name, sheet_index=0):
    """
    获取excel第n个sheet的table
    :param file_name: excel文件
    :param sheet_index: sheet索引
    :return: 第n个
    """
    return xlrd.open_workbook(file_name).sheet_by_index(sheet_index)


def get_excel_table_by_sheet_name(file_name, sheet_name):
    """
    根据sheet_name获取excel的table
    :param file_name: excel文件
    :param sheet_name: sheet名
    :return: 第n个
    """
    return xlrd.open_workbook(file_name).sheet_by_name(sheet_name)


def excel_data_process(file_name_array, columns_name, sheet_name):
    """
    读取excel文件列表进行数据处理
    :param file_name_array: excel文件列表
    :param columns_name: 列名称数组
    :param sheet_name: excel文件的sheet名
    :return:
    """
    result_data = pd.DataFrame(columns=columns_name)
    for excel_file in file_name_array:
        # 获取excel指定sheet的table
        table = get_excel_table_by_sheet_name(excel_file, sheet_name)
        # 解析table生成DataFrame
        df = analysis_sheet_table(table)
        if not (df is None):
            result_data = result_data.append(df, ignore_index=True)
        # 删除全部空的列、行，并重排序
    return result_data.dropna(axis=1, how='all').dropna(axis=0, how='all').reset_index(drop=True)


def analysis_sheet_table(table):
    """
    解析table生成dataframe
    :param table: excel的sheet的table
    :return:
    """
    # 行数
    total_rows = table.nrows
    # 列数
    total_columns = table.ncols
    data_array = []
    for row in range(0, total_rows):
        sub_array = []
        for column in range(0, total_columns):
            cell_value = str(table.cell(row, column).value).strip()
            if len(cell_value) == 0:
                sub_array.append(None)
            else:
                sub_array.append(cell_value)
        data_array.append(sub_array)
    if len(data_array):
        # 弹出表头作为列标题数组使用
        columns_value = data_array.pop(0)
        df = pd.DataFrame(data_array, columns=columns_value)
        return df
    else:
        return None


def excel_data_process_ext(file_name_array, columns, columns_name, start_row, row_number=2):
    """
    读取excel文件列表进行数据处理
    :param file_name_array: excel文件列表
    :param columns: 列编号数组
    :param columns_name: 列名数组
    :param start_row: 开始行
    :param row_number:行数，默认2，因为第一组数据是前两行
    :return:
    """
    result_data = pd.DataFrame(columns=columns_name)
    for excel_file in file_name_array:
        # 获取excel第n个sheet的table
        table = get_excel_table_by_sheet_index(excel_file)

        # 行数
        if not row_number:
            row_number = table.nrows

        # 解析table生成dataframe
        df = analysis_sheet_table_ext(table, start_row, row_number, columns, columns_name)
        if not (df is None):
            result_data = result_data.append(df, ignore_index=True)
    # 删除全部空的列、行，并重排序
    return result_data.dropna(axis=1, how='all').dropna(axis=0, how='all').reset_index(drop=True)


def analysis_sheet_table_ext(table, start_row, row_number, columns, columns_name):
    """
    解析table生成dataframe
    :param table: excel的sheet的table
    :param start_row: 开始行号
    :param row_number: 总行数
    :param columns: 总列数
    :param columns_name: 列标题数组
    :return:
    """
    data_array = []
    for row in range(start_row, row_number):
        # 子数组先放扩展列值作为前几列，与columns_name中的前几列对应
        sub_array = [judge_value(table.cell(1, 0).value), judge_value(table.cell(1, 1).value), judge_value(table.cell(1, 2).value),
                     judge_value(table.cell(1, 3).value), judge_value(table.cell(1, 4).value)]
        for column in columns:
            cell_value = str(table.cell(row, column).value).strip()
            if len(cell_value) == 0:
                sub_array.append(None)
            else:
                sub_array.append(cell_value)
        data_array.append(sub_array)
    if len(data_array):
        # 弹出表头
        data_array.pop(0)
        # columns_value = ['机型', '主控程序版本号', '变流程序版本号', '中央监控版本', '故障代码', '需要文件类型', '变量逻辑表达式编号', '变量表达式对应故障原因编号', '变量表达式对应处理建议编号']
        df = pd.DataFrame(data_array, columns=columns_name)
        return df
    else:
        return None


def excel_data_process_ext2(file_name_array, columns, columns_name, start_row, row_number=2):
    """
    读取excel文件列表进行数据处理
    :param file_name_array: excel文件列表
    :param columns: 列编号数组
    :param columns_name: 列名数组
    :param start_row: 开始行
    :param row_number:行数，默认2，因为第一组数据是前两行
    :return:
    """
    result_data = pd.DataFrame(columns=columns_name)
    for excel_file in file_name_array:
        # 获取excel第n个sheet的table
        table = get_excel_table_by_sheet_index(excel_file)

        # 行数
        if not row_number:
            row_number = table.nrows

        # 解析table生成dataframe
        df = analysis_sheet_table_ext2(table, start_row, row_number, columns, columns_name)
        if not (df is None):
            result_data = result_data.append(df, ignore_index=True)
    # 删除全部空的列、行，并重排序
    return result_data.dropna(axis=1, how='all').dropna(axis=0, how='all').reset_index(drop=True)


def analysis_sheet_table_ext2(table, start_row, row_number, columns, columns_name):
    """
    解析table生成dataframe
    :param table: excel的sheet的table
    :param start_row: 开始行号
    :param row_number: 总行数
    :param columns: 总列数
    :param columns_name: 列标题数组
    :return:
    """
    data_array = []
    for row in range(start_row, row_number):
        # 子数组先放扩展列值作为前几列，与columns_name中的前几列对应
        sub_array = [judge_value(table.cell(1, 0).value)]
        for column in columns:
            cell_value = str(table.cell(row, column).value).strip()
            if len(cell_value) == 0:
                sub_array.append(None)
            else:
                sub_array.append(cell_value)
        data_array.append(sub_array)
    if len(data_array):
        # 弹出表头
        data_array.pop(0)
        df = pd.DataFrame(data_array, columns=columns_name)
        return df
    else:
        return None


def judge_value(obj):
    temp_value = str(obj).strip()
    if len(temp_value) == 0:
        return None
    else:
        if isinstance(obj, float):
            return '{:g}'.format(obj)
        else:
            return temp_value
