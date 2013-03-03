#!/bin/sh

script1="write-email1.lisp"
echo "(in-package \"ACL2\")(include-book \"route-email\" :uncertified-okp t)(rwEmail \"../../../incoming/email/send-email.xml\" \"../../../store/email/email_`date "+%Y%m%d%H%M%S"`.xml\" state)" > $script1
acl2 < $script1
rm $script1
sleep 1

script2="write-email2.lisp"
echo "(in-package \"ACL2\")(include-book \"route-email\" :uncertified-okp t)(rwEmail \"../../../incoming/email/send-email.xml\" \"../../../store/email/email_`date "+%Y%m%d%H%M%S"`.xml\" state)" > $script2
acl2 < $script2
rm $script2
sleep 1

script3="write-email3.lisp"
echo "(in-package \"ACL2\")(include-book \"route-email\" :uncertified-okp t)(rwEmail \"../../../incoming/email/send-email.xml\" \"../../../store/email/email_`date "+%Y%m%d%H%M%S"`.xml\" state)" > $script3
acl2 < $script3
rm $script3
sleep 1

script4="write-email4.lisp"
echo "(in-package \"ACL2\")(include-book \"route-email\" :uncertified-okp t)(rwEmail \"../../../incoming/email/send-email.xml\" \"../../../store/email/email_`date "+%Y%m%d%H%M%S"`.xml\" state)" > $script4
acl2 < $script4
rm $script4
sleep 1

script5="write-email5.lisp"
echo "(in-package \"ACL2\")(include-book \"route-email\" :uncertified-okp t)(rwEmail \"../../../incoming/email/send-email.xml\" \"../../../store/email/email_`date "+%Y%m%d%H%M%S"`.xml\" state)" > $script5
acl2 < $script5
rm $script5
sleep 1

script6="write-email6.lisp"
echo "(in-package \"ACL2\")(include-book \"route-email\" :uncertified-okp t)(rwEmail \"../../../incoming/email/send-email.xml\" \"../../../store/email/email_`date "+%Y%m%d%H%M%S"`.xml\" state)" > $script6
acl2 < $script6
rm $script6
sleep 1


echo "Complete!"

