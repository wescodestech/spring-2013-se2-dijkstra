#!/bin/bash
###############################################################################
# Created by Matthew A. Crist on March 8, 2013.
# This file will provide the actions required to register a user to the address
# book.  Folders will be created for a store.
#
# Server listening port: 20001
#
# CHANGE LOG:
#------------------------------------------------------------------------------
# 2013-03-17	-	Added directory creation on user registration.
# 2013-03-09	-	Removed grep validation for request structure added if check
# 2013-03-09	-	Added grep validation for request structure
# 2013-03-09	-	Encapsulated all values in function register_user
# 2013-03-08	-	Added support for network connectivity.
# 2013-03-08	-	Initial conception of this file.
###############################################################################

function register_user() {
	# Set the active working directory for this process
	cd "./modules/user/register"

	while [ $RUN_SERVICE ]
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
		store_xml="$(cat $store_file)"	# We are certain our store is correct.

		# Verify that our grep returned a valid result.
		if [ ${#register_xml} -gt 0 ]
		then
			# Generate out dynamic script and send it to acl2
			echo "(in-package \"ACL2\")(include-book \"register-user\")(registerUser \"" $register_xml "\" \"" $store_xml "\" state)" > $script
			acl2 < $script

			# Parse the domain to use
			domain=$(grep "<domain>.*</domain>" $register_file)
			domain=$(echo ${domain#*>}) #Trim the opening tag
			domain=$(echo ${domain%<*}) #Trim the ending tag

			# Parse the name to use
			name=$(grep "<name>.*</name>" $register_file)
			name=$(echo ${name#*>}) #Trim the opening tag
			name=$(echo ${name%<*}) #Trim the ending tag

			# Attempt to create the domain if it does not exist
			if [ -f "../../../store/email/${domain}" ]
			then
				# Domain exists already
				echo "../../../store/email/${domain}"
			else
				mkdir "../../../store/email/${domain}"
			fi	# end if

			# Attempt to create the home directory if it does not exist
			if [ -f "../../../store/email/${domain}/${name}" ]
			then
				# Home folder already exists
				echo "../../../store/email/${domain}/${name}"
			else
				mkdir "../../../store/email/${domain}/${name}"
			fi	#end if
			
			# Cleanup temporary files and make new file permanent
			rm $script
		
			# Verify the new permanent file was generated
			if [ -f "$temp_file" ]
			then
				# Replace our permanent store with the newly created one.
				rm $store_file
				mv $temp_file $store_file
			fi	# end if
		fi	#end if

		# Remove the registration attempt
		rm $register_file

		# Ensure everything is done by a 1 second delay
		sleep 1
	done	# end loop
} # end function register_user

# Kick off this service
register_user
