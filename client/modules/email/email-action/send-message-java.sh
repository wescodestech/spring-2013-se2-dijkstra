#!/bin/sh

#Created By: Wesley R. Howell
#FOR USE ONLY WITH THE JAVA APPLICATION, DO NOT USE UNDER THE REGUALR SHELL
#Shell Script to Process an email message
#invocation ./route-email.sh "inputFile" where inputFile is the email message in the incoming/email folder on the server

cd /Users/w_howell/code/spring-2013-se2-dijkstra/client/modules/email/email-action

script="write-email.lisp"
echo "(in-package \"ACL2\")(include-book \"rw-email\" :uncertified-okp t)(writeMessage \"$1\" \"$2\" \"$3\" \"$4\" state)" > $script
/Users/w_howell/Desktop/acl2-image-4.3.0/run_acl2 < $script
rm $script

sh /Users/w_howell/code/spring-2013-se2-dijkstra/client/modules/email/email-action/split-email.sh

echo "Complete!"