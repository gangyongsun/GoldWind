#!/user/bin/env python3
# -*- coding: utf-8 -*-

import poplib
import pandas as pd
from email.parser import Parser
from email.header import decode_header
from email.utils import parseaddr

poplib._MAXLINE = 20480


def decode_str(s):
    value, charset = decode_header(s)[0]
    if charset:
        value = value.decode(charset)
    return value


def get_header(msg):
    for header in ['From', 'To', 'Subject']:
        value = msg.get(header, '')
        if value:
            # 文章的标题有专门的处理方法
            if header == 'Subject':
                value = decode_str(value)
            elif header in ['From', 'To']:
                # 地址也有专门的处理方法
                hdr, addr = parseaddr(value)
                name = decode_str(addr)
                # value = name + ' < ' + addr + ' > '
                value = name
        print(header + ':' + value)


def judge_subject(msg):
    value = msg.get('Subject', '')
    if value and value == 'Thank you for your Community Market purchase':
        return True
    else:
        return False


def guess_charset(msg):
    charset = msg.get_charset()
    if charset is None:
        content_type = msg.get('Content-Type', '').lower()
        pos = content_type.find('charset=')
        if pos >= 0:
            charset = content_type[pos + 8:].strip()
    print(charset)
    return charset


def get_file(msg):
    for part in msg.walk():
        filename = part.get_filename()
        if filename is not None:  # 如果存在附件
            filename = decode_str(filename)  # 获取的文件是乱码名称，通过一开始定义的函数解码
            data = part.get_payload(decode=True)  # 取出文件正文内容
            # 此处可以自己定义文件保存位置
            path = filename
            f = open(path, 'wb')
            f.write(data)
            f.close()
            print(filename, 'download')


def not_empty(s):
    return s and s.strip()


def get_content(msg):
    for part in msg.walk():
        content_type = part.get_content_type()

        if content_type == 'text/plain':
            content = part.get_payload(decode=True).decode('unicode_escape')
            content_array = content.split('====')[0].replace('\r', '').split('https://')
            content_array = list(filter(not_empty, content_array))
        else:
            continue

        # 解析账户名
        steam_acount = content_array.pop(0).split(',')[0].split(' ')[1]
        content_array_len = len(content_array)
        # 解析重要信息
        part2_array = content_array[content_array_len - 2].split('\n')
        part2_array = list(filter(not_empty, part2_array))
        if 'store.steampowered.com/account' in part2_array:
            part2_array.remove('store.steampowered.com/account')
        if '--------' in part2_array:
            part2_array.remove('--------')
        if '(fees charged by Valve Corp.)' in part2_array:
            part2_array.remove('(fees charged by Valve Corp.)')
        if 'The Steam Support Team' in part2_array:
            part2_array.remove('The Steam Support Team')

        type_num = part2_array.pop(0).split(':')[0].split('Prisma 2 Case', 1)[0]

        result_dict = dict(zip(part2_array[0::2], part2_array[1::2]))
        result_dict['account'] = steam_acount
        result_dict['Prisma 2 Case'] = type_num if type_num != '' else 1
        if 'Confirmation Number' in result_dict.keys():
            del result_dict['Confirmation Number']
    # print(result_dict)
    return result_dict


def get_email_content(account, password, pop3_server='pop.163.com'):
    """
    收邮件
    :param account: 邮箱账号
    :param password: 邮箱密码（客户端授权码非登陆密码）
    :param pop3_server: 服务器地址
    :return:
    """
    # 开始连接到服务器
    server = poplib.POP3(pop3_server)

    # 打开或者关闭调试信息，为打开，会在控制台打印客户端与服务器的交互信息
    server.set_debuglevel(0)

    # 打印POP3服务器的欢迎文字，验证是否正确连接到了邮件服务器
    print(server.getwelcome().decode('utf8'))

    # 开始进行身份验证
    server.user(account)
    server.pass_(password)

    # 返回邮件总数目和占用服务器的空间大小（字节数）， 通过stat()方法即可
    email_num, email_size = server.stat()
    print("消息的数量: {0}, 消息的总大小: {1}".format(email_num, email_size))

    # 使用list()返回所有邮件的编号，默认为字节类型的串
    rsp, msg_list, rsp_siz = server.list()
    # print("服务器的响应: {0},\n消息列表： {1},\n返回消息的大小： {2}".format(rsp, msg_list, rsp_siz))

    dict_array = []
    total_mail_numbers = len(msg_list)
    for i in range(1, total_mail_numbers):
        print('========================', i, '========================')
        rsp, msglines, msgsiz = server.retr(i)
        msg_content = b'\r\n'.join(msglines).decode('utf-8', 'ignore')
        msg = Parser().parsestr(msg_content)
        flag = judge_subject(msg)
        if flag:
            result_dict = get_content(msg)
            dict_array.append(result_dict)

    df = pd.DataFrame(dict_array)
    # 关闭与服务器的连接，释放资源
    server.close()
    return df


def delete_email(email, password, host="mail.163.com"):
    # connect to pop3 server
    server = poplib.POP3(host)
    # open debug
    # server.set_debuglevel(1)

    # 身份验证
    server.user(email)
    server.pass_(password)

    # 使用list()返回所有邮件的编号，默认为字节类型的串
    # list()返回tuple
    resp, mails, octets = server.list()
    # print("响应信息： ", resp)
    # print("所有邮件简要信息： ", mails)
    # print("list方法返回数据大小（字节）： ", octets)

    # get the latest, index from 1:
    index = len(mails)

    # 删除所有邮件
    while index > 0:
        server.dele(index)
        print(index)
        index = index - 1

        # commit command and close
    server.quit()
