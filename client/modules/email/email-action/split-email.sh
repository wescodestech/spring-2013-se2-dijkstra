#!/bin/sh

#Created By: Wesley R. Howell
#Shell Script to Split the xml for multiple recipients. 
#invocation ./split-emails.sh where inputFile is the email message in the incoming/email folder on the server


for i in `ls "../../../store/email/outbox/"`
do
awk '/<?xml/{n++}{filename = "../../../store/email/outbox/msg_" n ".xml"; print >filename }' ../../../store/email/outbox/$i

rm ../../../store/email/outbox/$i
sleep 1
done

echo "Complete!"
