;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; server-email.lisp
;
; Created on March 7, 2013 by Wesley R. Howell.
;
; Purpose:
; This file will execute the email generation from a client. 
;
; CHANGE LOG:
; 3/7/2013 - Created file to handled the client logic for email messages
; -----------------------------------------------------------------------
;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(in-package "ACL2")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;Base Lines
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defun break-at-set (delimiters xs)
  (if (or (not (consp xs))
          (member-equal (car xs) delimiters))
      (list '() xs)
      (let* ((first-thing (car xs))
             (break-of-rest (break-at-set delimiters (cdr xs)))
             (prefix (car break-of-rest))
             (suffix (cadr break-of-rest)))
          (list (cons first-thing prefix) suffix))))

(defun break-at (delimiter xs)
   (break-at-set (list delimiter) xs))

(defun getEmailXML (email)
  (if (endp email)
      nil
      (let* ((hd (concatenate 'string
                              "<?xml version='1.0'?>"
                              "<!DOCTYPE user SYSTEM '../../../dtd/messages.dtd'>"
                              "<email>"))
             (to (concatenate 'string
                              "<to>" (car (car (car email))) "@"
                              (car (cdr (car (car email))))
                              "</to>"))
             (from (concatenate 'string 
                                "<from>" (car (cadr email)) "@"
                                (car(cdr (cadr email)))"</from>"))
             (sub (concatenate 'string
                               "<subject>" (cadr (cdr email)) "</subject>"))
             (msg (concatenate 'string
                               "<content>" (cadr (cdr (cdr email))) 
                               "</content>"))
             (ft (concatenate 'string "</email>"
                              )))
              (list hd to from sub msg ft))))        

(defun getContactStructure (str) 
  (let* ((chrs (coerce str 'list))
         (name (car (break-at #\@ chrs)))
         (domain (cdr (break-at #\@ chrs))))
        (list  (coerce name 'string) 
              (coerce (cdr (car domain)) 
                      'string)))) 

(defun getEmailStructure (xml)
  (let* (;(xml (cdr (getEmailXMLTokens file)))
         (to (car (car (cdr (cdr xml)))))
         (from (car (car (cdr (cdr (cdr (cdr (cdr xml))))))))
         (sub (car (car (cddddr  (cddddr xml)))))
         (msg (car (car (cddddr (cddddr (cdddr xml)))))))
    (list (list (getContactStructure to)) 
                (getContactStructure from) sub msg)))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;Gets the string value
(defun splitToString (toString)
   (coerce toString 'string)    
)


(defun splitToStruct (toList)
     	(if (consp (car (cdr toList)))
            (cons (splitToString (car toList))
                    (splitToStruct 
                     (break-at #\, (cdr (car (cdr toList))))))
      		(list (splitToString (car toList)))
          )
)   


(defun splitToField (toString)
  (let* ((tos (break-at #\, (coerce toString 'list))))
    (splitToStruct tos)
  )
)

(defun generateEmailFromStrings (to from sub msg)
   (let* (
         (tos (getContactStructure to))
         (fr (getContactStructure from))
         (subject sub)
         (message msg))
         (list (list tos) fr subject message))
)

(defun multRecip (toList from sub msg)
	(if (consp (cdr toList))
     	(cons (getEmailXML (generateEmailFromStrings (car toList)
                                                  from sub msg))
                (multRecip (cdr toList) from sub msg))
     	(list (getEmailXML (generateEmailFromStrings (car toList) from sub msg)))
     ) 
)

(defun email (to from sub msg)
   (if (consp (cdr (splitToField to)))
     (multRecip (splitToField to) from sub msg) 
   	(getEmailXML (generateEmailFromStrings (car (splitToField to)) from sub msg)) 
   )
)





