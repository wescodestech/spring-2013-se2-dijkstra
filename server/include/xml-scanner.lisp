;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; xml-scanner.lisp
; Written by Matthew A. Crist on February 7, 2013.
;
; Purpose:
; This file contains the functions that are necessary for scanning XML 
; data and returning those tokens to the requester.
;
; Change Log:
; -----------------------------------------------------------------------
; 2013-02-19	-	Added support for XML reader to ignore whitespace.
;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(in-package "ACL2")

; (consume xs d)
; Consumes tokens until the delimiter is reached.
; xs - The list of characters to be consumed.
; d  - The delimiter to consume to.
(defun consume (xs d)
	(if (endp xs)
		xs
		(if (equal (car xs) d)
			(cons (car xs) nil)
			(cons (car xs) (consume (cdr xs) d)))))

; (tag xs)
; Discrimminates between openTag and closeTag and invokes consume to the 
; end of a tag is located and coerces that list into a string for usage.
; xs - The list of characters to locate the tag within.
(defun tag (xs)
	(let* ((guard (cadr xs)))
		(if (equal guard #\/)
			(list (coerce (consume xs #\>) 'string) "closeTag")
			(list (coerce (consume xs #\>) 'string) "openTag"))))

; (pcData xs)
; Acquires the string that is located between two tags.
; xs - The list of characters to find the PCDATA.
(defun pcData (xs)
	(list (coerce (reverse 
		(cdr (reverse (consume xs #\<)))) 'string) "PCDATA"))

; (nextToken xs)
; Acquires the next token in the XML string sequence.  This function is 
; called in a recursive fashion and will return all tokens associated with
; a token type.
; xs - The list of characters that will be tokenized.
(defun nextToken (xs)
	(if (endp xs)
		xs
		(let* ((x (car xs)))
			(if (equal x #\<)
				(cons (tag xs) 
					(nextToken (cdr (member-equal #\> (cdr xs))))) 
				(if (or (equal x #\space)
						  (equal x #\newline)
						  (equal x #\tab)
						  (equal x #\page))
					(nextToken (cdr xs))
					(cons (pcData xs) 
						(nextToken (member-equal #\< (cdr xs)))))))))

; (tokenizeXML xml)
; Tokenizes the xml and returns a list of all tokens and their types.
; xml - The string of XML that will be parsed.
(defun tokenizeXML (xml)
	(nextToken (coerce xml 'list)))
