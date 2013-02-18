;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; server-email.lisp
;
; Created on February 17, 2013 by Wesley Howell.
;
; Purpose:
; This file will execute the email transition between clients. In order 
; for this to happen, the incoming email will need to be opened, then the 
; to field will need to be parsed into a list. Once this list is generated
; we can then send a copy of the email message to the receipent clients.
;
; CHANGE LOG:
; -----------------------------------------------------------------------
;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;