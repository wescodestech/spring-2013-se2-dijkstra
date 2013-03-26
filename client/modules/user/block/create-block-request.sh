#!/bin/bash
########################################################################################
# Created by Adam Ghodratnama on March 25, 2013
#
# This file performs the following actions:
# -Takes arguments for creating a "block user" request (blockerDomain, blockerAddress,
#  blockeeDomain, blockeeAddress
# -Invokes the ACL2 "create-block-request.lisp" file to create a block XML request
# -Sends the created XML file to the server for processing/action
#
# Invocation example:
# -create-block-request.sh "blockerDomain" "blockerAddress" "blockeeDomain" "blockeeAddress"
#
# CHANGE LOG:
# --------------------------------------------------------------------------------------
# 2013-3-25		-	File creation
#
#########################################################################################

# ensure that we're in the correct directory to call the script
#echo $PWD

script="create-request.lisp"
echo "(in-package \"ACL2\")(include-book \"create-block-request\" :uncertified-okp t)(createRequest '(\"$1\" \"$2\") '(\"$3\" \"$4\") `date "+%Y%m%d%H%M%S"` state)" > $script
acl2 < $script

rm $script

echo "Complete!"
