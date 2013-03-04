#!/bin/sh

#Shell Script to Process an email message
#invocation ./route-email.sh "inputFile" where inputFile is the email message in the incoming/email folder on the server

script="write-email.lisp"
echo "(in-package \"ACL2\")(include-book \"route-email\" :uncertified-okp t)(rwEmail \"$1\" \"`date "+%Y%m%d%H%M%S"`\" state)" > $script
acl2 < $script
rm $script
rm $1
sleep 1


echo "Complete!"

