#!/bin/bash
###############################################################################
# Created By: Wesley R. Howell
# Modified by: Matthew A. Crist (2013-03-08)
# Shell Script to Process an email message
#
# invocation: ./route-email.sh "inputFile" 
# 		inputFile - is the email message in the incoming/email folder on the 
#                 server.
#
# Server listening port: 20005
#
# CHANGE LOG:
#------------------------------------------------------------------------------
# 2013-03-23	-	Changed path to module from send-email to send.
# 2013-03-08	-	Added network listening capabilities to script and changed
#                 loop to infinite loop until kill command receive as well as
#                 removed the need for multiple files.
###############################################################################

# Routes the email to the required destination
function route_email() {
	# Ensure our working directory is the home for this file
	cd "./modules/email/send"

	# Continue to listen on port 20005 until KILL_SERVICE command recieved.
	while [ $RUN_SERVICE ]
	do

		# Information dump for network connectivity
		script="write-email.lisp"
		email_file="../../../incoming/email/send/`date +%s`.xml"
	
		# Send mail port 20005
		nc -l 20005 > $email_file

		# Temporary dynamically written ACL2 script
		echo "(in-package \"ACL2\")(include-book \"route-email\" :uncertified-okp t)(rwEmail \"$email_file\" \"`date "+%Y%m%d%H%M%S"`\" state)" > $script
	
		# Dump dynamically written script to ACL2 for execution
		acl2 < $script

		# Cleanup temporary files.
		rm $script
		rm $email_file

		# Sleep 1 second to ensure file uniqueness
		sleep 1
	done # end loop
} # end function route_email

# Invoke function
route_email
