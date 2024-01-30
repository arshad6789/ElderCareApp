from flask import *
from database import *
import uuid
i=Blueprint('api',__name__)
@api.route('/login')
def login():
	data={}
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(username,password)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)

@api.route('/usermanagefeedback')
def usermanagefeedback():
	data={}
	lid=request.args['lid']
	complaint=request.args['complaint']
	q="insert into feedback values(null,(select user_id from users where login_id='%s'),'%s',curdate())"%(lid,complaint)
	insert(q)
	data['status']="success"
	data['method']="usermanagefeedback"
	return str(data)


@api.route('/userviewfeedback')
def userviewfeedback():
	data={}
	lid=request.args['lid']
	q="select * from feedback where user_id=(select user_id from users where login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewfeedback"
	return str(data)


@api.route('/userregister')
def userregister():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']
	place=request.args['place']
	email=request.args['email']
	phone=request.args['phone']
	dob=request.args['dob']
	district=request.args['district']
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s'"%(username)
	res=select(q)
	if res:
		data['status']="duplicate"
	else:
		q="insert into login values(null,'%s','%s','user')"%(username,password)
		id=insert(q)
		q="insert into users values(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,dob,phone,email,place,district)
		insert(q)
		data['status']="success"
	return str(data)



@api.route('/doctorregister')
def doctorregister():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']
	place=request.args['place']
	email=request.args['email']
	phone=request.args['phone']
	username=request.args['username']
	password=request.args['password']
	licen=request.args['licen']
	quali=request.args['quali']
	q="select * from login where username='%s'"%(username)
	res=select(q)
	if res:
		data['status']="duplicate"
	else:
		q="insert into login values(null,'%s','%s','pending')"%(username,password)
		id=insert(q)
		q="insert into doctors values(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,phone,email,quali,licen)
		insert(q)
		data['status']="success"
	return str(data)











@api.route('/userview_doctors')
def userview_doctors():
	data={}
	q="SELECT * FROM `doctors`"
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)










@api.route('/usermakepayment')
def usermakepayment():
	data={}
	mid=request.args['mid']
	pid=request.args['pid']
	quantity=request.args['quantity']
	amount=request.args['amount']
	q="insert into payment values(null,'%s','%s',curdate())"%(pid,amount)
	insert(q)
	q="update `uploadprescription` set status='paid' where prescription_id='%s'"%(pid)
	update(q)
	q="update medicines set quantity=quantity-'%s' where medicine_id='%s'"%(quantity,mid)
	update(q)
	data['status']="success"
	return str(data)






@api.route('/doctorviewappoinments')
def doctorviewappoinments():
	data={}
	
	lid=request.args['lid']
	q="select * from appoinments inner join users using(user_id) where doctor_id=(select doctor_id from doctors where login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/doctorview_customers')
def doctorview_customers():
	data={}
	
	app_id=request.args['app_id']
	q="select * from users inner join appoinments using(user_id) where appoinment_id='%s'"%(app_id)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)





@api.route('/user_view_doctor_prescription')
def user_view_doctor_prescription():
	data={}
	
	appoi_id=request.args['appoi_id']
	q="select * from prescription inner join appoinments using(appoinment_id) where appoinment_id='%s'"%(appoi_id)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)







@api.route('/doctor_upload_prescription',methods=['get','post'])
def doctor_upload_prescription():
	data={}
	
	app_id=request.form['app_id']
	image=request.files['image']
	path="static/"+str(uuid.uuid4())+image.filename
	image.save(path)
	q="insert into prescription values(null,'%s','%s',curdate(),'pending')"%(app_id,path)
	insert(q)
	data['status']="success"
	return str(data)


@api.route('/User_view_news')
def User_view_news():
	data={}
	news_ids=request.args['news_ids']
	q="select * from news inner join summary on `news`.`type_id`=`summary`.`news_id` where news_id='%s'"%(news_ids)
	res=select(q)
	if res:
		data['status']="success"

		data['data']=res
	else:
		data['status']="failed"
	data['method']='User_view_news'
	return str(data)

