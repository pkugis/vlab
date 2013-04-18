from django.db import models

# Creat basic models here.
class Student(models.Model):
	studentID = models.CharField(max_length=50, primary_key=True)
	studentPassword = models.CharField(max_length=50)
	studentName = models.CharField(max_length=50)
	studentAge = models.IntegerField()
	studentGender = models.CharField(max_length=2)
	studentEmail = models.CharField(max_length=50)
	studentMobile = models.CharField(max_length=15)

	def __str__(self):
		return "%s, %s, %s, %d, %s, %s, %s" % (self.studentID, self.studentPassword, self.studentName, self.studentAge, self.studentGender, self.studentEmail, self.studentMobile)
	
	def __unicode__(self):
		return "%s, %s, %s, %d, %s, %s, %s" % (self.studentID, self.studentPassword, self.studentName, self.studentAge, self.studentGender, self.studentEmail, self.studentMobile)

class Teacher(models.Model):
	teacherID = models.CharField(max_length=50, primary_key=True)
	teacherName = models.CharField(max_length=100)
	teacherPassword = models.CharField(max_length=50)
	teacherEmail = models.CharField(max_length=50)		

	def __str__(self):
		return "%s, %s, %s, %s" % (self.teacherID, self.teacherName, self.teacherPassword, self.teacherEmail)

	def __unicode__(self):
		return "%s, %s, %s, %s" % (self.teacherID, self.teacherName, self.teacherPassword, self.teacherEmail)
class Flavor(models.Model):
	flavorID = models.AutoField(primary_key=True)
	vcpus = models.IntegerField()
	maxmemory = models.IntegerField()

	def __str__(self):
		return "%d, %d, %d" % (self.flavorID, self.vcpus, self.maxmemory)

class Course(models.Model):
	courseID = models.CharField(max_length=50)
	courseName = models.CharField(max_length=200)
	teacher = models.ForeignKey(Teacher, related_name='teacher_course')
	baseimagedesc = models.CharField(max_length=200)
	flavor = models.ForeignKey(Flavor,related_name='flavor_course')
	flavororder = models.IntegerField()
	opendates = models.CharField(max_length=100)
	
	def __str__(self):
		return "%s, %s, %s, %s, %s, %d, %s" % (self.courseID, self.courseName, self.teacher, self.baseimagedesc, self.flavor, self.flavororder, self.opendates)

	def __unicode__(self):
		return "%s, %s, %s, %s, %s, %d, %s" % (self.courseID, self.courseName, self.teacher, self.baseimagedesc, self.flavor, self.flavororder, self.opendates)

class Selective(models.Model):
	selectiveID = models.AutoField(primary_key=True)
	course = models.ForeignKey(Course,related_name='course_selective')
	student = models.ForeignKey(Student,related_name='student_selective')
	childimagedesc = models.CharField(max_length=200)
	
	def __str__(self):
		return "%s, %s, %s, %s" % (self.selectiveID, self.course, self.student, self.childimagedesc)
	def __unicode__(self):
		return "%s, %s, %s, %s" % (self.selectiveID, self.course, self.student, self.childimagedesc)

class Host(models.Model):
	hostID = models.IntegerField()
	hostIP = models.CharField(max_length=20)
	hostMemory = models.IntegerField()

	def __str__(self):
		return "%d, %s, %d" % (self.hostID, self.hostIP, self.hostMemory)
	
# Create models for further development
class History(models.Model):
	domName = models.CharField(max_length=50)	
	domtype = models.CharField(max_length=50)	#Linux, Win7, XP
	domcpu = models.IntegerField()
	dommem = models.IntegerField()

	def __str__(self):
		return "%s, %s, %d, %d" % (self.domName, self.domtype, self.domcpu, self.dommem) 
	
class Message(models.Model):
	messageID = models.AutoField(primary_key=True)
	sender = models.CharField(max_length=50)
	receiver = models.CharField(max_length=50)
	verify = models.BooleanField()
	sendtime = models.DateField()
	responsetime = models.DateField()

	def __str__(self):
		return "%s, %s, %d, %s, %s" % (self.sender, self.receiver, self.verify, self.sendtime, self.responsetime)	
