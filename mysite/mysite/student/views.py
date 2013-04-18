# Create your views here.
import libvirt
import sys
from django.contrib import auth
from django.template import Context, loader
from django.http import HttpResponse, httpResponseRedirect
from django.shortcuts import render_to_response, get_object_or_404
from django.template import RequestContext
from django.core.urlresolvers import reverse
