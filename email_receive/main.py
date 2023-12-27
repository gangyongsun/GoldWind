#!/user/bin/env python3
# -*- coding: utf-8 -*-

import pandas as pd
from util.email_util import get_email_content

user_name = '515801610@163.com'
password = 'NUALRKXJCIPCNCAC'

if __name__ == '__main__':
    file_path = './csv/steam.csv'

    df = get_email_content(user_name, password)
    df.to_csv(file_path)

    df = pd.read_csv(file_path)
    result = df.groupby(['account'])['Prisma 2 Case'].sum()
    result.to_csv('./csv/result.csv')
