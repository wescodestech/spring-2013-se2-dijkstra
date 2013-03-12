#!/bin/sh

#Created By: Wesley R. Howell
#Shell Script to Process an email message
#invocation ./route-email.sh "inputFile" where inputFile is the email message in the incoming/email folder on the server



for i in `ls "../../../incoming/email/"`
do
script="write-email.lisp"
echo "(in-package \"ACL2\")(include-book \"rw-email\" :uncertified-okp t)(readEmail \"../../../incoming/email/$i\" \"`date "+%Y%m%d%H%M%S"`\" state)" > $script
acl2 < $script
rm $script
rm ../../../incoming/email/$i
sleep 1
done


echo "Complete!"