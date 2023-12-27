#!/bin/bash

function orignize_file() {
  expression_all='^\s*\(exps_code|^\s*\(\s*RS|^\s*\(\s*SR|^\s*\(\s*TOF|^\s*\(\s*TON|^\s*\(\s*TIME|^\s*\(\s*TP|^\s*\(\s*F_TRIG|^\s*\(\s*R_TRIG|^\s*\(\s*ADD|^\s*\(\s*SUB|^\s*\(\s*MUL|^\s*\(\s*DIV|^\s*\(\s*MOD|^\s*\(\s*AND|^\s*\(\s*OR|^\s*\(\s*NOT|^\s*\(\s*XOR|^\s*\(\s*SEL|^\s*\(\s*MAX|^\s*\(\s*MIN|^\s*\(\s*LIMIT|^\s*\(\s*GT|^\s*\(\s*LT|^\s*\(\s*LE|^\s*\(\s*GE|^\s*\(\s*EQ|^\s*\(\s*NE|^\s*\(\s*ABS|^\s*\(\s*SQRT|^\s*\(\s*LN|^\s*\(\s*LOG|^\s*\(\s*EXP|^\s*\(\s*SIN|^\s*\(\s*COS|^\s*\(\s*TAN|^\s*\(\s*ASIN|^\s*\(\s*ACOS|^\s*\(\s*ATAN|^\s*\(\s*EXPT|^\s*\(\s*SHL|^\s*\(\s*SHR|^\s*\(\s*ROL|^\s*\(\s*ROR|^\s*\(\s*CTU|^\s*\(\s*CTD|^\s*\(\s*CTUD|^\s*\(\s*FVAR|^\s*\(\s*BVAR|^\s+\)|^\s+\d+'
  for element in $(ls $1); do
    dir_or_file=$1"/"$element
    if [ -d $dir_or_file ]; then
      orignize_file $dir_or_file
    else
      if [[ $dir_or_file =~ \.txt$ ]]; then
        data=$(sed 's/\（/\(/g' $dir_or_file | grep -E $expression_all | tr '\r' '\n')
        echo $data >$dir_or_file.tmp1
      fi
    fi
  done
}

main_folder='/Users/alvin/Documents/Goldwind/数字化/故障可视化/1.5modle'

orignize_file $main_folder
