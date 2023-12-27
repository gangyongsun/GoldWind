import json


def read_json_file(file_name, mode):
    """
    读取json文件解析为dict
    :param file_name: json文件
    :param mode: 读取模式
    :return: json dict
    """
    json_file = open(file_name, mode)
    json_dic = json.load(json_file)
    json_file.close()
    return json_dic


def get_json_data(json_str):
    """
    这个专门解析桑思远发过来的JSON字符串，key是一个编号，value就是要处理的内容，比较特殊
    :param json_str:
    :return:
    """
    json_dict = json.loads(json_str)
    json_data_array = []
    for key in json_dict.keys():
        json_data_array.append(json_dict[key])
    return json_data_array[0]


def list_dict_keys(dict_data):
    """
    遍历输出dict的key
    :param dict_data: dict数据
    :return:
    """
    if isinstance(dict_data, dict):
        for key in dict_data.keys():
            print(key)
            list_dict_keys(dict_data[key])


def list_json_dict_properties(dict_data):
    """
    遍历输出dict的key
    :param dict_data: dict数据
    :return:
    """
    properties = []
    if isinstance(dict_data, dict):
        for key in dict_data.keys():
            for key2 in dict_data[key].keys():
                properties.append(key2)
    return properties
