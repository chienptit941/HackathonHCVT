import numpy as np


def item_cosine_estimate(item_vector_a, item_vector_b):
    dot_product = np.dot(item_vector_a, item_vector_b)
    item_vector_a_mod = np.linalg.norm(item_vector_a)
    item_vector_b_mod = np.linalg.norm(item_vector_b)
    cosine_similarity = dot_product / (item_vector_a_mod * item_vector_b_mod)
    return cosine_similarity


def pearsonr(x, y):
    n = len(x)
    sum_x = float(sum(x))
    sum_y = float(sum(y))
    sum_x_sq = sum(map(lambda x: pow(x, 2), x))
    sum_y_sq = sum(map(lambda x: pow(x, 2), y))
    psum = sum(map(lambda x, y: x * y, x, y))  # itertools.imap
    if n > 0:
        num = psum - (sum_x * sum_y/n)
        den = pow((sum_x_sq - pow(sum_x, 2) / n) * (sum_y_sq - pow(sum_y, 2) / n), 0.5)
    else:
        num = 0
        den = 0
    if den == 0:
        return 0
    return num / den


def average(x):
    n = len(x)
    sum_x = float(sum(x))
    if n == 0:
        return 0
    return sum_x / n



