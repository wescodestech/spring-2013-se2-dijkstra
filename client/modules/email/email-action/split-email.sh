#!/bin/sh

#Created By: Wesley R. Howell
#Shell Script to Split the xml for multiple recipients. 
#invocation ./split-emails.sh where inputFile is the email message in the incoming/email folder on the server
###########
#Change Log:
#3/25/2013 - Added the send messages to server using nc, Right now its one of those things that works "sometimes" has a bug to still work out.
###########

for i in `ls "../../../store/email/outbox/"`
do
awk '/<?xml/{n++}{filename = "../../../store/email/outbox/msgx_" n ".xml"; print >filename }' ../../../store/email/outbox/$i
	
	for y in `find ../../../store/email/outbox/ -name "msgx*"`
	do
		mv ../../../store/email/outbox/$y ../../../store/email/outbox/`date "+%Y%m%d%H%M%S"`.xml
		sleep 1
	done
rm ../../../store/email/outbox/$i
sleep 1
done

#send message to server
for j in `ls "../../../store/email/outbox/"`
do
cat ../../../store/email/outbox/$j | nc localhost 20005
sleep 1
done

echo "Complete!"