@api.route('/User_news')
def User_news():
	data={}
	q="select * from news inner join summary on `news`.`type_id`=`summary`.`news_id` "
	res=select(q)
	if res:
		data['status']="success"

		data['data']=res
	else:
		data['status']="failed"
	data['method']='User_news'
	return str(data)












# @api.route('/viewmedicalshop')
# def viewmedicalshop():
# 	data={}
# 	q="select * from medicalshop"
# 	res=select(q)
# 	if res:
# 		data['status']="success"
# 		data['data']=res 
# 	else:
# 		data['status']="failed"
# 		data['method']='viewmedicalshop'
# 	return str(data)


@api.route('/user_upload_doctor_prescription_to_medical_shop')
def user_upload_doctor_prescription_to_medical_shop():
	data={}
	img=request.args['file']
	datae=request.args['date']
	statusss=request.args['statusss']
	medicalshop=request.args['medicalshop']
	lid=request.args['lid']
	q="insert into uploadprescription values(null,'%s',(select user_id from users where login_id='%s'),'%s','0',curdate(),'pending')" %(medicalshop,lid,img)
	insert(q)
	data['status']="success"
	data['method']='user_upload_doctor_prescription_to_medical_shop'
	return str(data)













@api.route('/emg_remove',methods=['get','post'])
def emg_remove():

	data={}
	contact_ids=request.args['contact_ids']

	q= "delete from contacts where contact_id='%s'"% (contact_ids)
	print(q)
	delete(q)
	data['status'] = 'success'
	
	data['method'] = 'emg_remove'
	return str(data)





@api.route('/Customer_send_complaint')
def Customer_send_complaint():
    data={}
    log_id=request.args['log_id']
    complaint=request.args['complaint']
    q="INSERT INTO `complaint` VALUES(NULL,(SELECT `user_id` FROM `users` WHERE `login_id`='%s'),'%s','pending',NOW())"%(log_id,complaint)
    print(q)
    res=insert(q)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']="Customer_send_complaint"
    return str(data)


@api.route('/view_complaint')
def view_complaint():
    data={}
    log_id=request.args['log_id']
    q="SELECT * FROM `complaint` WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(log_id)
    res=select(q)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']="view_complaint"
    return str(data)

@api.route('/User_view_food_details')
def User_view_food_details():
	data={}
	# log_id=request.args['log_id']
	appoint_id=request.args['appoint_id']
	q="SELECT * FROM `food` WHERE appoinment_id='%s'"%(appoint_id)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	data['method']="User_view_food_details"
	return str(data)



@api.route('/userviewprofile/')
def userviewprofile():
	data={}
	log=request.args['login_id']
	q="SELECT * from doctors where login_id='%s' "%(log)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
		data['method']='userviewprofile'
	else:
		data['status']='failed'
		data['method']='userviewprofile'
	return str(data)

@api.route('/userupprof')
def userupprof():
	data={}
	fname = request.args['fname']
	lname = request.args['lname']
	place= request.args['place']
	mail = request.args['email']
	phn = request.args['phone']
	# print(phn)
	logid=request.args['logid']
	q="update doctors set fname='%s',lname='%s', place='%s',phone='%s',email='%s' where login_id='%s' "%(fname,lname,place,phn,mail,logid)
	update(q)
	data['status']='success'
	data['method']='userupprof'
	return str(data)

@api.route('/User_download_files',methods=['get','post'])
def User_download_files():
	data={}
	log_id=request.args['log_id']
	# q="select * from student_submissions inner join assignment using(assignment_id) where student_id=(select student_id from student where login_id='%s')"%(lid)
	# q="SELECT `customer`.`login_id`,`customer`.`customer_id`,`product`.`product_id`,`product`.`product`,`assign_work`.`assign_work_id`,`file_upload`.`upload_id`,`file_upload`.`file` FROM `customer`,`product`,`assign_work`,`file_upload` WHERE `customer`.`customer_id`=(SELECT `customer_id` FROM `customer` WHERE `login_id`='%s') group by product"%(log_id)
	q=" SELECT *,prescription.`date` AS prisdate FROM `prescription` INNER JOIN `appoinments` USING(`appoinment_id`) INNER JOIN `doctors` USING(`doctor_id`) WHERE `user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')"%(log_id)
	print(q)
	res=select(q)
	print(res)
	print(q)
	data['status']="success"
	data['data']=res
	return str(data)


