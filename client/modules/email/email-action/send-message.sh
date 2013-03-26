#!/bin/sh

#Created By: Wesley R. Howell
#Shell Script to Process an email message
#invocation ./route-email.sh "inputFile" where inputFile is the email message in the incoming/email folder on the server


script="write-email.lisp"
echo "(in-package \"ACL2\")(include-book \"rw-email\" :uncertified-okp t)(writeMessage \"$1\" \"$2\" \"$3\" \"$4\" state)" > $script
acl2 < $script
rm $script

./split-email.sh



echo "Complete!"