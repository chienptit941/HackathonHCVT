# -*- coding: utf-8 -*-
"""
@Author: tungnd
    
"""
import MySQLdb


def get_connection(host='192.168.1.216', user='root', password='toor', db_name='hcvt'):
    db_connection = MySQLdb.connect(host, user, password, db_name, charset='utf8')#,
    return db_connection


def get_course_detail(course_id):
    sql = 'SELECT s.name, s.numberclass, s.startcourse, s.endcourse, c.name, s.description \
    FROM hcvt.tblsubjects s JOIN hcvt.tblcategory c \
    ON s.tblcategory_id = c.id \
    WHERE s.id=' + str(course_id) + ';'
    # print sql
    cursor = get_connection().cursor()
    results = []
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
    except Exception as ex:
        print 'Exception: ', ex
        pass
    parsed_results = ['', '', '', '', '', '']
    if len(results) > 0:
        result = results[0]
        name = str_encode(result[0])
        numberclass = str(result[1])
        startcourse = str(result[2])
        endcourse = str(result[3])
        category = str_encode(result[4])
        description = str_encode(result[5])
        parsed_results = [name, numberclass, startcourse, endcourse, category, description]

    return parsed_results

def get_user_profile(user_id):
    sql = 'SELECT u.fname, u.lname, u.favourite, s.name, r.score \
    FROM hcvt.tblrate r JOIN hcvt.tbluser u \
    ON r.tbluser_id = u.id \
    JOIN hcvt.tblsubjects s \
    ON r.tblcourse_id = s.id \
    WHERE r.pastSubject=1 AND u.id=' + str(user_id) + ';'
    # print sql
    cursor = get_connection().cursor()
    results = []
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
    except Exception as ex:
        print 'Exception: ', ex
        pass
    name = ''
    interests = []
    rates = dict()
    if len(results) > 0:
        for result in results:
            name = str_encode(result[0]) + str_encode(result[1])
            interests = str_encode(result[2]).split(',')
            course_name = str_encode(result[3])
            course_rate = str(result[4])
            rates[course_name] = course_rate
    parsed_results = {'name': name, 'interests': interests, 'rates': rates}

    return parsed_results


def str_encode(string):
    try:
        string = string.encode('utf-8')
    except Exception as inst:
        print inst
        pass
    return string

