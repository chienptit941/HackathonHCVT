

def get_rated_courses(user_id):
    u_rated_courses = ['A', 'B', 'D']
    u_course_rates = [5.0, 4.0, 3.0]
    return u_rated_courses, u_course_rates


def get_user_rated_data(user_id, course_id):
    u_rated_courses, _ = get_rated_courses(user_id)
    related_courses = u_rated_courses + course_id
    # Select user who took all related_course
    user_x = []


