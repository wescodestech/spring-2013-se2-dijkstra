#!/bin/bash
########################################################################################
# Created by Adam Ghodratnama on March 23, 2013 
#
# This file sends the newly created User request XML to the server
#
# CHANGE LOG:
# --------------------------------------------------------------------------------------
# 2013-3-23		-	File creation
#
#
#
#########################################################################################
#!/bin/bash

#loop through all of the request files in the requests folder
for i in `ls "./requests/"`
do
	#temporary request file
	request_reg= "./requests/$i"
	
	echo "Send request file $i"
	
	#send each request file to the server using port 20001
	nc -l 20001 > $request_reg
		
	#remove temp request variable
	rm $request_reg

done

echo "End of send-user-requests"