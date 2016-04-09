import numpy as np
from rnn_train import Train
from rnn_predict import Predict
import data_helpers


def predict_one(u_id, rate_data, item_id, user_id='', is_train=False):
    if u_id < (len(rate_data) - 1):
        train_data = rate_data[:u_id] + rate_data[u_id + 1:]
    else:
        train_data = rate_data[:u_id]
    train_data = np.array(train_data)
    input_data = []
    label_data = []
    for data_id, train_data_ in enumerate(train_data):
        if item_id < (len(train_data_) - 1):
            other_rate = train_data_[:item_id] + train_data_[item_id + 1:]
        else:
            other_rate = train_data_[:item_id]
        input_data.append(other_rate)
        label_data.append([train_data_[item_id]])
        # input_data.append(train_data_[:-1])
        # label_data.append([train_data_[-1]])
    input_data = np.array(input_data)
    label_data = np.array(label_data)
    batch_size = len(input_data)
    out_dir = '/home/cao/workspace/HackathonHCVT/collaborative_suggest/regression/models'
    out_dir += str(user_id)
    # import random
    # random_node_id = random.randint(0, 100)
    # out_dir += '/' + str(random_node_id)
    n_hidden = 12
    # new_train_model = Train(learning_rate=0.001, n_hidden=12, batch_size=2,
    #                         training_iters=1000, out_dir='./models',
    #                         checkpoint_step=1000)
    # new_train_model.run(input_data=input_data, label_data=label_data)
    if is_train:
        new_train = Train(learning_rate=0.001, n_hidden=n_hidden, batch_size=batch_size, training_iters=1000, out_dir=out_dir,
                          checkpoint_step=1000)
        new_train.run(input_data, label_data)

    u_data = rate_data[u_id]
    predict_data = [u_data[:item_id] + u_data[item_id + 1:]]
    batch_size = len(predict_data)
    new_predict = Predict(n_hidden=n_hidden, batch_size=batch_size, out_dir=out_dir)
    predicted_rates = new_predict.predict(predict_data)
    return predicted_rates[0][0]




# rate_data_ = [[5.0, 4.0, 5.0, 5.0], [5.0, 5.0, 4.0, 4.0], [5.0, 5.0, 5.0, 5.0], [2.0, 1.0, 2.0, 2.0],
#               [1.0, 2.0, 1.0, 1.0], [1.0, 1.0, 2.0, 1.0]]
# rate_data_ = [[1.0, 2.0, 2.0, 3.0], [5.0, 4.0, 3.0, 4.0], [4.0, 4.0, 3.0, 5.0], [2.0, 3.0, 5.0, 4.0]]
# u_id_ = 0
# item_id_ = len(rate_data_[0]) - 1
# predict_one(u_id_, rate_data_, item_id_)
