(include-book "../../../include/io-utilities")
(include-book "../../../include/xml-scanner")
(in-package "ACL2")

(defun getDomain (tokens)
	(if (endp tokens)
		nil
		(if (equal "<domain>" (car tokens))
			(cadr tokens)
			(getDomain (cdr tokens)))))

(defun getName (tokens)
	(if (endp tokens)
		nil
		(if (equal "<name>" (car tokens))
			(cadr tokens)
			(getName (cdr tokens)))))

(defun getPassword (tokens)
	(if (endp tokens)
		nil
		(if (equal "<password>" (car tokens))
			(cadr tokens)
			(getPassword (cdr tokens)))))

(defun registerUser (xml)
	(let* ((tokens (tokenizeXML xml))
			 (domain (getDomain parsed))
			 (name   (getName   parsed))
			 (reg    (loadAddressBook)))))
