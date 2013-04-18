# Create your views here.
import sys
from django.contrib import auth
from django.template import Context, loader
from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import render_to_response, get_object_or_404
from django.template import RequestContext
from django.core.urlresolvers import reverse
from django.utils import simplejson
	
def index(request):
	return render_to_response('bootstrap/index.html')#,{'domain_id_list':dom_container})

