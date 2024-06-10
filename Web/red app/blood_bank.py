from flask import *
from database import *
blood_bank=Blueprint('blood_bank',__name__)

@blood_bank.route('/blood_bank_home',methods=['get','post'])
def blood_bank_home():
	if not session.get("lid") is None:
		data={}
		
		return render_template("blood_bank_home.html",data=data)
	else:
		return redirect(url_for('public.login'))



@blood_bank.route('/blood_bank_View_Request_for_Blood',methods=['get','post'])
def blood_bank_View_Request_for_Blood():
	if not session.get("lid") is None:
		data={}
		bid=session['bid']
		q="SELECT *,CONCAT (`users`.`first_name`,' ',`users`.`last_name`) AS username FROM `request` INNER JOIN `users` USING (`user_id`) INNER JOIN `bloodgroups` ON `request`.group_id=`bloodgroups`.group_id WHERE `requested_id`='%s' AND status='accepted' ORDER BY `date_time` DESC"%(session['bid'])
		data['accept']=select(q)
		q="SELECT *,CONCAT (`users`.`first_name`,' ',`users`.`last_name`) AS username FROM `request` INNER JOIN `users` USING (`user_id`) INNER JOIN `bloodgroups` ON `request`.group_id=`bloodgroups`.group_id WHERE `status`='pending' ORDER BY `date_time` DESC"
		data['request']=select(q)
		if'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None

		if action=="accept":
			# q="SELECT * FROM `request` WHERE `request_id`='%s'"%(id)
			# res=select(q)
			# q="SELECT * FROM `availablebloods` WHERE `group_id`='%s' AND `allocated_id`='%s' AND `type`='blood_bank' AND `stock`>'%s'"%(res[0]['group_id'],session['bid'],res[0]['unit_required'])
			# res1=select(q)
			# if res1:
			# 	q="UPDATE `request` SET `requested_id`='%s',`type`='blood_bank',`status`='accepted' WHERE `request_id`='%s'"%(session['bid'],id)
			# 	update(q)
			# 	q="UPDATE `availablebloods` SET `stock`=`stock`-'%s' WHERE `ablood_id`='%s'"%(res[0]['unit_required'],res1[0]['ablood_id'])
			# 	update(q)
			# 	flash('ACCEPTED')
			# 	return redirect(url_for('blood_bank.blood_bank_View_Request_for_Blood'))
			# else:
			# 	flash('UPDATE YOUR STOCK')

			q="update request set status='accepted' , requested_id='%s' where request_id='%s'"%(bid,id)
			update(q)
			return redirect(url_for('blood_bank.blood_bank_View_Request_for_Blood'))



		if action=="reject":
			q="update request set status='reject' ,requested_id='0' where request_id='%s'"%(id)
			update(q)
			return redirect(url_for('blood_bank.blood_bank_View_Request_for_Blood'))

		return render_template("blood_bank_View_Request_for_Blood.html",data=data)
	else:
		return redirect(url_for('public.login'))



@blood_bank.route('/blood_bank_Add_available_Bloods',methods=['get','post'])
def blood_bank_Add_available_Bloods():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM `availablebloods` INNER JOIN `bloodgroups` USING (`group_id`)  WHERE `allocated_id`='%s' AND `type`='blood_bank' GROUP BY `group_id`"%(session['bid'])
		data['available']=select(q)
		q="SELECT * FROM `bloodgroups`"
		data['blood']=select(q)
		if 'submit' in request.form:
			blood=request.form['blood']
			unit=request.form['unit']
			q="SELECT * FROM `availablebloods` WHERE `group_id`='%s' AND `allocated_id`='%s' AND `type`='blood_bank'"%(blood,session['bid'])
			res=select(q)
			if res:
				q="UPDATE `availablebloods` SET `stock`=`stock`+'%s',date_time=NOW()  WHERE `ablood_id`='%s'"%(unit,res[0]['ablood_id'])
				update(q)
				flash("DETAILS UPDATED")
				return redirect(url_for('blood_bank.blood_bank_Add_available_Bloods'))
			else:
				q="INSERT INTO `availablebloods`(`group_id`,`allocated_id`,`type`,`stock`,`date_time`) VALUES ('%s','%s','blood_bank','%s',NOW())"%(blood,session['bid'],unit)
				insert(q)
				flash("DETAILS ADDED")
				return redirect(url_for('blood_bank.blood_bank_Add_available_Bloods'))
		return render_template("blood_bank_Add_available_Bloods.html",data=data)
	else:
		return redirect(url_for('public.login'))


@blood_bank.route('/bloodback_viewdonors',methods=['get','post'])
def bloodback_viewdonors():
	
		data={}
		q="SELECT * FROM `users`  inner join bloodgroups USING(group_id) where utype='donor'"
		res=select(q)
		data['res']=res
		
		return render_template("bloodback_viewdonors.html",data=data)

@blood_bank.route('/blood_bank_addrequirement_message',methods=['get','post'])
def blood_bank_addrequirement_message():
	
	data={}
	q="SELECT * FROM `requirement_message` INNER JOIN `bloodbank` USING (bank_id) INNER JOIN `bloodgroups` USING (group_id)"
	res=select(q)
	data['requ']=res


	q="select * from bloodgroups"
	res=select(q)
	data['blood']=res


	if "submit" in request.form:
		g=request.form['group']
		u=request.form['unit']
		d=request.form['description']
		did=request.args['did']
		q="insert into requirement_message values(null,'%s','%s','%s','%s','%s')"%(session['bid'],did,g,u,d)
		insert(q)
		return redirect(url_for('blood_bank.blood_bank_addrequirement_message'))
	
	return render_template("blood_bank_addrequirement_message.html",data=data)


@blood_bank.route('/bloodbank_viewrecivers',methods=['get','post'])
def bloodbank_viewrecivers():
	
		data={}
		q="SELECT * FROM `users`  inner join bloodgroups USING(group_id) where utype='receiver'"
		res=select(q)
		data['res']=res
		
		return render_template("bloodbank_viewrecivers.html",data=data)

@blood_bank.route('/bloodbank_distribution',methods=['get','post'])
def bloodbank_distribution():
	
	data={}
	q="SELECT * FROM `distribution` INNER JOIN `bloodgroups` USING (group_id)"
	res=select(q)
	data['requ']=res


	q="select * from bloodgroups"
	res=select(q)
	data['blood']=res


	if "submit" in request.form:
		g=request.form['group']
		u=request.form['unit']
		
		rid=request.args['rid']
		q="insert into distribution values(null,'%s','%s','%s',now())"%(rid,g,u)
		insert(q)
		return redirect(url_for('blood_bank.bloodbank_distribution'))
	
	return render_template("bloodbank_distribution.html",data=data)
