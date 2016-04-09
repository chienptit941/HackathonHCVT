import data_helpers
from rnn_model import RNN
import tensorflow as tf
import numpy as np


class Predict(object):
    def __init__(self, n_hidden, batch_size, out_dir):
        self.n_hidden = n_hidden
        self.batch_size = batch_size
        self.ckpt_dir = out_dir + "/checkpoints"

    def test(self):
        input_data, label_data = data_helpers.load_test_data()
        n_steps = 3
        n_input = 1
        n_classes = 1
        n_hidden = self.n_hidden
        batch_size = self.batch_size

        new_rnn = RNN(n_steps=n_steps, n_input=n_input,
                      n_hidden=self.n_hidden, n_classes=n_classes)

        saver = tf.train.Saver(tf.all_variables(), max_to_keep=1)
        with tf.Session() as sess:
            ckpt = tf.train.get_checkpoint_state(self.ckpt_dir)
            if ckpt and ckpt.model_checkpoint_path:
                model_checkpoint_path_arr = ckpt.model_checkpoint_path.split("/")
                abs_model_checkpoint_path = self.ckpt_dir + '/' + model_checkpoint_path_arr[-1]
                saver.restore(sess, abs_model_checkpoint_path)
                test_len = len(input_data)
                test_data, test_label = data_helpers.get_random_batch(input_data, label_data, test_len)
                test_data = test_data.reshape((batch_size, n_steps, n_input))
                print "Testing Accuracy:", sess.run(new_rnn.accuracy, feed_dict={new_rnn.x: test_data, new_rnn.y: test_label,
                                                                                 new_rnn.istate: np.zeros((test_len, 2*n_hidden))})

    def predict(self, input_data=None):
        if input_data is None:
            input_data = data_helpers.load_predict_data()
        label_data = [[0.0] for _ in input_data]
        predict_labels = label_data
        n_steps = len(input_data[0])
        # n_steps = 3
        n_input = 1
        n_classes = 1
        n_hidden = self.n_hidden
        batch_size = self.batch_size

        with tf.Graph().as_default():
            new_rnn = RNN(n_steps=n_steps, n_input=n_input,
                          n_hidden=self.n_hidden, n_classes=n_classes)

            saver = tf.train.Saver(tf.all_variables(), max_to_keep=1)
            with tf.Session() as sess:
                ckpt = tf.train.get_checkpoint_state(self.ckpt_dir)
                if ckpt and ckpt.model_checkpoint_path:
                    model_checkpoint_path_arr = ckpt.model_checkpoint_path.split("/")
                    abs_model_checkpoint_path = self.ckpt_dir + '/' + model_checkpoint_path_arr[-1]
                    saver.restore(sess, abs_model_checkpoint_path)
                    test_len = len(input_data)
                    test_data, _ = data_helpers.get_random_batch(input_data, label_data, test_len)
                    test_data = test_data.reshape((test_len, n_steps, n_input))
                    feed_dict = {new_rnn.x: test_data,
                                 new_rnn.istate: np.zeros((test_len, 2*n_hidden))}
                    predict_labels = sess.run(new_rnn.pred, feed_dict=feed_dict)
                    print "Predict label:", predict_labels
        return predict_labels

# new_predict = Predict(n_hidden=12, batch_size=2, out_dir='models')
# new_predict.predict()
