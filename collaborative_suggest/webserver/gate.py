import os
import sys
from flask import Flask,render_template, request,json
import ast
from collaborative_suggest.statistic import suggester
from collaborative_suggest.utils import db_connection
app = Flask(__name__)


from datetime import timedelta
from flask import make_response, current_app
from functools import update_wrapper


def crossdomain(origin=None, methods=None, headers=None,
                max_age=21600, attach_to_all=True,
                automatic_options=True):
    if methods is not None:
        methods = ', '.join(sorted(x.upper() for x in methods))
    if headers is not None and not isinstance(headers, basestring):
        headers = ', '.join(x.upper() for x in headers)
    if not isinstance(origin, basestring):
        origin = ', '.join(origin)
    if isinstance(max_age, timedelta):
        max_age = max_age.total_seconds()

    def get_methods():
        if methods is not None:
            return methods

        options_resp = current_app.make_default_options_response()
        return options_resp.headers['allow']

    def decorator(f):
        def wrapped_function(*args, **kwargs):
            if automatic_options and request.method == 'OPTIONS':
                resp = current_app.make_default_options_response()
            else:
                resp = make_response(f(*args, **kwargs))
            if not attach_to_all and request.method != 'OPTIONS':
                return resp

            h = resp.headers

            h['Access-Control-Allow-Origin'] = origin
            h['Access-Control-Allow-Methods'] = get_methods()
            h['Access-Control-Max-Age'] = str(max_age)
            if headers is not None:
                h['Access-Control-Allow-Headers'] = headers
            return resp

        f.provide_automatic_options = False
        return update_wrapper(wrapped_function, f)
    return decorator


def start_server():
    app.run(host='0.0.0.0', port='8080', processes=5)


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/predict', methods=['POST'])
@crossdomain(origin='*')
def predict():
    try:
        k = int(request.form['k'])
        if k is None:
            k = 5
    except:
        k = 5
    try:
        u_id = int(request.form['user_id'])
        print(u_id)
        item_id = int(request.form['item_id'])
        print(item_id)
        rate_data = ast.literal_eval(request.form['rate_data'])
        print(rate_data)
        output = str(suggester.predict_one(u_id, rate_data, item_id, k))
    except:
        output = 'Server failed'
    return json.dumps(output)


@app.route('/get_hot_courses', methods=['GET'])
@crossdomain(origin='*')
def get_hot_courses():
    try:
        u_id = request.args.get('user_id')
        output = {'courses': ['A', 'B', 'C', 'D'], 'course_ids': [1, 2, 3, 4]}
    except:
        output = 'Server failed'
    return json.dumps(output)


@app.route('/get_studied_courses', methods=['GET'])
@crossdomain(origin='*')
def get_studied_courses():
    try:
        u_id = request.args.get('user_id')
        output = {'courses': ['A', 'B', 'C'], 'statuses': ['finished', 'finished', 'finished']}
    except:
        output = 'Server failed'
    return json.dumps(output)


@app.route('/get_suggested_courses', methods=['GET'])
@crossdomain(origin='*')
def get_suggested_courses():
    try:
        u_id = request.args.get('user_id')
        # course_ids = [1, 2, 3, 4]
        # courses = ['A', 'B', 'C', 'D']
        course_ids, courses = suggester.suggest_list(user_id=u_id)
        output = {'courses': courses, 'course_ids': course_ids}
    except:
        output = 'Server failed'
    return json.dumps(output)


@app.route('/user_profile', methods=['GET'])
@crossdomain(origin='*')
def get_user_profile():
    try:
        u_id = request.args.get('user_id')
        # name = 'Adam'
        # interests = ['IT', 'English']
        # rates = {"subject name a": "5.0", "subject name b": "4.0"}
        # output = {'user_id': u_id, 'name': name, 'interests': interests,
        #           'rates': rates}
        output = db_connection.get_user_profile(user_id=u_id)
    except:
        output = 'Server failed'
    return json.dumps(output)


@app.route('/get_course_detail', methods=['GET'])
@crossdomain(origin='*')
def get_course_detail():
    try:
        course_id = request.args.get('course_id')
        # name = 'Speaking'
        # numberclass = 10
        # startcourse = '2016-10-20'
        # endcourse = '2016-11-20'
        # category = 'English'
        # description = 'Interesting course'
        name, numberclass, startcourse, endcourse, category, description = db_connection.get_course_detail(course_id)
        output = {'course_id': course_id, 'name': name, 'numberclass': numberclass,
                  'startcourse': startcourse, 'endcourse': endcourse, 'category': category,
                  'description': description}
    except:
        output = 'Server failed'
    return json.dumps(output)


@app.route('/course_register', methods=['GET'])
@crossdomain(origin='*')
def course_register():
    try:
        course_id = request.args.get('course_id')
        user_id = request.args.get('user_id')
        output = 'Successful'
    except:
        output = 'Failed'
    return json.dumps(output)

if __name__ == "__main__":
    start_server()
