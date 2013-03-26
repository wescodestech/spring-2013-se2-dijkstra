#!/bin/bash
########################################################################################
# Created by Adam Ghodratnama on March 15, 2013
#
# This file performs the following actions:
# -Takes arguments for creating a user registration request (Domain, Name, Password)
# -Calls on the ACL2 file "create-user-request.lisp" to create an XML user request
# -Sends the created XML file to the server for processing/registration by invoking
#   send-user-request.sh
#
# Invocation example:
# -create-user-request.sh "domain" "name" "password" 	
#
# CHANGE LOG:
# --------------------------------------------------------------------------------------
# 2013-3-15		-	File creation
#
# 2013-3-17		-   Adjusted parameters of the script to time stamp the request that is
#					created
#
# 2013-3-24		-	Added invocation of send-user-request.sh to send requsts that are
#					created to the server
#
#########################################################################################

# ensure that we're in the correct directory to call the script
#echo $PWD

script="create-request.lisp"
echo "(in-package \"ACL2\")(include-book \"create-user-request\" :uncertified-okp t)(createRequest '(\"$1\" \"$2\" \"$3\") `date "+%Y%m%d%H%M%S"` state)" > $script
acl2 < $script

rm $script

echo "Complete!"

# Call the script that sends the requests to the server
./send-user-requests.sh