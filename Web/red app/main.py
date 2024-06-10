from flask import Flask
from public import public
from admin import admin
from blood_bank import blood_bank
from hospital import hospital
from organization import organization
from api import api

app=Flask(__name__)
app.secret_key='redapp'

app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(blood_bank,url_prefix='/blood_bank')
app.register_blueprint(hospital,url_prefix='/hospital')
app.register_blueprint(organization,url_prefix='/organization')
app.register_blueprint(api,url_prefix='/api')

app.run(debug=True,  port=5003,host ="0.0.0.0")