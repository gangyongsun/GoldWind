import requests
import json

# 1.API URL
api_url = 'https://9l7hv8l9r4.execute-api.cn-north-1.amazonaws.com.cn/DayReport/report'

# 2.方法名参数，可选参数如（oneDayAvg：求日平均值；oneMinAvg：求分钟级别平均值；oneMinCount：求分钟级别count；oneMinRandom：求分钟级别random值）
functionName = 'oneMinRandom'

avg_1day_properties = 'WTUR.WSpd.Ra.F32,WTUR.Temp.Ra.F32,WTUR.TotEgyAt.Wt.F32,WTUR.Coney.Wt.F32'
avg_1min_properties = 'WTUR.PwrAt.Ra.F32.Theory,WTUR.PwrAt.Ra.F32,WTUR.WSpd.Ra.F32,WTUR.PwrAt.Ra.F32.SetValue'
count_1min_properties = 'WTUR.PwrAt.Ra.F32,WTUR.PwrAt.Ra.F32.Theory'
random_1min_properties = 'WTUR.Turst.Rs.S,WTUR.BooL.Rd.b1.LimPowStopState,WTUR.BooL.Rd.b0.PowerFlag,WTUR.Other.Wn.I16.StopModeWord,WTUR.Flt.Ri.I32.main'

# 3.要求值的字段名，用逗号分隔，避免逗号前后有空格，如果担心有空格请用程序处理下
targetPropertyArray = random_1min_properties

# 读取json文件获取内容
with open('/Users/alvin/Documents/Goldwind/数字化/接手龙龙任务/风机日报项目/daily_from_hbase.json') as file_obj:
    jsonContent = file_obj.read()
# 转json dict
json_dict = json.loads(jsonContent)

data = json.dumps({'functionName': functionName, 'targetPropertyArray': targetPropertyArray, 'json_dict': json_dict})

response = requests.post(api_url, data)

print(response.text)
