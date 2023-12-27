#!/user/bin/env python3
# -*- coding: utf-8 -*-
import pandas as pd

df = pd.read_csv('./csv/result.csv')
count = df['Prisma 2 Case'].sum()
print(count)
