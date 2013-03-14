#!/bin/sh

#Created By: Wesley R. Howell
#FOR USE ONLY WTIH THE JAVA APPLICATION, DO NOT USE WITH THE REGULAR SHELL
#Shell Script to Process an email message
#invocation ./route-email.sh "inputFile" where inputFile is the email message in the incoming/email folder on the server

cd /Users/w_howell/code/spring-2013-se2-dijkstra/client/modules/email/email-action

for i in `ls "../../../incoming/email/"`
do
script="write-email.lisp"
echo "(in-package \"ACL2\")(include-book \"rw-email\" :uncertified-okp t)(readEmail \"../../../incoming/email/$i\" \"`date "+%Y%m%d%H%M%S"`\" state)" > $script
/Users/w_howell/Desktop/acl2-image-4.3.0/run_acl2 < $script
rm $script
rm ../../../incoming/email/$i
sleep 1
done


echo "Complete!"