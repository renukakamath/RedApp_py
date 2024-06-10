from flask import *
from database import *
import uuid
public=Blueprint('public',__name__)

@public.route('/')
def home():
	return render_template("index.html")


@public.route('/login',methods=['get','post'])
def login():
	session.clear()
	if 'submit' in request.form:
		username=request.form['username']
		password=request.form['password']
		q="SELECT * FROM `login` WHERE `username`='%s' AND `password`='%s'"%(username,password)
		res=select(q)
		if not res:
			flash('INCORRECT USERNAME OR PASSWORD')
		if res:
			session['lid']=res[0]['login_id']
			if res[0]['usertype']=='admin':
				flash('HELLO ADMIN')
				return redirect(url_for("admin.admin_home"))
			if res[0]['usertype']=='blood_bank':
				flash('HELLO BLOOD BANK')
				q="SELECT * FROM `bloodbank` WHERE `login_id`='%s'"%(res[0]['login_id'])
				res1=select(q)
				session['bid']=res1[0]['bank_id']
				bid=session['bid']
				return redirect(url_for("blood_bank.blood_bank_home"))
			if res[0]['usertype']=='hospital':
				flash('HELLO HOSPITAL')
				q="SELECT * FROM `hospitals` WHERE `login_id`='%s'"%(res[0]['login_id'])
				res1=select(q)
				session['hid']=res1[0]['hospital_id']
				return redirect(url_for("hospital.hospital_home"))
			if res[0]['usertype']=='organization':
				flash('HELLO ORGANIZATION')
				q="SELECT * FROM `organisation` WHERE `login_id`='%s'"%(res[0]['login_id'])
				res1=select(q)
				session['oid']=res1[0]['organisation_id']
				return redirect(url_for("organization.organization_home"))

	return render_template("login.html")



@public.route('/blood_bank_register',methods=['get','post'])
def blood_bank_register():
	if 'submit' in request.form:
		name=request.form['name']
		place=request.form['place']
		latitude=request.form['latitude']
		longitude=request.form['longitude']
		number=request.form['number']
		email=request.form['email']
		uname=request.form['uname']
		pwd=request.form['pwd']
		q="SELECT * FROM `login` WHERE `username`='%s'"%(uname)
		res=select(q)
		if res:
			flash('USERNAME ALREADY EXIST')
			return redirect(url_for('public.blood_bank_register'))
		else:
			q="INSERT INTO `login`(`username`,`password`,`usertype`) VALUES ('%s','%s','blood_bank')"%(uname,pwd)
			log_id=insert(q)
			q="INSERT INTO `bloodbank`(`login_id`,`bankname`,`place`,`latitude`,`longitude`,`phone`,`email`) VALUES ('%s','%s','%s','%s','%s','%s','%s')"%(log_id,name,place,latitude,longitude,number,email)
			insert(q)
			flash('REGISTERED')
			return redirect(url_for('public.login'))
	return render_template("blood_bank_register.html")








@public.route('/hospital_register',methods=['get','post'])
def hospital_register():
	if 'submit' in request.form:
		name=request.form['name']
		place=request.form['place']
		latitude=request.form['latitude']
		longitude=request.form['longitude']
		number=request.form['number']
		email=request.form['email']
		uname=request.form['uname']
		pwd=request.form['pwd']
		q="SELECT * FROM `login` WHERE `username`='%s'"%(uname)
		res=select(q)
		if res:
			flash('USERNAME ALREADY EXIST')
			return redirect(url_for('public.blood_bank_register'))
		else:
			q="INSERT INTO `login`(`username`,`password`,`usertype`) VALUES ('%s','%s','hospital')"%(uname,pwd)
			log_id=insert(q)
			q="INSERT INTO `hospitals`(`login_id`,`hospital`,`place`,`latitude`,`longitude`,`phone`,`email`) VALUES ('%s','%s','%s','%s','%s','%s','%s')"%(log_id,name,place,latitude,longitude,number,email)
			insert(q)
			flash('REGISTERED')
			return redirect(url_for('public.login'))
	return render_template("hospital_register.html")


@public.route('/organization_register',methods=['get','post'])
def organization_register():
	if 'submit' in request.form:
		name=request.form['name']
		phone=request.form['phone']
		email=request.form['email']
		uname=request.form['uname']
		pwd=request.form['pwd']
		q="SELECT * FROM `login` WHERE `username`='%s'"%(uname)
		res=select(q)
		if res:
			flash('USERNAME ALREADY EXIST')
			return redirect(url_for('public.blood_bank_register'))
		else:
			q="INSERT INTO `login`(`username`,`password`,`usertype`) VALUES ('%s','%s','organization')"%(uname,pwd)
			log_id=insert(q)
			q="INSERT INTO `organisation`(`login_id`,`organization_name`,`phone`,`email`) VALUES ('%s','%s','%s','%s')"%(log_id,name,phone,email)
			insert(q)
			return redirect(url_for('public.login'))
	return render_template("organization_register.html")