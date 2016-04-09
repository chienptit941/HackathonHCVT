from sklearn.neighbors import NearestNeighbors
import pickle
import operator
import similarity_estimator
import numpy as np


def knn_total(x, k):
    knn_model = NearestNeighbors(n_neighbors=k, algorithm="ball_tree").fit(x)
    distances, indices = knn_model.kneighbors(x)
    return distances, indices


def knn_train(x, k, file_path='./knn_model'):
    knn_model = NearestNeighbors(n_neighbors=k, algorithm="ball_tree").fit(x)
    try:
        pickle.dump(knn_model, open(file_path, 'wb'))
    except Exception as inst:
        print inst
        pass
    return knn_model


def knn_predict(x, knn_model=None, file_path='./knn_model'):
    if knn_model is None:
        try:
            knn_model = pickle.load(open(file_path, 'rb'))
        except Exception as inst:
            print inst
            knn_model = None
    distances = None
    indices = None
    if knn_model is not None:
        try:
            distances, indices = knn_model.kneighbors(x)
        except Exception as inst:
            print inst
            pass
    return [distances, indices]


def similarity_knn(sample_id, x, k, threshold=0.0, dist_func=None):
    test_instance = x[sample_id]
    neighbor_indices = []
    neighbor_distances = []
    neighbors = []
    neighbors_indices_, neighbor_distances_, neighbors_ = get_sim_neighbors(
        x, test_instance, k + 1, threshold=threshold, dist_func=dist_func)
    for nb_id, nb_ in enumerate(neighbors_indices_):
        if not nb_ == sample_id:
            neighbor_indices.append(nb_)
            neighbor_distances.append(neighbor_distances_[nb_id])
            neighbors.append(neighbors_[nb_id])
    return neighbor_indices, neighbor_distances, neighbors


def get_sim_neighbors(training_set, test_instance, k, threshold=0.0, dist_func=None):
    distances = []
    if dist_func is None:
        dist_func = similarity_estimator.pearsonr
    for x in range(len(training_set)):
        # dist = similarity_estimator.pearsonr(test_instance, training_set[x])
        dist = dist_func(test_instance, training_set[x])
        print dist
        if dist >= threshold:
            distances.append((x, dist, training_set[x]))
    distances.sort(key=operator.itemgetter(1), reverse=True)
    neighbors = []
    neighbor_indices = []
    neighbor_distances = []
    for x in range(k):
        if x < len(distances):
            neighbor_indices.append(distances[x][0])
            neighbor_distances.append(distances[x][1])
            neighbors.append(distances[x][2])
    return neighbor_indices, neighbor_distances, neighbors


