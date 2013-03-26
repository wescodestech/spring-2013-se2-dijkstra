#!/bin/bash
########################################################################################
# Created by Adam Ghodratnama on March 25, 2013 
#
# This file sends the newly created User block request XML to the server
#
# CHANGE LOG:
# --------------------------------------------------------------------------------------
# 2013-3-25		-	File creation
#
#
#
#########################################################################################
#!/bin/bash

#loop through all of the request files in the requests folder
for i in `ls "./requests/"`
do
	#temporary request file
	request_block= "./requests/$i"
	
	echo "Send request file $i"
	
	#send each request file to the server using the appropriate port for block requests
	#nc -l 20001 > $request_block
		
	#remove temp request variable
	rm $request_block

done

echo "End of send-block-requests"