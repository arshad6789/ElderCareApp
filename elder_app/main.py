from flask import *

from public import public
from admin import admin
from api import api


app=Flask(__name__)


app.secret_key="hello"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(api,url_prefix='/api')




app.run(debug=True,port=5451,host="0.0.0.0")
# app.run(debug=True,port=5058,host="192.168.1.20")
