# -*- coding: utf-8 -*-
"""
@Author: tungnd
    
"""
import MySQLdb


def get_connection(host='192.168.1.216', user='root', password='toor', db_name='hcvt'):
    db_connection = MySQLdb.connect(host, user, password, db_name, charset='utf8')#,
    return db_connection



