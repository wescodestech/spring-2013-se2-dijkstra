#!/bin/sh

script="write-user.lisp"
register_file="../../../incoming/user/register/register-user.xml"
store_file="../../../store/address-book/address-book.xml"
temp_file="../../../store/address-book/temp_address-book.xml"
register_xml="$(cat $register_file)"
store_xml="$(cat $store_file)"
echo "(in-package \"ACL2\")(include-book \"register-user\")(registerUser \"" $register_xml "\" \"" $store_xml "\" state)" > $script

acl2 < $script

echo "Complete!"

rm $script
#rm $register_file
rm $store_file
mv $temp_file $store_file
