#!/bin/bash
##########################################################################
# start_server.sh
# Created by Matthew A. Crist on March 9, 2013.
#
# This file will start as the entry point to start all shell processes for
# the server to begin listening for incoming information to be processed.
#
# Registered Ports:
# ------------------------------------------------------------------------
# 20001	-	User Registration
# 20002  -  User Verification
# 20005	-	Incoming Email
#
# CHANGE LOG:
# ------------------------------------------------------------------------
# 2013-03-23	-	Added user verification module for email reception.
# 2013-03-09	-	Initial conception of file.
#
##########################################################################

module[0]="./modules/user/register/register-user.sh"
module[1]="./modules/email/send-email/route-email.sh"
module[2]="./modules/user/verify/verify-user.sh"

# Kill command for all processes.
export RUN_SERVICE=true

for((i=0;i<=${#module[@]};i++))
do
	coproc bash ${module[$i]}
done

echo "Hit 'enter' to kill service..."
read user_input
export RUN_SERVICE=false
		
# Just attempt to kill all the processes at this point
pkill ^nc
pkill ^bash
