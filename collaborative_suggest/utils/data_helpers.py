

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


