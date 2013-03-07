;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; remote-actions.lisp
; Written by Matthew A. Crist on March 4, 2013.
;
; Purpose:
; This file contains the actions that will be used to process the actions 
; that can be invoked upon the client and server remotely by the client 
; or server for the need to send extra information for action invocation.
;
; Format for actions are as such:
; {SERVERACTION=COPYFILE(user.register); 
; SERVERACTION=MOVEFILE(user.store);}
;
; Brackets encapsulate the actions, and semicolins separate the actions.
; SERVERACTION denotes a server action to take place.  CLIENTACTION 
; denotes that a client action should take place.  At the time of this 
; file conception, MOVEFILE and COPYFILE actions are only considered.  The
; portion containing the string between the brackets is the module that 
; will be considered.  This will acquire the "monitor" value from the 
; module registration on the server side.  In otherwords, the file that
; contains the "header" information will be MOVED/COPIED (depending on 
; action) to the monitor for the module that is in the brackets, thus
; invoking the monitor process and kick starting the module itself.  
; Files that contain monitor information will end with a file extension
; of $unix_timestamp.rmi.  When this file is copied, the header 
; information is removed and the output is the following XML that is 
; contained in the file ($unix_timestamp.xml).
;
; CHANGE LOG:
; ------------------------------------------------------------------------
; 2013-03-04	Initial conception of the remote-actions file.
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(in-package "ACL2")

; (getActionString xs)
; Acquires the action string that is present in the text that is loaded 
; from the RMI file.
;
; xs - the list of characters that contains the action string.
(defun getActionString (xs)
	(if (endp xs)
		xs
		(if (equal (car xs) #\})
			nil
			(cons (car xs) (getActionString (cdr xs))))))

; (getAction xs)
; Acquires each action, which is separated by the semicolin in the action
; string.
;
; xs - The list of characters that contain the actions.
(defun getAction (xs)
	(if (endp xs)
		xs
		(if (equal (car xs) #\;)
			nil
			(cons (car xs) (getAction (cdr xs))))))
	
; (getActionKey xs)
; Acquires the key value that is associated with the key/value string.
;
; xs - the list of characters that contain the key/value
(defun getActionKey (xs)
	(if (endp xs)
		xs
		(if (equal (car xs) #\=)
			nil
			(if (or (equal (car xs) #\Space)
					  (equal (car xs) #\Newline)
					  (equal (car xs) #\Page)
					  (equal (car xs) #\Tab))
				(getActionKey (cdr xs))
				(cons (car xs) (getActionKey (cdr xs)))))))

; (getActionType xs)
; Acquires the action key value that occurs before the = sign.
;
; xs - the list of characters that contains the key.
(defun getActionType (xs)
	(let* ((key (getActionKey xs))
		    (value (cdr (member-equal #\= xs))))
		(cons (coerce key 'string) (coerce value 'string))))

; (splitActions xs)
; Acquires the key / value pairs that will be used to invoke actions upon
; the server/client.
;
; xs - the list of characters that contain the key / value pair.
(defun splitActions (xs)
	(let* ((next (member-equal #\; xs)))
		(if next
			(cons (getActionType (getAction xs)) (splitActions (cdr next)))
			next)))
		
; (getActions xs)
; Acquires the actions header information from the string or list that is
; passed to this function.  If the value that is passed is a string, then
; it will convert it into a list of characters for processing.
;
; xs - the list or string that contains the header actions.
(defun getActions (xs)
	(if (listp xs)
		(if (endp xs)
			xs
			(if (equal (car xs) #\{)
				(let* ((actionString (getActionString (cdr xs))))
					(splitActions actionString))
				nil))
		(getActions (coerce xs 'list))))
