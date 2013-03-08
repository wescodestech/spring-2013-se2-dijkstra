#!/bin/bash
###############################################################################
# Created by Matthew A. Crist on March 8, 2013.
# This file will provide the actions required to register a user to the address
# book.
#
# Server listening port: 20001
#
# CHANGE LOG:
#------------------------------------------------------------------------------
#
###############################################################################

while [ !$KILL_SERVICE ]
do
	# File definitions
	script="write-user.lisp"
	register_file="../../../incoming/user/register/`date +%s`.xml"
	store_file="../../../store/address-book/address-book.xml"
	temp_file="../../../store/address-book/temp_address-book.xml"

	# Registration port 20001
	nc -l 20001 > $register_file

	# Load the contents of the store files for store in temporary ACL2 script
	register_xml="$(cat $register_file)"
	store_xml="$(cat $store_file)"
	echo "(in-package \"ACL2\")(include-book \"register-user\")(registerUser \"" $register_xml "\" \"" $store_xml "\" state)" > $script

	# Execute temporary ACL2 script.
	acl2 < $script

	# Cleanup temporary files and make new file permanent
	rm $script
	rm $register_file
	rm $store_file
	mv $temp_file $store_file

	# Ensure everything is done by a 1 second delay
	sleep 1
done	# end loop
