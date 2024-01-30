from flask import *
from database import *
import uuid
# from summerize import *
import gensim
from gensim.summarization import summarize
admin=Blueprint('admin',__name__)
 

@admin.route('/adminhome',methods=['get','post'])
def adminhome():
	return render_template('adminhome.html')





@admin.route('/admin_view_feedback',methods=['get','post'])
def admin_view_feedback():
	data={}
	q="SELECT * FROM feedback INNER JOIN users using(user_id)"
	res=select(q)
	data['feedbacks']=res
	print(res)
	
	return render_template('admin_view_feedback.html',data=data)





@admin.route('/admin_manage_type',methods=['get','post'])
def admin_manage_type():
	data={}
	if 'submit' in request.form: 
		typ=request.form['typ']
		details=request.form['details']
		print("fffffffffffffff",details)
		from gensim.summarization import summarize
		summary = summarize(details, word_count=60)
		print("summary : ",summary)
		q="select * from news where news_heading='%s'"%(typ)
		res=select(q)
		if res:
			flash('THIS TYPE IS ALREADY ADDED')
			return redirect(url_for('admin.admin_manage_type'))
		else:
			q = "insert into news values(NULL, '%s', '%s', now(), '%s')" % (typ, details, summary)
			lid = insert(q)
			q = "insert into summary values(null, '%s', '%s')" % (lid, summary)
			insert(q)
			return redirect(url_for('admin.admin_manage_type'))

	q="select * from news"
	res=select(q)
	if res:
		data['type']=res
		# print(res)
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=='delete':
		q="delete from news where type_id='%s'"%(id)
		delete(q)
		return redirect(url_for('admin.admin_manage_type'))
	return render_template('admin_manage_type.html',data=data)






@admin.route('/admin_view_docterp',methods=['get','post'])
def admin_view_docterp():
	data={}

	# q="SELECT * FROM `users` INNER JOIN `billdetails` USING(`user_id`) INNER JOIN `payment` USING(`bill_id`)"
	q="SELECT  * FROM `appoinments` INNER JOIN `doctors` USING(`doctor_id`) INNER JOIN `users` USING(`user_id`)"
	res=select(q)
	print(res)
	data['orders']=res

	return render_template("admin_view_docterp.html",data=data)



@admin.route('/admin_view_doctors',methods=['get','post'])
def admin_view_doctors():
	data={}

	q="SELECT * FROM `doctors` inner join login using(login_id)"
	res=select(q)
	print(res)
	data['doct']=res

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None

	if action=="accept":
		q="update login set usertype='doctor' where login_id='%s'"%(id)
		update(q)
		return redirect(url_for('admin.admin_view_doctors'))

	if action=="reject":
		q="update login set usertype='rejected' where login_id='%s'"%(id)
		update(q)
		return redirect(url_for('admin.admin_view_doctors'))
	return render_template("admin_view_doctors.html",data=data)