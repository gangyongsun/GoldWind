对slip的处理步骤

1.通过pattern_data.sh脚本生成中间文件*.txt.tmp1
    里面的正则表达式，是通过expr.py生成的

2.通过Python程序处理中间文件生成中间文件*.txt.tmp2
    main_slip.py/change_line_and_add_comma()

3.通过命令行查看需要处理的中间文件*.txt.tmp2：
    grep 'OR\s*(exps_code' */*.txt.tmp2
    grep 'AND\s*$' */*.txt.tmp2
    grep 'AND\s*(exps_code' */*.txt.tmp2
    grep 'EQ\s*(exps_code' */*.txt.tmp2

    在Linux终端可以这样用,因为BSD sh不支持正则，所以MAC用户要用上面的方式👆
    grep -E 'OR\s*(exps_code|AND\s*$|AND\s*(exps_code|EQ\s*(exps_code' */*.txt.tmp2

    用这个脚本来获取帮助吧：checker.sh

4.对换行的和多加逗号的进行处理（手动处理）

5.上面步骤处理完成后，对所有文件进行扩展名替换（转csv文件）
    main_slip.py/redefine_ext('.csv')

6.去掉csv文件第一行空行并合并到中间文件
    main_slip.py/merge_csv_files()

7.检查一遍合并的csv文件

8.读取CSV生成SQL脚本
    generate_sql_from_csv(TMP_FOLDER + 'temp.csv')
