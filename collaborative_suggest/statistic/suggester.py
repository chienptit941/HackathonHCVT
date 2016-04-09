import similarity_estimator
import knn
import numpy as np
from collaborative_suggest.utils import data_helpers


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
    rated_data, user_list = data_helpers.get_user_rated_data(user_id)
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

test_neighbors = get_similar_users(user_id='a')
for ea_ in test_neighbors:
    print ea_
