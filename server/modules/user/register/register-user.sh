#!/bin/sh

script="temp_register-user.lisp"
xml_file="../../../incoming/user/register/register-user.xml"
xml="$(cat $xml_file)"
lisp="$(cat 'register-user.lisp')"
echo $lisp "(registerUser \"" $xml "\")" >> $script

acl2 < $script

rm $script
#rm $xml_file
