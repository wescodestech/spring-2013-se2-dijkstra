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
; 2/18/2013 - Added to SVN Repo
; 2/18/2013 - Updated
; -----------------------------------------------------------------------
;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(in-package "ACL2")
(include-book "io-utilities" :dir :teachpacks)
(include-book "list-utilities" :dir :teachpacks)

(defun getEmailXML (email)
  (if (endp email)
      nil
      (let* ((to (concatenate 'string
                              "<to>" (car (car (car email))) ","
                              (car (cdr (car (car email))))
                              "</to>"))
             (from (concatenate 'string 
                                "<from>" (car (cadr email)) ","
                                (car(cdr (cadr email)))"</from>"))
             (sub (concatenate 'string
                               "<subject>" (cadr (cdr email)) "</subject>"))
             (msg (concatenate 'string
                               "<content>" (cadr (cdr (cdr email))) "</content>")))
              (list to from sub msg))))        
    

;(splitEmail email)
; Takes an Email and assigns it to one receipent
; This will be useful when we send an email with multiple receipients
; email - the email strucutre that is defined in the Data Structures of the
;         design. 
(defun splitEmail (email)
  (if (listp email) ;Checks if the structure is not empty,
                    ;We need to make sure the data is always correct 
      (let* ((tos (car email)) ;List of to emails
             (from (car (cdr email)));from address structure
             (sub (car (cdr (cdr email))));subject string
             (msg (car (cdr (cdr (cdr email))))));message string 
        (if (consp (cdr tos))
            (cons (list (car tos)  from sub msg) (splitEmail (list (cdr tos) from sub msg)))
            (list (list (car tos) from sub msg))))
      nil))

(defun writeEmail (email fOut state)
  (mv-let (error-close state)
          (string-list->file
           fOut
           (getEmailXML email)
           state
           )
          (if error-close
              (mv error-close state)
              (mv (string-append ", output file: " fOut)
                  state)
              )))

;test function
(splitEmail '( (("howell" "localhost") ("crist" "localhost") ("ghodratnama" "localhost")) 
               ("howell" "localhost") "sub" "msg"))

(writeEmail '((("howell" "localHost")) ("crist" "localhost") "SUB" "MSG")
               "testXML.xml" state)