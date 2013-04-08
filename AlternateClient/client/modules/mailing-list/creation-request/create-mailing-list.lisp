;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; mailing-list.lisp                                                      
; Created on March 27, 2013 by Adam Ghodratnama
;
; This file will create a request to register a mailing list.  An XML mailing 
; list file will be created from the following arguments:
; -Mailing list name (name string)
; -List of adresses '('(domain1 name1)... '(domainN nameN))
; -And an owner (name password)
; 
; CHANGE LOG:
; ------------------------------------------------------------------------
; 2013-03-27	-	Initial file conception.
;                   -       Creation of XML Generating functions:
;                           addressesXML and ownerXML
; 2013-03-28        -       Creation of file output method
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(in-package "ACL2")
(include-book "../../../include/io-utilities")
(include-book "../../../include/xml-scanner")

; (addressesXML address)
; Generates the XML form for an address from the address structure.
; address - the address structure in the form of (domain name).
(defun addressesXML (address)
	(if (endp address)
		nil
			; Domain tag <domain>#PCDATA</domain>	
		(let* ((domain (concatenate 'string 
                           "<domain>" (car address) "</domain>"))
                       ; Name tag <name>#PCDATA</name>
                       (name (concatenate 'string
                           "<name>" (cadr address) "</name>"))
                       ; Create a list with the address fields in it
                       (regRequest (list domain name)))
			regRequest)))

;Generates the XML form for an owner for use in a mailing list
;owner - the owner structure in the form of (owner password)
(defun ownerXML (ownerInfo)
	(if (endp ownerInfo)
		nil
			; Name tag <name>#PCDATA</name>	
		(let* ((name (concatenate 'string 
                           "<name>" (car ownerInfo) "</name>"))
                       ; Password tag <password>#PCDATA</password>
                       (password (concatenate 'string
                           "<password>" (cadr ownerInfo) "</password"))
                       ; Create a list with the ownerInfo fields in it
                       (regRequest (list name password)))
			regRequest)))

; The purpose of this function is to create an XML file that requests
; access for the creation of a mailing list.
;
; The function will create an XML file containing the name of the mailing
; list, addresses to be sent to, and the owner
; password for a user
;
; Example call: (createRequest '("localhost" "adam" "password") timeStamp state)
;
(defun createRequest (name addresses owner timeStamp state)
  (let* ((domain (car (addressesXML addresses)))
         (name (cadr (addressesXML addresses)))
         (ownerName (car (ownerXML owner)))
         (ownerPass (cadr (ownerXML owner))))
    (mv-let (error state)
            (string-list->file
             (concatenate 'string
                  (string-append "request-mailing-list" (rat->str timeStamp 0))
                  ".xml")
             (list 
                  "<?xml version='1.0'?>"
                  "<!DOCTYPE mailing-list SYSTEM '../../../dtd/mailing-list.dtd'>"
                  (concatenate 'string
                               (string-append "<list-name>" name)
                               "</list-name>")
                  "<address>" 
                  domain 
                  name 
                  "</address>"
                  "<owners>"
                  "<owner>"
                  ownerName
                  ownerPass
                  "</owner>"
                  "</owners>")
             state)
            (if error
                (mv error state)
                (mv "Wrote mailing-list creation request" state)))))