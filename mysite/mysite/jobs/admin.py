from jobs.models import Job
from jobs.models import Location
from django.contrib import admin

class LocationAdmin(admin.ModelAdmin):
	list_display = ("city", "state", "country")

admin.site.register(Location,LocationAdmin)
