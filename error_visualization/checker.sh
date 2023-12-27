#!/bin/bash

main_folder='/Users/alvin/Documents/Goldwind/数字化/故障可视化/1.5modle'

grep '(OR(exps_code' $main_folder/*/*.txt.tmp2 >./.tmp.checker
grep '(AND(exps_code' $main_folder/*/*.txt.tmp2 >>./.tmp.checker
grep 'AND\s*$' $main_folder/*/*.txt.tmp2 >>./.tmp.checker

cat ./.tmp.checker|awk -F ':' '{print $1}'|sort -u