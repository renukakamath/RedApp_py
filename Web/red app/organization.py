from flask import *
from database import *
organization=Blueprint('organization',__name__)

@organization.route('/organization_home',methods=['get','post'])
def organization_home():
	if not session.get("lid") is None:
		data={}
		return render_template("organization_home.html")
	else:
		return redirect(url_for('public.login'))


@organization.route('/organization_Manage_Camp',methods=['get','post'])
def organization_Manage_Camp():
	if not session.get("lid") is None:
		data={}
		if'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None

		if 'submit' in request.form:
			date=request.form['date']
			time=request.form['time']
			latitude=request.form['latitude']
			longitude=request.form['longitude']
			q="INSERT INTO `camp`(`organization_id`,`allocates_id`,`allocates_type`,`date`,`time`,`latitude`,`longitude`) VALUES ('%s','%s','%s','%s','%s','%s','%s')"%(session['oid'],id,action,date,time,latitude,longitude)
			insert(q)
			flash('CAMP DETAILS ADDED')
			return redirect(url_for('organization.organization_view_hospitals_and_bank',action=action,id=id))
		return render_template("organization_Manage_Camp.html",data=data)
	else:
		return redirect(url_for('public.login'))



@organization.route('/organization_view_hospitals_and_bank',methods=['get','post'])
def organization_view_hospitals_and_bank():
	if not session.get("lid") is None:
		data={}
		if'action' in request.args:
			action=request.args['action']
		else:
			action=None

		if action=="hospitals":
			q="SELECT * FROM `hospitals` ORDER BY `hospital`"
			data['hospital']=select(q)
		if action=="bloodbank":
			q="SELECT * FROM `bloodbank` ORDER BY `bankname`"
			data['bank']=select(q)
		if action=="camp":
			q="SELECT * FROM (( (SELECT `camp_id`,`organization_id`,`allocates_type`,`date`,`time`,`camp`.`latitude`,`camp`.`longitude`,`hospitals`.`hospital` AS allocate FROM `camp` INNER JOIN `hospitals` ON (`camp`.`allocates_id`=`hospitals`.`hospital_id`) WHERE `allocates_type`='hospital' AND `organization_id`='%s') UNION (SELECT `camp_id`,`organization_id`,`allocates_type`,`date`,`time`,`camp`.`latitude`,`camp`.`longitude`,`bloodbank`.`bankname` AS allocate FROM `camp` INNER JOIN `bloodbank` ON (`camp`.`allocates_id`=`bloodbank`.`bank_id`) WHERE `allocates_type`='blood_bank' AND `organization_id`='%s') )temp )ORDER BY `camp_id` DESC"%(session['oid'],session['oid'])
			data['camp']=select(q)
		if action=='update':
			id=request.args['id']
			q="SELECT * FROM `camp` WHERE `camp_id`='%s'"%(id)
			data['up']=select(q)
		if 'submit' in request.form:
			date=request.form['date']
			time=request.form['time']
			latitude=request.form['latitude']
			longitude=request.form['longitude']
			q="UPDATE `camp` SET `date`='%s',`time`='%s',`latitude`='%s',`longitude`='%s' WHERE `camp_id`='%s'"%(date,time,latitude,longitude,id)
			update(q)
			return redirect(url_for('organization.organization_view_hospitals_and_bank',action="camp"))
		return render_template("organization_view_hospitals_and_bank.html",data=data)
	else:
		return redirect(url_for('public.login'))




@organization.route('/organization_camp_blood',methods=['get','post'])
def organization_camp_blood():
	if not session.get("lid") is None:
		data={}
		id=request.args['id']
		if'action' in request.args:
			action=request.args['action']
		else:
			action=None

		if action=="delete":
			did=request.args['did']
			q="DELETE FROM `campblood` WHERE `cblood_id`='%s'"%(did)
			delete(q)
			return redirect(url_for('organization.organization_camp_blood',id=id))
		
		q="SELECT * FROM `bloodgroups` ORDER BY `group`"
		data['blood']=select(q)
		q="SELECT * FROM `campblood` INNER JOIN `bloodgroups` USING (`group_id`) WHERE `camp_id`='%s'"%(id)
		data['camp_blood']=select(q)
		if 'submit' in request.form:
			blood=request.form['blood']
			units=request.form['units']
			q="SELECT * FROM `campblood`  WHERE `camp_id`='%s' AND `group_id`='%s'"%(id,blood)
			res0=select(q)
			if res0:
				flash('DETAILS ALREADY INSERTED')
				return redirect(url_for('organization.organization_camp_blood',action=action,id=id))
			else:

				q="SELECT * FROM `camp` WHERE `camp_id`='%s'"%(id)
				res=select(q)
				if res:
					if res[0]['allocates_type']=='hospital':
						q="SELECT * FROM `availablebloods` WHERE `group_id`='%s' AND `allocated_id`='%s' AND `type`='hospital'"%(blood,res[0]['allocates_id'])
						res1=select(q)
						if res1:
							q="UPDATE `availablebloods` SET `stock`=`stock`+'%s',date_time=NOW()  WHERE `ablood_id`='%s'"%(units,res1[0]['ablood_id'])
							update(q)
							flash("DETAILS UPDATED")
							q="INSERT INTO `campblood`(`camp_id`,`group_id`,`no_of_units`) VALUES ('%s','%s','%s')"%(id,blood,units)
							insert(q)
							return redirect(url_for('organization.organization_camp_blood',action=action,id=id))
						else:
							q="INSERT INTO `availablebloods`(`group_id`,`allocated_id`,`type`,`stock`,`date_time`) VALUES ('%s','%s','hospital','%s',NOW())"%(blood,res[0]['allocates_id'],units)
							insert(q)
							flash("DETAILS ADDED")
							q="INSERT INTO `campblood`(`camp_id`,`group_id`,`no_of_units`) VALUES ('%s','%s','%s')"%(id,blood,units)
							insert(q)
							return redirect(url_for('organization.organization_camp_blood',action=action,id=id))
					if res[0]['allocates_type']=='blood_bank':
						q="SELECT * FROM `availablebloods` WHERE `group_id`='%s' AND `allocated_id`='%s' AND `type`='blood_bank'"%(blood,res[0]['allocates_id'])
						res1=select(q)
						if res1:
							q="UPDATE `availablebloods` SET `stock`=`stock`+'%s',date_time=NOW()  WHERE `ablood_id`='%s'"%(units,res1[0]['ablood_id'])
							update(q)
							flash("DETAILS UPDATED")
							q="INSERT INTO `campblood`(`camp_id`,`group_id`,`no_of_units`) VALUES ('%s','%s','%s')"%(id,blood,units)
							insert(q)
							return redirect(url_for('organization.organization_camp_blood',action=action,id=id))
						else:
							q="INSERT INTO `availablebloods`(`group_id`,`allocated_id`,`type`,`stock`,`date_time`) VALUES ('%s','%s','blood_bank','%s',NOW())"%(blood,res[0]['allocates_id'],units)
							insert(q)
							flash("DETAILS ADDED")
							q="INSERT INTO `campblood`(`camp_id`,`group_id`,`no_of_units`) VALUES ('%s','%s','%s')"%(id,blood,units)
							insert(q)
							return redirect(url_for('organization.organization_camp_blood',action=action,id=id))
		return render_template("organization_camp_blood.html",data=data)
	else:
		return redirect(url_for('public.login'))
