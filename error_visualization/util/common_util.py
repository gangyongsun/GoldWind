# coding:utf-8

import os
import sys
import re

curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
# 先引入目录的上级
sys.path.append(rootPath)

from config.config import VAR_ARRAY
from config.config import PUBLIC_KEYWORD
from config.config import TOTAL_ARRYA


def mkdir(path):
    """
    创建文件夹目录
    :param path:路径
    :return:Ture False
    """
    # 去除首位空格,去除尾部 \ 符号
    path = path.strip().rstrip("\\")

    # 判断路径是否存在
    is_exists = os.path.exists(path)

    # 判断结果
    if not is_exists:
        # 如果不存在则创建目录
        # 创建目录操作函数
        os.makedirs(path)
        # print(path + ' 创建成功')
        return True
    else:
        # 如果目录存在则不创建，并提示目录已存在
        # print(path + ' 目录已存在')
        return False


def change_ext(file, new_ext):
    """
    通过文件名生成新文件名
    :param file: 文件
    :param new_ext: 新扩展名
    :return:
    """
    # 文件夹
    folder_name = os.path.dirname(file)
    # 文件名
    file_name = os.path.basename(file)
    # 去掉扩展名的文件名
    file_name_without_ext = os.path.splitext(file_name)[0]
    new_file = folder_name + '/' + file_name_without_ext + new_ext
    return new_file


def write_file(file_name, content, encoding):
    """
    写文件
    :param file_name: 文件名(包含全路径)
    :param content: 文件内容
    :param encoding: 编码
    :return:
    """
    # 写SQL文件
    with open(file_name, 'w', encoding=encoding) as f:
        f.write(str(content))
        f.close()


def walk_file(path, start_flag, end_flag):
    """
    遍历文件夹拿文件
    :param path: 主文件夹
    :param start_flag: 开头字符
    :param end_flag: 结尾字符
    :return: 文件数组
    """
    file_array = []
    for root, dirs, files in os.walk(path):
        # root 表示当前正在访问的文件夹路径
        # dirs 表示该文件夹下的子目录名list
        # files 表示该文件夹下的文件list
        for file in files:
            full_file_name = os.path.join(root, file)
            file_name = os.path.basename(full_file_name)
            if start_flag is not None and end_flag is not None:
                if file_name.startswith(start_flag) and file_name.endswith(end_flag):
                    file_array.append(full_file_name)
            elif start_flag is None and end_flag is not None:
                if file_name.endswith(end_flag):
                    file_array.append(full_file_name)
            elif start_flag is not None and end_flag is None:
                if file_name.startwith(start_flag):
                    file_array.append(full_file_name)
            else:
                file_array.append(full_file_name)

    return file_array


def generate_expression():
    """
    生产正则表达式字符串
    :return: 正则表达式字符串
    """
    expression = '^\s*\(' + PUBLIC_KEYWORD
    for item in TOTAL_ARRYA + VAR_ARRAY:
        sub_exp = '^\s*\(\s*' + item
        expression = expression + '|' + sub_exp
    expression = expression + '|^\s+\)|^\s+\d+'
    return expression


def get_file_content(path):
    """
    读取文件获取文件内容
    :param path: 文件路径
    :return: 文件内容
    """
    file_handler = open(path, 'r')
    content = file_handler.read()
    file_handler.close()
    return content


def re_findall(expression, content):
    """
    匹配文件内容
    :param expression: 正则表达式
    :param content: 文件内容
    :return:
    """
    result = re.findall(expression, content)
    return result