@api.route('/viewfood')
def viewfood	():
	data={}
	log_id=request.args['log_id']
	appoint_id=request.args['appoint_id']
	q="SELECT * FROM `food` WHERE `doctor_id`=(SELECT `doctor_id` FROM `doctors` WHERE `login_id`='%s') and appoinment_id='%s'"%(log_id,appoint_id)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	data['method']="viewfood"
	return str(data)

@api.route('/Doctor_update_food_details')
def Doctor_update_food_details():
	data={}
	log_id=request.args['log_id']
	appoint_id=request.args['appoint_id']
	times=request.args['time']
	food=request.args['food']
	q="INSERT INTO `food` VALUES (NULL,'%s',(SELECT `doctor_id` FROM `doctors` WHERE `login_id`='%s'),'%s','%s')"%(appoint_id,log_id,times,food)
	res=insert(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	data['method']="Doctor_update_food_details"

	return str(data)
@api.route('/accept',methods=['get','post'])
def accept():

	data={}
	appoint_id=request.args['appoint_id']

	q= "update appoinments set status='accept' where appoinment_id='%s'"%(appoint_id) 
	print(q)
	update(q)
	data['status'] = 'success'
	
	data['method'] = 'approve'
	return str(data)


from datetime import datetime

@api.route('/usermakeappointments')
def usermakeappointments():
	data={}
	did=request.args['did']
	date=request.args['date']
	reminder=request.args['reminder']
	time=request.args['time']
	lid=request.args['lid']
	q="insert into appoinments values(null,'%s',(select user_id from users where login_id='%s'),'%s','%s','pending','%s','0','pending')"%(did,lid,date,time,reminder)
	insert(q)
	data['status']="success"
	data['method']="addreminder"
	return str(data)

@api.route('/view_reminder')
def view_reminder():
	data={}
	lid=request.args['uid']
	q="select * from appoinments inner join doctors using(doctor_id) where user_id=(select user_id from users where login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="view_reminder"
	return str(data)

@api.route('/updatepasslocation')
def updatepasslocation():
	log_id=request.args['log_id']
	data={}
	from datetime import datetime

	# Get the current time
	current_time = datetime.now().time()

	# Format the current time as "10:00"
	current_time_formatted = current_time.strftime("%H:%M")+'%'

	print(current_time_formatted)  # Output: Current time in the format "10:00"

	q="select * from appoinments where user_id=(select user_id from users where login_id=%s) and time like '%s'" % (log_id,current_time_formatted)
	res=select(q)
	# q="select * from billdetails where user_id=(select user_id from users where login_id=%s) and bill_time like '%s'" % (log_id,current_time_formatted)
	# res=select(q)
	q="select * from medication where user_id=(select user_id from users where login_id=%s) and time like '%s'" % (log_id,current_time_formatted)
	res=select(q)
	print(q)
	if res:
		data['status']='success'
		data['data']=res
		print("xxxxxxxxxxxxxxxxxx",data['data'])
	else:
		data['status']='failed'
	data['method']='updatepasslocation'
	return str(data)

@api.route('/userviewappoinments')
def userviewappoinments():
	data={}
	
	lid=request.args['lid']
	q="select * from appoinments inner join doctors using(doctor_id) where user_id=(select user_id from users where login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/User_add_bills')
def User_add_bills():
	data={}
	date=request.args['date']
	reminder=request.args['reminder']
	time=request.args['time']
	lid=request.args['lid']
	amounts=request.args['amounts']
	q="insert into billdetails values(null,(select user_id from users where login_id='%s'),'%s','%s','%s','%s','payment pending')"%(lid,date,time,amounts,reminder)
	insert(q)
	data['status']="success"
	data['method']="User_add_bills"
	return str(data)

@api.route('/view_bill_details')
def view_bill_details():
	data={}
	lid=request.args['uid']
	q="select * from billdetails where user_id=(select user_id from users where login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="view_bill_details"
	return str(data)

@api.route('/payment')
def payment():
	data={}
	# work=request.args['Work']
	amount=request.args['Amount']
	log_id=request.args['log_id']
	bill_id=request.args['bill_id']
	
	q="insert into payment values(null,'%s',(select user_id from users where login_id='%s'),'%s',curdate())"%(bill_id,log_id,amount)
	print(q)
	res1=insert(q)
	q="update billdetails set bill_status='paid' where bill_id='%s'"%(bill_id)
	print(q)
	res1=update(q)
	if res1:
		data['status']="success"
		data['data']=res1
	else:
		data['status']="failed"
	data['method']="payment"
	return str(data)


from datetime import datetime, timedelta

@api.route('/User_medication_details')
def User_medication_details():
	data = {}
	dates = request.args.getlist('date')
	reminder = request.args['reminder']
	time = request.args['time']
	lid = request.args['lid']
	patient_id = request.args['patient_id']
	weekly = request.args['weekly']
	daily = request.args['daily']

	from datetime import datetime, timedelta



	if weekly:
		for date in dates:
			current_date = datetime.strptime(date, '%m/%d/%Y').date()
			print("_______________________________________",current_date)
			for i in range(7):
				# mydate =  timedelta(days=i)
				# print("////////////////////////////////", mydate)
				mydate = datetime.now() + timedelta(days=i)
				formatted_date = mydate.strftime("%Y-%m-%d")
				print("////////////////////////////////", formatted_date)
				q = "INSERT INTO medication VALUES (null, '%s', '%s', '%s', '%s', (SELECT doctor_id FROM doctors WHERE login_id='%s'))" % (patient_id, reminder, formatted_date, time, lid)
				res = insert(q)
		        # data['data'].append(res)

	if daily:
		for dates in dates:
			current_date = datetime.strptime(dates, '%m/%d/%Y').date()
			new_date = current_date.strftime("%Y-%m-%d")
			q2 = "INSERT INTO medication VALUES (null, '%s', '%s', '%s', '%s', (SELECT doctor_id FROM doctors WHERE login_id='%s'))" % (patient_id, reminder, new_date, time, lid)
			res = insert(q2)
		   

	data['status'] = "success"
	data['method'] = "User_medication_details"
	return str(data)




@api.route('/view_medication')
def view_medication():
	data={}
	lid=request.args['uid']
	patient_id=request.args['patient_id']
	q="select * from medication  where doctor_id=(select doctor_id from doctors where login_id='%s') and user_id='%s'"%(lid,patient_id)
	print("xxxxxxxxxxxxeeeeeeeeeeeeeeeeeeeee",q)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="view_medication"
	return str(data)


@api.route('/User_view_medication_details')
def User_view_medication_details():
	data={}
	lid=request.args['log_id']
	q="select * from medication  where user_id=(select user_id from users where login_id='%s')"%(lid)
	print("xxxxxxxxxxxxeeeeeeeeeeeeeeeeeeeee",q)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="User_view_request"
	return str(data)

@api.route('/User_view_appo')
def User_view_appo():
	data={}
	lid=request.args['aid']
	q="select * from appoinments  where appoinment_id='%s'"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="User_view_appo"
	return str(data)
@api.route('/delete_medication',methods=['get','post'])
def delete_medication():

	data={}
	medication_ids=request.args['medication_ids']

	q= "DELETE FROM `medication` WHERE  `medication_id`='%s'"%(medication_ids) 
	print(q)
	update(q)
	data['status'] = 'success'
	data['method']="delete_medication"
	return str(data)

@api.route('/Add_amount')
def Add_amount():
	data={}
	lid=request.args['log_id']
	amt=request.args['amt']
	q="update appoinments set appoin_amount='%s' where doctor_id=(select doctor_id from doctors where login_id='%s')"%(amt,lid)
	res=update(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	
	return str(data)
@api.route('/apayment')
def apayment():
	data={}
	mid=request.args['mid']
	# pid=request.args['pid']
	amount=request.args['Amount']
	q="update `appoinments` set status='paid', app_status='paid' where appoinment_id='%s'"%(mid)
	update(q)
	data['status']="success"
	return str(data)

