from bootstrap.models import Student, Teacher, Flavor, Course, Selective, History, Message 
from django.contrib import admin

class StudentAdmin(admin.ModelAdmin):
	list_display = ("studentID","studentPassword", "studentName","studentAge","studentGender","studentEmail","studentMobile")

class TeacherAdmin(admin.ModelAdmin):
	list_display = ("teacherID","teacherName", "teacherPassword", "teacherEmail")
	
class FlavorAdmin(admin.ModelAdmin):
	list_display = ("flavorID", "vcpus", "maxmemory")

class CourseAdmin(admin.ModelAdmin):
	list_display = ("courseID","courseName","teacher","baseimagedesc","flavor","flavororder","flavororder","opendates")
	
class SelectiveAdmin(admin.ModelAdmin):
	list_display = ("selectiveID","course","student","childimagedesc")

class HistoryAdmin(admin.ModelAdmin):
	list_display = ("domName","domtype","domcpu","dommem")	
	
class MessageAdmin(admin.ModelAdmin):
	list_display = ("messageID","sender","receiver","verify","sendtime","responsetime")	

admin.site.register(Student,StudentAdmin)
admin.site.register(Teacher,TeacherAdmin)
admin.site.register(Flavor,FlavorAdmin)
admin.site.register(Course,CourseAdmin)
admin.site.register(Selective,SelectiveAdmin)
admin.site.register(History,HistoryAdmin)
admin.site.register(Message,MessageAdmin)
