这是不同的方法和对应的targetPropertyArray

oneMinAvg：求分钟级别平均值
# 理论有功功率、变流器有功功率、机舱风速、有功功率设定值
avg_1min_properties = 'WTUR.PwrAt.Ra.F32.Theory,WTUR.PwrAt.Ra.F32,WTUR.WSpd.Ra.F32,WTUR.PwrAt.Ra.F32.SetValue'

oneMinRandom：求分钟级别random值
# 风机运行状态、风机限功率停机、风机限功率标志、风机运行模式字、主故障
random_1min_properties = 'WTUR.Turst.Rs.S,WTUR.BooL.Rd.b1.LimPowStopState,WTUR.BooL.Rd.b0.PowerFlag,WTUR.Other.Wn.I16.StopModeWord,WTUR.Flt.Ri.I32.main'

oneDayAvg：求日平均值
# 风机机舱风速、风机环境温度、风机发电量、风机耗电量
avg_1day_properties = 'WTUR.WSpd.Ra.F32,WTUR.Temp.Ra.F32,WTUR.TotEgyAt.Wt.F32,WTUR.Coney.Wt.F32'

oneMinCount：求分钟级别count
# 变流器有功功率、理论有功功率
count_1min_properties = 'WTUR.PwrAt.Ra.F32,WTUR.PwrAt.Ra.F32.Theory'
