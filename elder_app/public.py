from flask import *
from database import *

public=Blueprint('public',__name__)

@public.route('/',methods=['get','post'])
def home():
	session.clear()
	
	return render_template('home.html')

@public.route('/login',methods=['get','post'])
def login():
	if 'submit' in request.form:
		uname=request.form['uname']
		password=request.form['password']
		q="select * from login where username='%s' and password='%s'"%(uname,password)
		res=select(q)
		if res:
			session['lid']=res[0]['login_id']

			if res[0]['usertype']=='admin':
				return redirect(url_for('admin.adminhome'))
		else:
			flash("COMPLETE REGISTRATION BEFORE LOGIN")
	return render_template('login.html')

