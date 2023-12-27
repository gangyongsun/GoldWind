import pandas as pd

value_key = 'value'
timestamp_key = 'timestamp'


def covert_2_1min_level(timestamp_array):
    """
    将秒级时间转换为分钟级别
    :param timestamp_array:时间数组
    :return:将时间数组转换为精确到1分钟级别的时间数组
    """
    timestamp_1min_array = []
    for item_timestamp in timestamp_array:
        timestamp_1min_array.append(item_timestamp[0:16] + ':00')

    return timestamp_1min_array


def convert_2_1day_level(timestamp_array):
    """
    将秒级时间转换为分日级别
    :param timestamp_array:将时间数组转换为精确到1天级别的时间数组
    :return:
    """
    #
    timestamp_1day_array = []
    for item_timestamp in timestamp_array:
        timestamp_1day_array.append(item_timestamp[0:10] + ' 00:00:00')
    return timestamp_1day_array


def avg_1min_process(target_property_array, json_dict):
    """
    求1分钟平均值
    :param target_property_array:需要求1分钟均值的property数组
    :param json_dict:传过来的JSON字符串
    :return: json格式的结果
    """
    # 每台风机对应的字段数组,这个数组必须包含target_property_array的所有property
    property_array = []
    for key in json_dict.keys():
        property_array.append(key)

    series_array = []
    index_array = []
    for prop in target_property_array:
        # 判断property是否在获取的json的properties内
        if prop in property_array:
            index_array.append(prop)
            # 字段对应的value数组
            value_array = json_dict[prop][value_key]
            # 字段对应的时间数组
            timestamp_array = json_dict[prop][timestamp_key]

            # 将秒级时间转换为分钟级别
            timestamp_1min_array = covert_2_1min_level(timestamp_array)

            # 以value数组作为数据，1分钟时间数组作为列名，创建Series
            data_series = pd.Series(value_array, index=timestamp_1min_array)
            # 按列分组，求平均值
            data_series = data_series.groupby(timestamp_1min_array).mean()
            # 将不同字段的1分钟平均值累加到数组series_array
            series_array.append(data_series)

    # 根据series_array和index_array作为参数创建dataframe，并转置
    df = pd.DataFrame(series_array, index=index_array).T
    return df.to_json(orient="columns", force_ascii=False)


def avg_1day_process(target_property_array, json_dict):
    """
    求1天平均值
    :param target_property_array:需要求1天均值的property数组
    :param json_dict:传过来的JSON字符串
    :return: json格式的结果
    """
    # 每台风机对应的字段数组,这个数组必须包含target_property_array的所有property
    property_array = []
    for key in json_dict.keys():
        property_array.append(key)

    series_array = []
    index_array = []
    for prop in target_property_array:
        # 判断property是否在获取的json的properties内
        if prop in property_array:
            index_array.append(prop)
            # value数组
            value_array = json_dict[prop][value_key]
            # 时间数组
            timestamp_array = json_dict[prop][timestamp_key]

            # 将秒级时间转换为1日级别
            timestamp_1day_array = convert_2_1day_level(timestamp_array)

            data_series = pd.Series(value_array, index=timestamp_1day_array)
            data_series = data_series.groupby(timestamp_1day_array).mean()

            series_array.append(data_series)
    df = pd.DataFrame(series_array, index=index_array).T
    return df.to_json(orient="columns", force_ascii=False)


def count_1min_process(target_property_array, json_dict):
    """
    求1min count
    :param target_property_array:需要求1min count的property数组
    :param json_dict:传过来的JSON字符串
    :return: json格式的结果
    """
    # 每台风机对应的字段数组,这个数组必须包含target_property_array的所有property
    property_array = []
    for key in json_dict.keys():
        property_array.append(key)

    series_array = []
    index_array = []
    for prop in target_property_array:
        # 判断property是否在获取的json的properties内
        if prop in property_array:
            index_array.append(prop)
            # value数组
            value_array = json_dict[prop][value_key]
            # 时间数组
            timestamp_array = json_dict[prop][timestamp_key]

            # 将秒级时间转换为分钟级别
            timestamp_1min_array = covert_2_1min_level(timestamp_array)

            data_series = pd.Series(value_array, index=timestamp_1min_array)
            data_series = data_series.groupby(timestamp_1min_array).count()
            series_array.append(data_series)
    df = pd.DataFrame(series_array, index=index_array).T
    return df.to_json(orient="columns", force_ascii=False)


def random_1min_process(target_property_array, json_dict):
    """
    求1分钟随机值
    :param target_property_array: 需要求1分钟随机值的property数组
    :param json_dict:传过来的JSON字符串
    :return:json格式的结果
    """
    # 每台风机对应的字段数组,这个数组必须包含target_property_array的所有property
    property_array = []
    for key in json_dict.keys():
        property_array.append(key)

    series_array = []
    index_array = []
    for prop in target_property_array:
        # 判断property是否在获取的json的properties内
        if prop in property_array:
            index_array.append(prop)
            # value数组
            value_array = json_dict[prop][value_key]
            # 时间数组
            timestamp_array = json_dict[prop][timestamp_key]

            # 将秒级时间转换为分钟级别
            timestamp_1min_array = covert_2_1min_level(timestamp_array)

            data_series = pd.Series(value_array, index=timestamp_1min_array)
            data_series = data_series.groupby(timestamp_1min_array).first()
            series_array.append(data_series)
    df = pd.DataFrame(series_array, index=index_array).T
    return df.to_json(orient="columns", force_ascii=False)


def avg_1min_process_multi(target_property_array, json_dict):
    """
        求1分钟平均值
        :param target_property_array:需要求1分钟均值的property数组
        :param json_dict:传过来的JSON字符串包含多台的数据
        :return: json格式的结果
        """
    json = '{'
    for key in json_dict.keys():
        basic_json_dict = json_dict[key]
        sub_json = avg_1min_process(target_property_array, basic_json_dict)
        json = json + '\'' + key + '\':' + sub_json + ","
    json = json[:-1] + '}'
    return json


def avg_1day_process_multi(target_property_array, json_dict):
    """
    求1天平均值
    :param target_property_array:需要求1天均值的property数组
    :param json_dict:传过来的JSON字符串包含多台的数据
    :return: json格式的结果
    """
    json = '{'
    for key in json_dict.keys():
        basic_json_dict = json_dict[key]
        sub_json = avg_1day_process(target_property_array, basic_json_dict)
        json = json + '\'' + key + '\':' + sub_json + ","
    json = json[:-1] + '}'
    return json


def count_1min_process_multi(target_property_array, json_dict):
    """
   求1min count
   :param target_property_array:需要求1min count的property数组
   :param json_dict:传过来的JSON字符串包含多台的数据
   :return: json格式的结果
   """
    json = '{'
    for key in json_dict.keys():
        basic_json_dict = json_dict[key]
        sub_json = count_1min_process(target_property_array, basic_json_dict)
        json = json + '\'' + key + '\':' + sub_json + ","
    json = json[:-1] + '}'
    return json


def random_1min_process_multi(target_property_array, json_dict):
    """
    求1分钟随机值
    :param target_property_array: 需要求1分钟随机值的property数组
    :param json_dict:传过来的JSON字符串包含多台的数据
    :return:json格式的结果
    """
    json = '{'
    for key in json_dict.keys():
        basic_json_dict = json_dict[key]
        sub_json = random_1min_process(target_property_array, basic_json_dict)
        json = json + '\'' + key + '\':' + sub_json + ","
    json = json[:-1] + '}'
    return json
