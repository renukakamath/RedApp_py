from flask import *
from database import *
hospital=Blueprint('hospital',__name__)

@hospital.route('/hospital_home',methods=['get','post'])
def hospital_home():
	if not session.get("lid") is None:
		data={}
		return render_template("hospital_home.html")
	else:
		return redirect(url_for('public.login'))


@hospital.route('/hospital_View_Request_for_Blood',methods=['get','post'])
def hospital_View_Request_for_Blood():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,CONCAT (`users`.`first_name`,' ',`users`.`last_name`) AS username FROM `request` INNER JOIN `users` USING (`user_id`) INNER JOIN `bloodgroups` USING (`group_id`) WHERE `requested_id`='%s' AND `type`='hospital' ORDER BY `date_time` DESC"%(session['hid'])
		data['accept']=select(q)
		q="SELECT *,CONCAT (`users`.`first_name`,' ',`users`.`last_name`) AS username FROM `request` INNER JOIN `users` USING (`user_id`) INNER JOIN `bloodgroups` USING (`group_id`) WHERE `status`='pending' ORDER BY `date_time` DESC"
		data['request']=select(q)
		if'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None

		if action=="accept":
			q="SELECT * FROM `request` WHERE `request_id`='%s'"%(id)
			res=select(q)
			q="SELECT * FROM `availablebloods` WHERE `group_id`='%s' AND `allocated_id`='%s' AND `type`='hospital' AND `stock`>'%s'"%(res[0]['group_id'],session['hid'],res[0]['unit_required'])
			res1=select(q)
			if res1:
				q="UPDATE `request` SET `requested_id`='%s',`type`='hospital',`status`='accepted' WHERE `request_id`='%s'"%(session['hid'],id)
				update(q)
				q="UPDATE `availablebloods` SET `stock`=`stock`-'%s' WHERE `ablood_id`='%s'"%(res[0]['unit_required'],res1[0]['ablood_id'])
				update(q)
				flash('ACCEPTED')
				return redirect(url_for('hospital.hospital_View_Request_for_Blood'))
			else:
				flash('UPDATE YOUR STOCK')
				return redirect(url_for('hospital.hospital_View_Request_for_Blood'))

		return render_template("hospital_View_Request_for_Blood.html",data=data)
	else:
		return redirect(url_for('public.login'))



@hospital.route('/hospital_Add_available_Bloods',methods=['get','post'])
def hospital_Add_available_Bloods():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM `availablebloods` INNER JOIN `bloodgroups` USING (`group_id`)  WHERE `allocated_id`='%s' AND `type`='hospital' GROUP BY `group_id`"%(session['hid'])
		data['available']=select(q)
		q="SELECT * FROM `bloodgroups`"
		data['blood']=select(q)
		if 'submit' in request.form:
			blood=request.form['blood']
			unit=request.form['unit']
			q="SELECT * FROM `availablebloods` WHERE `group_id`='%s' AND `allocated_id`='%s' AND `type`='hospital'"%(blood,session['hid'])
			res=select(q)
			if res:
				q="UPDATE `availablebloods` SET `stock`=`stock`+'%s',date_time=NOW()  WHERE `ablood_id`='%s'"%(unit,res[0]['ablood_id'])
				update(q)
				flash("DETAILS UPDATED")
				return redirect(url_for('hospital.hospital_Add_available_Bloods'))
			else:
				q="INSERT INTO `availablebloods`(`group_id`,`allocated_id`,`type`,`stock`,`date_time`) VALUES ('%s','%s','hospital','%s',NOW())"%(blood,session['hid'],unit)
				insert(q)
				flash("DETAILS ADDED")
				return redirect(url_for('hospital.hospital_Add_available_Bloods'))
		return render_template("hospital_Add_available_Bloods.html",data=data)
	else:
		return redirect(url_for('public.login'))
