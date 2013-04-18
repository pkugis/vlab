from vlab.models import Student, Teacher, CourseInfo
from django.contrib import admin

class StudentAdmin(admin.ModelAdmin):
	list_display = ("studentID","studentPassword","studentName","studentAge","studentGender")

admin.site.register(Student,StudentAdmin)

