# Create your views here.
import sys
import re
import libvirt
from django.contrib import auth
from django.template import Context, loader
from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import render_to_response, get_object_or_404
from django.template import RequestContext
from django.core.urlresolvers import reverse
from django.utils import simplejson
from bootstrap.models import Student, Teacher, Course, Selective, Flavor, History, Message, Host 
from time import ctime

def index(request):
	return render_to_response('bootstrap/index.html')

# probably query4teacher would be a teacher's name
# administrator may not remember certain teacher's ID or email
#def listteachers(request,query4teacher):
def listteachers(request):
	data_dict = []
	teachers = Teacher.objects.all()
	if teachers:
		for item in teachers:
			data = {'teacherID':item.teacherID,'teacherName':item.teacherName,'teacherEmail':item.teacherEmail}
			data_dict.append(data)
			result = simplejson.dumps(data_dict)
	else:
		pass
	return HttpResponse(result,content_type='application/json')

def listteacherbyID(request, ID4teacher):
	data_dict = []
	teachers = Teacher.objects.all(filter=teacher_ID)
	return render_to_response('bootstrap/teacherdetail.html')
		
def listcourses(request):
	data_dict = []
	courses = Course.objects.all()
	result = simplejson.dumps(data_dict)
	if courses:
		for item in courses:
			#data = {'courseID':item.courseID,'courseName':item.courseName,'teacher':item.teacher.teacherName,'baseimagedesc':item.baseimagedesc,'vcpu':item.flavor.vcpus,'mem':item.flavor.maxmemory,'flavororder':item.flavororder,'opendates':item.opendates}
			data = {'courseID':item.courseID,'courseName':item.courseName,'teacher':item.teacher.teacherName,'opendates':item.opendates}
			data_dict.append(data)
			result = simplejson.dumps(data_dict)
	else:
		pass
	return HttpResponse(result,content_type="application/json")

def liststudents(request):	
	data_dict = []
	students = Student.objects.all()
	if students:
		for item in students:
			data = {'studentID':item.studentID,'studentName':item.studentName,'studentEmail':item.studentEmail}
			data_dict.append(data)
			result = simplejson.dumps(data_dict)
	else:
		pass
	return HttpResponse(result,content_type='application/json')


#def liststudentbyID(request, student_ID):
#	data_dict = []
#	students = Student.objects.all(filter=student_ID)
#	return render_to_response('bootstrap/studentdetail.html')

def listhosts(requet):
	data_dict = []
	hosts = Host.objects.all()
	if hosts:
		for item in hosts:
			data = {'hostID':item.hostID,'hostIP':item.hostIP,'hostMemory':item.hostMemory}
			data_dict.append(data)
			result = simplejson.dumps(data_dict)
	else:
		pass
	return HttpResponse(result,content_type='application/json')
	
#each_domain_info['maxMemory']=dom.maxMemory()/1024
#each_domain_info['maxVcpus']=dom.maxVcpus()
#each_domain_info['UUID']=dom.UUIDString()
#return HttpResponse(result,content_type="application/json")
def listdomainsbyIp(request,hostIP):
	data=[]
	#conn=libvirt.openReadOnly('xen+ssh://root@'+hostIP+'/')
	conn=libvirt.openReadOnly('qemu+ssh//'+hostIP+'/')
	domainInfo_list=[]
	if conn.listDomainsID():
		for domainID in conn.listDomainsID():
			dom=conn.lookupByID(domainID)
			each_domain_info={}
			each_domain_info['domainID']=domainID
			each_domain_info['name']=dom.name()
			each_domain_info['OSType']=dom.OSType()
			each_domain_info['state']=dom.info()[0]
			domainInfo_list.append(each_domain_info)
			result = simplejson.dumps(domainInfo_list)
	else:
		pass
	return render_to_response("bootstrap/domainsinfo.html",{"IP":hostIP,"domainsinfo_host":domainInfo_list},context_instance=Context(request))

def studentlogin(request):
	return render_to_response("bootstrap/studentlogin.html")

def studentauthen(request):
	try:
		IsStudentIn = Student.objects.get(studentID=request.POST['studentID'])
	except Student.DoesNotExist:
		return render_to_response("bootstrap/studentlogin.html",{"message":"can't find such studentID in the system!"},context_instance=Context(request))
	if IsStudentIn.studentPassword == request.POST['studentPwd']:
		try:
			SelectiveInfo = Selective.objects.get(student=request.POST['studentID'])
		except SelectiveInfo.DoesNotExist:
			request.session['userid']=IsStudentIn.studentID
			return render_to_response("bootstrap/student.html",{"loginIDtag":IsStudentIn,"selectiveInfo":SelectiveInfo},context_instance=Context(request))
		return render_to_response("bootstrap/student.html",{"loginIDtag":IsStudentIn,"selectiveInfo":SelectiveInfo},context_instance=Context(request)) 
	else:
		return render_to_response("bootstrap/studentlogin.html",{"message":"studentID and password can't match!"},context_instance=Context(request))	

def studentlogout(request):
	try:
		del request.session['studentID']
	except KeyError:
		pass
	return render_to_response("bootstrap/studentlogin.html")

#def listSelectiveByID(request,stuid):
#	try:
#		selectives = Selective.objects.get(student=stuid)
#	except Selective.DoesNotExist:
#		return Http  
#	return HttpResponse(result,content_type='application/json') 

def listInstances(request,courseid):
	return render_to_response("bootstrap/studentlogin.html")	

'''def studenttestlogin(request):
	#if request.method == 'POST':
	#	if request.session.test_cookie_worked():
	#		request.session.delete_test_cookie()
	#		return HttpResponse("You have logged in.")
	#	else:
	#		return 
	try:
		IsStudentIn = Student.objects.get(studentID=request.POST['studentID'])
	except Student.DoesNotExist:
		return HttpResponse("no this user.")
	if IsStudentIn.studentPassword == request.POST['studentPwd']:
		request.session['userid']=IsStudentIn.studentID
		return HttpResponse("<html>You're logged in.<a href='../studenttestlogout/'>"+IsStudentIn.studentID+"</a></html>")
	else:
		return HttpResponse("your name and password doesn't match.")

def studenttestlogout(request):
	try:
		del request.session['studentID']
	except KeyError:
		pass
	return HttpResponse("You are logged out.")'''


def adminlogin(request):
	return render_to_response("bootstrap/login.html")		
