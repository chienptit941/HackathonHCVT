import similarity_estimator
import knn
import numpy as np
from collaborative_suggest.utils import data_helpers
from collaborative_suggest.regression import rnn_app


def predict_r_u_i(u, neighbors, neighbor_distances, neighbor_rate_i):
    r_u_avg = similarity_estimator.average(u)
    sum_r_v_i = 0.0
    sum_u_v = 0.0
    for nb_id, nb_ in enumerate(neighbors):
        r_v_i = neighbor_rate_i[nb_id]
        r_v_avg = similarity_estimator.average(nb_)
        sim_u_v = neighbor_distances[nb_id]
        sum_r_v_i += sim_u_v * (r_v_i - r_v_avg)
        sum_u_v += abs(sim_u_v)
    pred_u_i = r_u_avg + (sum_r_v_i / sum_u_v)
    return pred_u_i


def neighbor_data_prepare(u_id, rate_data, u_rated_id_arr, item_id, k=5):
    training_set = []
    for rate_dt_id, rate_dt_ in enumerate(rate_data):
        r_v = [rate_dt_[u_rated_id_] for u_rated_id_ in u_rated_id_arr]
        training_set.append(r_v)
    u = training_set[u_id]
    neighbor_indices, neighbor_distances, _ = knn.similarity_knn(u_id, training_set, k=5)
    neighbors = [rate_data[v_id] for v_id in neighbor_indices]
    neighbor_rate_for_i = [rate_data[v_id][item_id] for v_id in neighbor_indices]

    return u, neighbors, neighbor_distances, neighbor_rate_for_i


def data_prepare(u_id, rate_data, item_id, k=5):
    u_rated_id_arr = []
    r_u = rate_data[u_id]
    for r_u_id, r_u_i in enumerate(r_u):
        if not (r_u_i == -1.0):
            u_rated_id_arr.append(r_u_id)
    u, neighbors, neighbor_distances, neighbor_rate_for_i = neighbor_data_prepare(
        u_id, rate_data, u_rated_id_arr, item_id, k)
    return u, neighbors, neighbor_distances, neighbor_rate_for_i


def predict_one(u_id, rate_data, item_id, k=5):
    u, neighbors, neighbor_distances, neighbor_rate_for_i = data_prepare(u_id, rate_data, item_id, k)
    r_u_i = predict_r_u_i(u, neighbors, neighbor_distances, neighbor_rate_for_i)
    return r_u_i


def get_similar_users(user_id, k=5, threshold=0.8):
    rated_data, _,  user_list = data_helpers.get_user_rated_data(user_id)
    neighbor_indices, _, _ = knn.similarity_knn(sample_id=0, x=rated_data, k=k,
                                                threshold=threshold, dist_func=similarity_estimator.pearsonr)
    neighbor_user_list = []
    for nb_id_ in neighbor_indices:
        neighbor_user_list.append(user_list[nb_id_])
    return neighbor_user_list


def get_related_courses(user_id):
    related_courses = []
    neighbor_user_list = get_similar_users(user_id)
    current_user_studied_courses = data_helpers.get_studied_courses(user_id)
    for nb_user_id_ in neighbor_user_list:
        nb_user_studied_courses = data_helpers.get_studied_courses(nb_user_id_)
        related_courses += nb_user_studied_courses
    related_courses = list(set(related_courses) - set(current_user_studied_courses))
    return related_courses


def get_related_courses_and_rates(user_id):
    related_courses = get_related_courses(user_id)
    predict_course_rates = []
    for related_course_ in related_courses:
        rate_data, _, _ = data_helpers.get_user_related_rate_data(user_id=user_id, course_id=related_course_)
        item_id = len(rate_data[0]) - 1
        u_id = 0
        predicted_rate = predict_one(u_id=u_id, rate_data=rate_data, item_id=item_id)
        # recurrent_predict_rate = rnn_app.predict_one(u_id=u_id, rate_data=rate_data, item_id=item_id)
        # print('=' * 50)
        # print(recurrent_predict_rate)
        predict_course_rates.append(predicted_rate)
    return related_courses, predict_course_rates

rl_cs_s, prd_c_r = get_related_courses_and_rates(user_id='a')
for rl_cs_id, rl_cs_ in enumerate(rl_cs_s):
    print rl_cs_
    print prd_c_r[rl_cs_id]

# rate_data_ = [[5.0, 4.0, 5.0, 5.0], [5.0, 5.0, 4.0, 4.0], [5.0, 5.0, 5.0, 5.0], [2.0, 1.0, 2.0, 2.0],
#               [1.0, 2.0, 1.0, 1.0], [1.0, 1.0, 2.0, 1.0]]
# # rate_data_ = [[1.0, 2.0, 2.0, 3.0], [5.0, 4.0, 3.0, 4.0], [4.0, 4.0, 3.0, 5.0], [2.0, 3.0, 5.0, 4.0]]
# u_id_ = 0
# item_id_ = len(rate_data_[0]) - 1
# print rnn_app.predict_one(u_id_, rate_data_, item_id_)
