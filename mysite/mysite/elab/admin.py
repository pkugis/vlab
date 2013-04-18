from elab.models import Student, Teacher, CourseInfo
from django.contrib import admin

class StudentAdmin(admin.ModelAdmin):
	list_display = ("studentID","studentPassword","studentName","studentAge","studentGender")

class TeacherAdmin(admin.ModelAdmin):
	list_display = ("teacherID","teacherpassword","courseID")

class CourseInfoAdmin(admin.ModelAdmin):
	list_display = ("courseID","student","teacher","DeltaImagePath","DeltaImageSize","DeltaImageChecksum","DeltaImageLastModifyTime","DeltaImageFormat","BasicImagePath","BasicImageSize","BasicImageLocation","StorageProtocol","XMLDescrAttach")

# class TeacherAdmin(admin.ModelAdmin):
#	list_display = ("")
admin.site.register(Student,StudentAdmin)
admin.site.register(Teacher,TeacherAdmin)
admin.site.register(CourseInfo,CourseInfoAdmin)
