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
; 2/27/2013 - Fixed the IO issue and now we can Call an XML file and 
;  		    Output a file from the same funciton. 
;             Note! This only pull the first message in the XML file!
; 2/26/2013 - Updated references to be relative instead of absolute for 
;             xml-scanner.lisp, io-utilities.lisp and list-utilities.lisp.
; 2/22/2013 - Finished the email input from file to file out
; 2/21/2013 - Worked on parsing XML documents and getting the email informatio
;             From the XML file
; 2/19/2013 - Moved from ./modules/user to ./modules/email sub directory.
; 2/18/2013 - Added to SVN Repo
; 2/18/2013 - Updated
; -----------------------------------------------------------------------
;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(in-package "ACL2")
(include-book "../../include/xml-scanner" :uncertified-okp t)
(include-book "../../include/io-utilities" :uncertified-okp t)
(include-book "../../include/list-utilities" :uncertified-okp t)
(set-state-ok t)


(defun getEmailXML (email)
  (if (endp email)
      nil
      (let* ((hd (concatenate 'string
                              "<?xml version='1.0'?>"
                              "<!DOCTYPE user SYSTEM '../../../dtd/messages.dtd'>"
                              "<messages>"
                              "<email>"))
             (to (concatenate 'string
                              "<to>" (car (car (car email))) ","
                              (car (cdr (car (car email))))
                              "</to>"))
             (from (concatenate 'string 
                                "<from>" (car (cadr email)) ","
                                (car(cdr (cadr email)))"</from>"))
             (sub (concatenate 'string
                               "<subject>" (cadr (cdr email)) "</subject>"))
             (msg (concatenate 'string
                               "<content>" (cadr (cdr (cdr email))) 
                               "</content>"))
             (ft (concatenate 'string "</email>"
                              "</messages>")))
              (list hd to from sub msg ft))))        
    
;(splitEmail email)
; Takes an Email and assigns it to one receipent
; This will be useful when we send an email with multiple receipients
; email - the email strucutre that is defined in the Data Structures of the
;         design. 
;(defun splitEmail (email)
;  (if (listp email) ;Checks if the structure is not empty,
;      (let* ((tos (car email)) ;List of to emails
;             (from (car (cdr email)));from address structure
;             (sub (car (cdr (cdr email))));subject string
;             (msg (car (cdr (cdr (cdr email))))));message string 
;        (if (consp (cdr tos))
;            (cons (list (car tos)  from sub msg) (splitEmail (list (cdr tos)
;                                                             from sub msg)))
;            (list (list (car tos) from sub msg))))
;      nil))

;;(writeEmail email fOut state)
;;Writes an email message based on the email data structure with
;;a single email address. Calls the getEmailXML function to generate
;;the output text.
;;email - the email data structure to output
;;fOut - the file path for the outputted file
;;state - the ACL2 state
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

;;;getEmailXMLTokens file
;;;This function will return the list of XML tokens for the email XML
;;;file - the filepath to the XML file
(defun getEmailXMLTokens (file state)
  (mv-let (input-xml error-open state)
          (file->string file state)
     (if error-open
         (mv error-open state)
         (mv (cdr (cdr (tokenizeXML input-xml))) state))))

;;getContactStructure string
;;This function will generate a contact structure
;;The delimiter right now is set at "," but can easily be changed to
;;the "@" symbol
;;string - the string to get the contact structure from
(defun getContactStructure (str) 
  (let* ((chrs (str->chrs str))
         (name (car (break-at #\, chrs)))
         (domain (cdr (break-at #\, chrs))))
        (list  (chrs->str name) (chrs->str (cdr (car domain))))))

;;getEmailStructure file
;;This function will generate the email data structure based on the 
;;FIRST email message in the tokenized XML file list. 
;;file - the xml file on the computer to parse
(defun getEmailStructure (xml)
  (let* (;(xml (cdr (getEmailXMLTokens file)))
         (to (car (car (cdr (cdr xml)))))
         (from (car (car (cdr (cdr (cdr (cdr (cdr xml))))))))
         (sub (car (car (cddddr  (cddddr xml)))))
         (msg (car (car (cddddr (cddddr (cdddr xml)))))))
    (list (list (getContactStructure to)) 
                (getContactStructure from) sub msg)))
  
;(consumeList xml)
;This will trash the top email message xml's tags and make the 
;next email message availiable to getEmailStructureList
;Warning! This requires the email XML to be in the exact format as
;on the project Wiki page
;xml- tokenized xml tags
(defun consumeList (xml)
  (cddddr (cddddr (cddddr (cddr xml)))))

;(getEmailStructureList xml)
;This funciton will take the email XML tags and create a list
;of email messages. 
;Warning! - this requires the xml format to the exact format as stated
;on the project wiki
;xml - the Tokenized list of xml tags
(defun getEmailStructureList (xml)
  (if (equal (car (car (cddddr (cddddr (cddddr (cddr xml)))))) "<email>")
      (cons (getEmailStructure xml) (getEmailStructureList (consumeList xml)))
      (list (getEmailStructure xml))
      ))

;getEmail file state
;This function's goal is to open an email XML file, 
;parse the xml file and output each individual message to a file. 
;The funciton below, runEmailOut, should be the recusive call for this
;function
;file - the file path to the file to open
;state - the ACL2 state
(defun getEmail (file state)
   (getEmailXMLTokens file state))
  

;(rwEmail fin fout state)
;This function is the entry point to parse a single email message from the
;XML file "fin"
;fin - input XML file
;fout - output XML filename
;state - ACL2 state
(defun rwEmail (fin fout state)
  (mv-let (input-as-string error-open state) 
	  (file->string fin state)
     (if error-open
         (mv error-open state)
         (mv-let (error-close state)
                 (string-list->file fout
                                  (getEmailXML (getEmailStructure 
                                              (cdr(cdr (cdr 
                                              (tokenizeXML input-as-string
                                                           ))))))
                                    state)  
            (if error-close
                (mv error-close state)
                (mv "Success File has been written!" 
                     state))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;test functions
;(splitEmail '( (("howell" "localhost") ("crist" "localhost") 
;                                       ("ghodratnama" "localhost")) 
;               ("howell" "localhost") "sub" "msg"))

;(writeEmail '((("howell" "localHost")) ("crist" "localhost") "SUB" "MSG")
;              "howell/testXML.xml" state)

;(cdr (getEmailXMLTok "howell/testXML.xml"))

;(getEmailStructure (cdr (getEmailXMLTok "howell/textXML.xml")))

;(getEmailStructure "howell/testXML.xml")