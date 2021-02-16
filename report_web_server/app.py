import json
import random
import time
import threading

from flask import Flask
from flask import render_template

GLOBAL_STATE = {'data': {}}


def update_data():
    while True:
        GLOBAL_STATE['data'] = {'a': random.randint(0, 100), 'b': random.randint(0, 1000)}
        time.sleep(5)


app = Flask(__name__)


@app.route("/")
def hello():
    return render_template('index.html')


@app.route("/report/getNumber")
def get_number():
    return json.dumps(GLOBAL_STATE['data'])


if __name__ == "__main__":
    daemon = threading.Thread(name='daemon', target=update_data)
    daemon.start()
    app.run(debug=True, port=8888)


