from frontend import app
from flask import render_template

@app.route('/', methods=['GET'])
def frontend():
    return render_template('index.html')