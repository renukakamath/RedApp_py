from flask import *
from database import *
admin=Blueprint('admin',__name__)

@admin.route('/admin_home',methods=['get','post'])
def admin_home():
	if not session.get("lid") is None:
		data={}
		return render_template("admin_home.html")
	else:
		return redirect(url_for('public.login'))


@admin.route('/admin_Manage_blood_groups',methods=['get','post'])
def admin_Manage_blood_groups():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM `bloodgroups` ORDER BY `group`"
		res1=select(q)
		data['res1']=res1
		if 'd' in request.args:
				d=request.args['d']
				print(d)
				q="DELETE FROM `bloodgroups` WHERE `group_id`='%s'"%(d)
				delete(q)
				return redirect(url_for('admin.admin_Manage_blood_groups'))
		if 'submit' in request.form:
			name=request.form['name']
			
			q="SELECT * FROM `bloodgroups` WHERE `group`='%s'"%(name)
			res=select(q)

			if res:
				flash('BLOOD GROUP ALREADY REGISTERED')
				return redirect(url_for('admin.admin_Manage_blood_groups'))
			else:
				q="INSERT INTO `bloodgroups`(`group`) VALUES ('%s')"%(name)
				insert(q)
				flash('BLOOD GROUP REGISTERED')
				return redirect(url_for('admin.admin_Manage_blood_groups'))
		return render_template("admin_Manage_blood_groups.html",data=data)
	else:
		return redirect(url_for('public.login'))


@admin.route('/admin_View_blood_banks',methods=['get','post'])
def admin_View_blood_banks():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM `bloodbank`"
		res=select(q)
		data['res']=res
		if not res:
				flash('NO DATA AVAILABLE')
		if'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None

		if action=="available":
			q="SELECT * FROM `availablebloods` INNER JOIN `bloodgroups` USING (`group_id`)  WHERE `allocated_id`='%s' AND `type`='blood_bank' GROUP BY `group_id`"%(id)
			data['available']=select(q)
			if not data['available']:
				flash('NO DATA AVAILABLE')
		return render_template("admin_View_blood_banks.html",data=data)
	else:
		return redirect(url_for('public.login'))




@admin.route('/admin_View_Hospitals',methods=['get','post'])
def admin_View_Hospitals():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM `users`  inner join bloodgroups USING(group_id) where utype='receiver'"
		res=select(q)
		data['res']=res
		# if not res:
		# 		flash('NO DATA AVAILABLE')
		# if'action' in request.args:
		# 	action=request.args['action']
		# 	id=request.args['id']
		# else:
		# 	action=None

		# if action=="available":
		# 	q="SELECT *,COUNT(`ablood_id`) AS COUNT FROM `availablebloods` INNER JOIN `bloodgroups` USING (`group_id`)  WHERE `allocated_id`='%s' AND `type`='hospital' GROUP BY `group_id`"%(id)
		# 	data['available']=select(q)
		# 	if not data['available']:
		# 		flash('NO DATA AVAILABLE')
		return render_template("admin_View_Hospitals.html",data=data)
	# else:
	# 	return redirect(url_for('public.login'))


@admin.route('/admin_View_Organization',methods=['get','post'])
def admin_View_Organization():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM `organisation`"
		data['org']=select(q)
		if not data['org']:
				flash('NO DATA AVAILABLE')
		if'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None

		if action=="camp":
			q="(SELECT *,`hospitals`.`hospital`AS ALOCATE FROM `camp` INNER JOIN `hospitals` ON (`camp`.`allocates_id`=`hospitals`.`hospital_id`) WHERE `allocates_type`='hospital' AND `organization_id`='%s') UNION (SELECT *,`bloodbank`.`bankname` AS ALOCATE  FROM `camp` INNER JOIN `bloodbank` ON (`camp`.`allocates_id`=`bloodbank`.`bank_id`) WHERE `allocates_type`='blood_bank' AND `organization_id`='%s')"%(id,id)
			data['camp']=select(q)
			if not data['camp']:
				flash('NO DATA AVAILABLE')
		return render_template("admin_View_Organization.html",data=data)
	else:
		return redirect(url_for('public.login'))
# @admin.route('/admin_home',methods=['get','post'])
# def admin_home():
# 	if not session.get("lid") is None:

# 		return render_template("admin_home.html")
# 	else:
# 		return redirect(url_for('public.login'))



@admin.route('/admin_viewreceiver',methods=['get','post'])
def admin_viewreceiver():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM `users` inner join bloodgroups USING (group_id) where utype='donor'"
		res=select(q)
		data['res']=res
		
		return render_template("admin_viewreceiver.html",data=data)


