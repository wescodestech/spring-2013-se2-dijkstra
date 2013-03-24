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

# Declared modules that will be initialized at startup
module[0]="./modules/user/register/register-user.sh"
module[1]="./modules/user/verify/verify-user.sh"
module[2]="./modules/email/send/route-email.sh"
moudle[3]="./modules/email/receive/receive-email.sh"

# Kill command for all processes.
export RUN_SERVICE=true

for((i=0;i<=${#module[@]};i++))
do
	process=`echo myproc$i`
	coproc ${process} { bash ${module[$i]}; }
done

echo "Hit 'enter' to kill service..."
read user_input
export RUN_SERVICE=false
		
# Just attempt to kill all the processes at this point
for((i=0;i<=${#module[@]};i++))
do
	process=`echo myproc$i\_PID`
	kill $process
	echo "Module killed: ${module[$i]}"
done
