#!/bin/bash
##########################################################################
# verify-user.sh
# Created on March 23, 2013 by Matthew A. Crist.
# This file will invoke the script required to verify that a user should 
# have access to an inbox and open a connection to send those files to the
# client.
#
# THIS MODULE RELIES HEAVILY ON THE CREATED DIRECTORIES FOR MAIL FOLDERS 
# ON USER REGISTRATION.  IF THIS FOLDER DOES NOT EXIST, THE VERIFICATION
# PROCESS IS POINTLESS.  A RESPONSE WILL BE USED ON THE SERVER SIDE
# ACCEPT(user.verify) or REJECT(user.verify) TO RESPOND BACK TO THE CLIENT
# IF IT IS ACCEPTABLE TO SEND/RECIEVE THE EMAIL IN THEIR INBOX.  WHEN THAT
# EMAIL IS SENT, IT IS REMOVED FROM THE SERVER ENTIRELY.
#
# CHANGE LOG:
# ------------------------------------------------------------------------
# 2013-03-23	-	Initial conception of this file.
#
##########################################################################

RUN_SERVICE=true

function verifyUser() {
	# Set the active working directory for this module to this directory
	cd "./modules/user/verify"

	while [ $RUN_SERVICE ]
	do
		response_file="write-response.lisp"
		verify_file="../../../incoming/user/verify/`date +%s`.xml"
		addressbook_file="../../../store/address-book/address-book.xml"

		echo "Listening for user verification on port 20002..."

		# Verification port
		nc -l 20002 > $verify_file

		verify_xml="$(cat $verify_file)"
		addressbook_xml="$(cat $addressbook_file)"

		if [ ${#verify_xml} -gt 0 ]
		then
			echo "(in-package \"ACL2\")(include-book \"verify-user.lisp\")(testUser \"$verify_xml\" \"$addressbook_xml\")" > $response_file
			acl2 < $response_file
		fi
	done
}

verifyUser
