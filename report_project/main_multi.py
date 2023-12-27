import json

from util.json_util import read_json_file

from util.process_util import avg_1day_process_multi
from util.process_util import avg_1min_process_multi

# 读取配置文件，获取要求1分钟平均、1天平均、1分钟count和1分钟随机值的字段
# 理论有功功率、变流器有功功率、机舱风速、有功功率设定值
avg_1min_properties = ['WTUR.PwrAt.Ra.F32.Theory','WTUR.PwrAt.Ra.F32','WTUR.WSpd.Ra.F32','WTUR.PwrAt.Ra.F32.SetValue']

# 风机运行状态、风机限功率停机、风机限功率标志、风机运行模式字、主故障
random_1min_properties = ['WTUR.Turst.Rs.S','WTUR.BooL.Rd.b1.LimPowStopState','WTUR.BooL.Rd.b0.PowerFlag','WTUR.Other.Wn.I16.StopModeWord','WTUR.Flt.Ri.I32.main']

# 风机机舱风速、风机环境温度、风机发电量、风机耗电量
avg_1day_properties = ['WTUR.WSpd.Ra.F32','WTUR.Temp.Ra.F32','WTUR.TotEgyAt.Wt.F32','WTUR.Coney.Wt.F32']

# 变流器有功功率、理论有功功率
count_1min_properties = ['WTUR.PwrAt.Ra.F32','WTUR.PwrAt.Ra.F32.Theory']

# 读取json文件，之后这里的JSON文件用户要通过restful服务发过来
json_dict = read_json_file('/Users/alvin/Documents/Goldwind/数字化/接手龙龙任务/风机日报项目/daily_from_hbase.json', 'r')

# 这里传入的是原始json数据
json2 = avg_1min_process_multi(avg_1min_properties, json_dict)

print(json2)
