from django.conf.urls import patterns, include, url

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'mysite.views.home', name='home'),
    # url(r'^mysite/', include('mysite.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),
    url(r'^elab/css/(?P<path>.*)$','django.views.static.serve',{'document_root':"/home/tom/mytemplates/elab/css"}),
    url(r'^elab/js/(?P<path>.*)$','django.views.static.serve',{'document_root':"/home/tom/mytemplates/elab/js"}),
    url(r'^elab/images/(?P<path>.*)$','django.views.static.serve',{'document_root':"/home/tom/mytemplates/elab/images"}),
    url(r'^elab/images/input/(?P<path>.*)$','django.views.static.serve',{'document_root':"/home/tom/mytemplates/elab/images/input"}),
    url(r'^elab/vncdir/(?P<path>.*)$','django.views.static.serve',{'document_root':"/home/tom/mytemplates/elab/vncdir"}),
    url(r'^elab/$','elab.views.index'),
    url(r'^elab/create/$','elab.views.create'),
    url(r'^elab/login/$','elab.views.login'),
    url(r'^elab/authen/$','elab.views.authen'),
    url(r'^elab/vnc/$','elab.views.vnc'),
    url(r'^elab/students/$','elab.views.students'),
    url(r'^elab/teachers/$','elab.views.teachers'),
    url(r'^elab/courses/$','elab.views.courses'),
    url(r'^elab/clusters/$','elab.views.clusters'),
    url(r'^polls/$','polls.views.index'),
    url(r'^polls/(?P<poll_id>\d+)/$','polls.views.detail'),
    url(r'^polls/(?P<poll_id>\d+)/results/$','polls.views.results'),
    url(r'^polls/(?P<poll_id>\d+)/vote/$','polls.views.vote'),
    # Uncomment the next line to enable the admin:
    url(r'^admin/', include(admin.site.urls)),
)
