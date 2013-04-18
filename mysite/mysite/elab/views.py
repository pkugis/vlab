# Create your views here.
import libvirt
import sys
from django.contrib import auth
from django.template import Context, loader
from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import render_to_response, get_object_or_404
from django.template import RequestContext
from django.core.urlresolvers import reverse
from elab.models import Student, Teacher, CourseInfo
from django.utils import simplejson	

def index(request):
	return render_to_response('elab/index.html')#,{'domain_id_list':dom_container})

def create(request):
	return render_to_response('elab/create.html')

def courses(request):
	courseinfo_list = CourseInfo.objects.all()	
	return render_to_response('elab/courses.html',{'mycourses':courseinfo_list},context_instance=Context(request))

def listcourses(request):
	loc = None
	data_arr = []
	loc = CourseInfo.objects.all()
	if loc:
		for obj in loc:
			data = {'StudentID': obj.student.studentID, 'TeacherID': obj.teacher.teacherID, 'CourseID': obj.courseID}
           		data_arr.append(data)
			result = simplejson.dumps(data_arr)
	else:
		pass
	return HttpResponse(result, content_type='application/json')
		

def clusters(request,hostip):
	host_list=['172.17.1.9']
	data=[]
	for hostIP in host_list:
		conn=libvirt.openReadOnly('xen+ssh://root@'+hostIP+'/')
		domainsInfo_list=[]
		for domainID in conn.listDomainsID():
			dom=conn.lookupByID(domainID)
			each_domain_info={}
			each_domain_info['domainID']=domainID
			#each_domain_info['maxMemory']=dom.maxMemory()/1024
			#each_domain_info['maxVcpus']=dom.maxVcpus()
			each_domain_info['name']=dom.name()
			each_domain_info['OSType']=dom.OSType()
			each_domain_info['state']=dom.info()[0]
			#each_domain_info['UUID']=dom.UUIDString()
			domainsInfo_list.append(each_domain_info)
		data.append(hostIP)
		data.append(domainsInfo_list)
	return render_to_response('elab/clusters.html',{'data':data},context_instance=Context(request))

def teachers(request):
	teacher_list = Teacher.objects.all()
	return render_to_response('elab/teachers.html',{'test':teacher_list},context_instance=Context(request))

def students(request):
	student_list = Student.objects.all()	
	return render_to_response('elab/students.html',{'hehe':student_list},context_instance=Context(request))

def login(request):
	return render_to_response('elab/login.html')

def authen(request):
	return render_to_response('elab/authen.html')

def vnc(request):
	return render_to_response('elab/vnc.html')
		
