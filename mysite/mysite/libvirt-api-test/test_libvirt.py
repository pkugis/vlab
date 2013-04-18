import libvirt
import sys

conn = libvirt.openReadOnly("xen+ssh://root@172.17.1.19/")
if conn == None:
    print 'Failed to open connection to the hypervisor'
    sys.exit(1)

try:
    dom0 = conn.lookupByName("test_win7")
except:
    print 'Failed to find the main domain'
    sys.exit(1)

print "%s: id %d running %s" % (dom0.name(), dom0.ID(), dom0.OSType())
print dom0.info()
