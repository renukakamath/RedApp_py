from flask import Blueprint, request
import demjson
from database import *

def encode(data):
	return demjson.encode(data)

api = Blueprint('api', __name__)

@api.route('/login/', methods=['get', 'post'])
def login():
	data = {}
	username = request.args['username']
	password = request.args['password']
	q = "select * from login where username = '%s' and password = '%s' " %(username, password)
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'login'
	return encode(data)

@api.route('/register/', methods=['get', 'post'])
def register():
	data = {}
	first_name = request.args['first_name']
	last_name = request.args['last_name']
	age = request.args['age']
	blood_group = request.args['blood_group']
	phone = request.args['phone']
	email = request.args['email']
	username = request.args['username']
	password = request.args['password']
	types=request.args['type']

	q_log = "INSERT INTO login (`username`, `password`, `usertype`) VALUES ('%s', '%s', '%s')" %(username, password,types)
	res_log = insert(q_log)
	if res_log:
		q = "INSERT INTO `users` (`login_id`, `first_name`, `last_name`, `age`, group_id, `phone`, `email`,utype) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s','%s')" %(res_log, first_name, last_name, age, blood_group, phone, email,types)
		res = insert(q)
		if res:
			data['status'] = 'success'
		else:
			data['status'] = 'failed'
	else:
		data['status'] = 'failed'
	data['method'] = 'register'
	return encode(data)

@api.route('/get_groups/', methods=['get', 'post'])
def get_groups():
	data = {}
	q = "SELECT * FROM `bloodgroups`"
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'get_groups'
	return encode(data)

@api.route('/available_bloods/', methods=['get', 'post'])
def available_bloods():
	data = {}
	gid=request.args['oid']
	q = "SELECT * FROM `availablebloods` INNER JOIN `bloodgroups` USING (group_id)  where group_id='%s'"%(gid)
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'available_bloods'
	return encode(data)

@api.route('/send_request/', methods=['get', 'post'])
def send_request():
	data = {}

	blood = request.args['blood']
	unit = request.args['unit']
	login_id = request.args['login_id']

	q = "INSERT INTO `request` (`user_id`, `requested_id`, `type`, `group_id`, `unit_required`, `date_time`, `status`) VALUES ((SELECT `user_id` FROM `users` WHERE `login_id` = '%s'), '0', 'pending', '%s', '%s', NOW(), 'pending')" %(login_id, blood, unit)
	res = insert(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'send_request'
	return encode(data)

@api.route('/my_requests/', methods=['get', 'post'])
def my_requests():
	data = {}

	login_id = request.args['login_id']

	q = "SELECT * FROM `request` INNER JOIN `bloodgroups` USING (`group_id`) WHERE `user_id` = (SELECT `user_id` FROM `users` WHERE `login_id` = '%s') " %(login_id)
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'my_requests'
	return encode(data)

@api.route('/request_details/', methods=['get', 'post'])
def request_details():
	data = {}

	request_id = request.args['request_id']

	q = "SELECT * FROM `request` WHERE `request_id` = '%s'" %(request_id)
	res = select(q)
	print(q)
	if res:
		qry = "SELECT `bankname` AS `name`, `place`, `phone`, `email` FROM `request` INNER JOIN `bloodbank` ON `requested_id` = `bank_id` WHERE `request_id` = '%s'" %(request_id)
		res_qry = select(qry)
		print(qry)
		data['data'] = res_qry
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'request_details'
	return encode(data)

@api.route('/view_camps/', methods=['get', 'post'])
def view_camps():
	data = {}

	q = "SELECT * FROM `camp` inner join campblood using (camp_id) inner join bloodgroups using (group_id) INNER JOIN `organisation` ON `organization_id` = `organisation_id` ORDER BY `camp_id` DESC"
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_camps'
	return encode(data)

@api.route('/Vieworganisation', methods=['get', 'post'])
def Vieworganisation():
	data = {}
	oid=request.args['oid']

	q = "SELECT * FROM `organisation` where organisation_id='%s'"%(oid)
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_camps'
	return encode(data)


@api.route('/Viewrequirement', methods=['get', 'post'])
def Viewrequirement():
	data = {}
	log_id=request.args['log_id']
	

	q = "SELECT  * FROM `requirement_message` INNER JOIN `bloodbank` USING (`bank_id`) INNER JOIN  `bloodgroups` USING (`group_id`) INNER JOIN users ON users.user_id=`requirement_message`.donar_id   where  users.login_id='%s'"%(log_id)
	res = select(q)
	print(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_camps'
	return encode(data)


@api.route('/availability', methods=['get', 'post'])
def availability():
	data = {}
	uid=request.args['uid']
	

	q = "insert into status values(null,'%s','availability',now())"%(uid)
	insert(q)
	data['status'] = 'success'
	
	data['method'] = 'view_camps'
	return encode(data)


@api.route('/Viewdonations', methods=['get', 'post'])
def Viewdonations():
	data = {}
	log_id=request.args['log_id']
	

	q = "SELECT * FROM `status` INNER JOIN `users` ON status.`donar_id`=`users`.`user_id` INNER JOIN `bloodgroups` USING (group_id)  WHERE login_id='%s'"%(log_id)
	res = select(q)
	print(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_camps'
	return encode(data)



@api.route('/viewusers')
def viewusers():
	data={}
	login_id=request.args['lid']
	q="select * from users where login_id='%s'"%(login_id)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
		data['method']='viewusers'
	return str(data)

@api.route('/updateuser')	
def updateuser():
	data={}
	name=request.args['name']
	place=request.args['place']
	phone=request.args['Phone']
	email=request.args['email']
	age=request.args['age']
	login_id=request.args['login_id']

	q="update `users` set first_name='%s',last_name='%s',phone='%s',email='%s',age='%s' where login_id='%s'"%(name,place,phone,email,age,login_id)
	update(q)
	print(q)
	data['status']='success'
	data['method']='updateuser'
	return str(data)



@api.route('/dviewusers')
def dviewusers():
	data={}
	login_id=request.args['lid']
	q="select * from users where login_id='%s'"%(login_id)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
		data['method']='dviewusers'
	return str(data)

@api.route('/dupdateuser')	
def dupdateuser():
	data={}
	name=request.args['name']
	place=request.args['place']
	phone=request.args['Phone']
	email=request.args['email']
	age=request.args['age']
	login_id=request.args['login_id']

	q="update `users` set first_name='%s',last_name='%s',phone='%s',email='%s',age='%s' where login_id='%s'"%(name,place,phone,email,age,login_id)
	update(q)
	print(q)
	data['status']='success'
	data['method']='dupdateuser'
	return str(data)