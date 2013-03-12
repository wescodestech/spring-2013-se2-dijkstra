#!/bin/sh

#Created By: Wesley R. Howell
#Shell Script to Split the xml for multiple recipients. 
#invocation ./split-emails.sh where inputFile is the email message in the incoming/email folder on the server


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

echo "Complete!"
