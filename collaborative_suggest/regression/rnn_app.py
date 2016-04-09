import numpy as np
from rnn_train import Train
from rnn_predict import Predict


def predict_one(u_id, rate_data, item_id):
    train_data = rate_data[:u_id] + rate_data[u_id + 1:]
    train_data = np.array(train_data)
    input_data = []
    label_data = []
    for data_id, train_data_ in enumerate(train_data):
        input_data.append(train_data_[:-1])
        label_data.append([train_data_[-1]])
    new_train_model = Train(learning_rate=1e-3, n_hidden=50, batch_size=2,
                            training_iters=100, out_dir='./models',
                            checkpoint_step=100)
    new_train_model.run(input_data=input_data, label_data=label_data)


