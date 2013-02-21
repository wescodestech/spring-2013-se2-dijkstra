;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; register-user.lisp
;
; Created on February 14, 2013 by Matthew A. Crist.
;
; Purpose:
; This file contains the functions that are essential to adding a user to 
; the address book.
;
; CHANGE LOG:
; -----------------------------------------------------------------------
;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(in-package "ACL2")

(include-book "../../../include/io-utilities")
(include-book "../../../include/xml-scanner")
(include-book "../address-book")

; (getDomain tokens)
; Acquires the domain of the registration request from the token list.
; tokens	-	the scanned tokens from the incoming XML register request.
(defun getDomain (tokens)
	(if (endp tokens)
		nil
		(if (equal "<domain>" (caar tokens))
			(caadr tokens)
			(getDomain (cdr tokens)))))

; (getName tokens)
; Acquires the name of the registration request from the token list.
; tokens	-	the scanned tokens from the incoming XML register request.
(defun getName (tokens)
	(if (endp tokens)
		nil
		(if (equal "<name>" (caar tokens))
			(caadr tokens)
			(getName (cdr tokens)))))

; (getPassword tokens)
; Acquires the password of the registration request from the token list.
; tokens	-	the scanned tokens from the incoming XML register request.
(defun getPassword (tokens)
	(if (endp tokens)
		nil
		(if (equal "<password>" (caar tokens))
			(caadr tokens)
			(getPassword (cdr tokens)))))

(set-state-ok t)
(set-ignore-ok t)

; (registerUser inputXML outputXML)
(defun registerUser (regXML abXML state)
	(let* ((tokens (tokenizeXML regXML))
			 (domain (getDomain tokens))
			 (name (getName tokens))
			 (pass (getPassword tokens))
			 (addressBook (getAddressBook (tokenizeXML abXML))))
		(mv-let (error state)
			(string-list->file 
			   "../../../store/address-book/temp_address-book.xml" 
			   (getAddressBookXML (addAddress addressBook (list domain name)))
				state)
			(if error
				(mv error state)
				(mv "Wrote temp_address-book.xml successfully" state)))))
