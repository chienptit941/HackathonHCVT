# -*- coding: utf-8 -*-
"""
@Author: tungnd

"""

import db_connection
def get_rated_courses(user_id, limit=3):
    sql = 'SELECT tblrate.tblcourse_id, tblrate.score \
        FROM tblrate\
        WHERE tblrate.tbluser_id = ' + str(user_id) + ' AND tblrate.score > -1\
        ORDER BY tblrate.id DESC LIMIT ' + str(limit) + ' ;'
    # print sql
    cursor = db_connection.get_connection().cursor()
    results = []
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
    except Exception as ex:
        print 'Exception: ', ex
        pass
    u_rated_courses = []
    u_course_rates = []
    for res in results:
        u_rated_courses.append(int(res[0]))
        u_course_rates.append(res[1])
    return u_rated_courses, u_course_rates

def get_course_rate(user_id, course_id):
    sql = 'SELECT score\
        FROM tblrate\
        WHERE tblrate.tbluser_id = ' + str(user_id) + ' AND tblrate.tblcourse_id = ' + str(course_id) + ';'
    cursor = db_connection.get_connection().cursor()
    results = []
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for res in results:
            return res[0]
    except Exception as ex:
        print 'Exception: ', ex
        pass
    return -1.0

def get_studied_courses(user_id):
    sql = 'SELECT tblrate.tblcourse_id  \
        FROM tblrate  \
        WHERE tblrate.tbluser_id = ' + str(user_id) + ' AND tblrate.pastSubject = 1; '
    cursor = db_connection.get_connection().cursor()
    results = []
    studieds = []
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for res in results:
            studieds.append(res[0])
    except Exception as ex:
        print 'Exception: ', ex
        pass
    return studieds
def get_all_course_id():
    sql = ''
    cursor = db_connection.get_connection().cursor()
    results = []
    courses = []
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for res in results:
            courses.append(res[0])
    except Exception as ex:
        print 'Exception: ', ex
        pass
    return courses
def get_all_user_id(ignore_id):
    sql = 'SELECT id FROM hcvt.tbluser WHERE id <> ' + str(ignore_id) + ' ;'
    cursor = db_connection.get_connection().cursor()
    results = []
    users = []
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for res in results:
            users.append(res[0])
    except Exception as ex:
        print 'Exception: ', ex
        pass
    return users
def get_user_related_rate_data(user_id, course_id):
    u_c, u_s = get_rated_courses(user_id, limit=10000)
    u_courses = []
    u_score = []
    for i in range(len(u_c)):
        if u_c[i] !=course_id:
            u_courses.append(u_c[i])
            u_score.append(u_s[i])
    u_courses.append(course_id)
    u_score.append(-1)
    users = get_all_user_id(user_id)
    res_users = [user_id]
    scores = [u_score]
    for user in users:
        score = [-1] * len(u_courses)
        tmp_course, tmp_score = get_rated_courses(user, limit=10000)
        if course_id in tmp_course:    # course_id bat buoc phai co
            # print tmp_course
            for i in range(len(tmp_course)):
                if tmp_course[i] in u_courses:
                    # print 'IN ', tmp_course[i]
                    index = u_courses.index(tmp_course[i])
                    score[index] = tmp_score[i]
            scores.append(score)
            res_users.append(user)
            # break
    return scores, u_courses, res_users

# scores, courses, users = get_user_related_rate_data(80, 1)
# print 'Number score: ', len(scores)
# for s in scores:
#     print s
# print '-' * 30
# print 'Course:', courses
# print '-' * 50
# print users, ' SIZE = ', len(users)


def get_user_rated_data(user_id):
    u_courses, u_score = get_rated_courses(user_id, limit=10000)
    users = get_all_user_id(user_id)
    res_users = [user_id]
    scores = [u_score]
    for user in users:
        score = [-1] * len(u_courses)
        tmp_course, tmp_score = get_rated_courses(user, limit=10000)
        # print tmp_course
        for i in range(len(tmp_course)):
            if tmp_course[i] in u_courses:
                # print 'IN ', tmp_course[i]
                index = u_courses.index(tmp_course[i])
                score[index] = tmp_score[i]
        scores.append(score)
        res_users.append(user)
        # break
    return scores, u_courses, res_users

# scores, courses, users = get_user_rated_data(80)
# print 'Number score: ', len(scores)
# for s in scores:
#     print s
# print '-' * 30
# print 'Course:', courses
# print '-' * 50
# print users, ' SIZE = ', len(users)




"""
def get_rated_courses(user_id):
    u_rated_courses = ['A', 'B', 'D']
    u_course_rates = [5.0, 4.0, 3.0]
    return u_rated_courses, u_course_rates


def get_course_rate(user_id, course_id):
    return 5.0


def get_studied_courses(user_id):
    # NOTE: rated course
    if user_id == 'a':
        return ['A', 'B', 'D']
    if user_id == 'user y':
        return ['A', 'B', 'C', 'D', 'E']
    if user_id == 'user z':
        return ['A', 'B', 'C', 'D', 'F']


def get_user_related_rate_data(user_id, course_id):
    rate_data = []
    u_rated_courses, u_course_rates = get_rated_courses(user_id)
    related_courses = u_rated_courses + [course_id]
    u_course_rates_expand = u_course_rates + [-1.0]
    # Select user who took all related_course
    user_x = [1.0, 2.0, 2.0, 3.0]
    user_y = [5.0, 4.0, 3.0, 4.0]
    user_z = [4.0, 4.0, 3.0, 5.0]
    user_t = [2.0, 3.0, 5.0, 4.0]
    rate_data.append(u_course_rates_expand)
    rate_data.append(user_x)
    rate_data.append(user_y)
    rate_data.append(user_z)
    rate_data.append(user_t)
    return rate_data


def get_user_rated_data(user_id):
    rate_data = []
    user_list = []
    u_rated_courses, u_course_rates = get_rated_courses(user_id)
    # Select user who took all related_course
    found_user_list = ['user x', 'user y', 'user z', 'user t']
    user_list = [user_id] + found_user_list
    user_x = [1.0, 2.0, 2.0]
    user_y = [5.0, 4.0, 3.0]
    user_z = [4.0, 4.0, 3.0]
    user_t = [2.0, 3.0, 5.0]
    rate_data.append(u_course_rates)
    rate_data.append(user_x)
    rate_data.append(user_y)
    rate_data.append(user_z)
    rate_data.append(user_t)
    user_list.append(user_id)
    return rate_data, user_list

"""
