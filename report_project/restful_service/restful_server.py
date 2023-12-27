# -*- coding:utf-8 -*-
import os
import sys
import json
from flask import Flask, request
from flask_restful import Api, Resource, reqparse

curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
# 先引入目录的上级
sys.path.append(rootPath)

from util.process_util import avg_1day_process_multi
from util.process_util import avg_1min_process_multi
from util.process_util import count_1min_process_multi
from util.process_util import random_1min_process_multi

app = Flask(__name__)
api = Api(app)


class OneDayAvgProcessService(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        # set arguments
        parser.add_argument('targetPropertyArray', type=str)

        # 解析传过来的JSON文件
        params_file = request.files['jsonFile']
        dst = os.path.join(os.path.dirname(__file__), params_file.name)
        params_file.save(dst)
        json_file = open(dst, 'r')
        json_dict = json.load(json_file)
        json_file.close()
        os.remove(dst)

        # 解析传过来的property参数
        args = parser.parse_args()
        prop_array = args['targetPropertyArray'].split(',')

        response = avg_1day_process_multi(prop_array, json_dict)
        return response

    def post(self):
        parser = reqparse.RequestParser()
        # set arguments
        parser.add_argument('targetPropertyArray', type=str)

        with open('/Users/alvin/Documents/Goldwind/数字化/接手龙龙任务/风机日报项目/daily_from_hbase.json') as file_obj:
            content = file_obj.read()

        content = '''{\"652238001\":
                            {
                                \"WTUR.WSpd.Ra.F32\":
                                {
                                    \"value\":[100,200,300,400],
                                    \"timestamp\":[\"2019-11-18 14:00:00\",\"2019-11-18 14:01:00\",\"2019-11-18 14:02:00\",\"2019-11-18 14:03:00\"]
                                },
                                \"WTUR.Temp.Ra.F32\":
                                {
                                    \"value\":[110,220,330,440],
                                    \"timestamp\":[\"2019-11-18 14:00:00\",\"2019-11-18 14:01:00\",\"2019-11-18 14:02:00\",\"2019-11-18 14:03:00\"]
                                },
                                \"WTUR.TotEgyAt.Wt.F32\":
                                {
                                    \"value\":[120,210,430,240],
                                    \"timestamp\":[\"2019-11-18 14:00:00\",\"2019-11-18 14:01:00\",\"2019-11-18 14:02:00\",\"2019-11-18 14:03:00\"]
                                },
                                \"WTUR.Coney.Wt.F32\":
                                {
                                    \"value\":[140,240,490,540],
                                    \"timestamp\":[\"2019-11-18 14:00:00\",\"2019-11-18 14:01:00\",\"2019-11-18 14:02:00\",\"2019-11-18 14:03:00\"]
                                }
                            }
                        }'''
        json_dict = json.loads(content)

        # 解析传过来的property参数
        args = parser.parse_args()
        prop_array = args['targetPropertyArray'].split(',')

        response = avg_1day_process_multi(prop_array, json_dict)
        return response


class OneMinAvgProcessService(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        # set arguments
        parser.add_argument('targetPropertyArray', type=str)
        parser.add_argument('data', type=str)

        # 解析传过来的JSON文件
        params_file = request.files['jsonFile']
        dst = os.path.join(os.path.dirname(__file__), params_file.name)
        params_file.save(dst)
        json_file = open(dst, 'r')
        json_dict = json.load(json_file)
        json_file.close()
        os.remove(dst)

        # 解析传过来的property参数
        args = parser.parse_args()
        prop_array = args['targetPropertyArray'].split(',')

        response = avg_1min_process_multi(prop_array, json_dict)
        return response


class OneMinCountProcessService(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        # set arguments
        parser.add_argument('targetPropertyArray', type=str)
        parser.add_argument('data', type=str)

        # 解析传过来的JSON文件
        params_file = request.files['jsonFile']
        dst = os.path.join(os.path.dirname(__file__), params_file.name)
        params_file.save(dst)
        json_file = open(dst, 'r')
        json_dict = json.load(json_file)
        json_file.close()
        os.remove(dst)

        # 解析传过来的property参数
        args = parser.parse_args()
        prop_array = args['targetPropertyArray'].split(',')

        response = count_1min_process_multi(prop_array, json_dict)
        return response


class OneMinRandomProcessService(Resource):
    def get(self):
        parser = reqparse.RequestParser()
        # set arguments
        parser.add_argument('targetPropertyArray', type=str)
        parser.add_argument('data', type=str)

        # 解析传过来的JSON文件
        params_file = request.files['jsonFile']
        dst = os.path.join(os.path.dirname(__file__), params_file.name)
        params_file.save(dst)
        json_file = open(dst, 'r')
        json_dict = json.load(json_file)
        json_file.close()
        os.remove(dst)

        # 解析传过来的property参数
        args = parser.parse_args()
        prop_array = args['targetPropertyArray'].split(',')

        response = random_1min_process_multi(prop_array, json_dict)
        return response


# 1天均值API
api.add_resource(OneDayAvgProcessService, '/report/oneDayAvg')
# 1Min均值API
api.add_resource(OneMinAvgProcessService, '/report/oneMinAvg')
# 1Min Count API
api.add_resource(OneMinCountProcessService, '/report/oneMinCount')
# 1Min Random 值API
api.add_resource(OneMinRandomProcessService, '/report/oneMinRandom')
