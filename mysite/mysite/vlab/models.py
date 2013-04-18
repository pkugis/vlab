from django.db import models

# Create your models here.
class Student(models.Model):
	studentID = models.CharField(max_length=50)
	studentPassword = models.CharField(max_length=50)
	studentName = models.CharField(max_length=50)
	studentAge = models.IntegerField()
	studentGender = models.CharField(max_length=2)
	
	def __str__(self):
		return "%s, %s, %s, %d, %s" % (self.studentID, self.studentPassword, self.studentName, self.studentAge, self.studentGender)

class Teacher(models.Model):
	teacherID = models.CharField(max_length=50)
	teacherpassword = models.CharField(max_length=50)
	courseID = models.CharField(max_length=50)

class CourseInfo(models.Model):
	courseID = models.CharField(max_length=50)
	student = models.ForeignKey(Student)
	teacher = models.ForeignKey(Teacher)
	DeltaImagePath = models.CharField(max_length=200)
	DeltaImageSize = models.IntegerField()
	DeltaImageChecksum = models.CharField(max_length=100)
	DeltaImageLastModifyTime = models.DateTimeField("last modified")
	DeltaImageFormat = models.CharField(max_length=10)
	BasicImagePath = models.CharField(max_length=200)
	BasicImageSize = models.IntegerField()
	BasicImageLocation = models.CharField(max_length=30)
	StorageProtocol = models.CharField(max_length=20)
	XMLDescrAttach = models.CharField(max_length=100)
	# CourseDes = models.content
	# SoftwareList = models.
