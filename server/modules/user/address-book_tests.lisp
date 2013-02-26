(include-book "address-book")

; Theorem:
; If the address is in the address book, then adding the address to 
; the address book results in the same address book structure.
(defthm address-is-in-book-adding-returns-original-book
	(implies (isInAddressBook addressBook address)
				(equal addressBook (addAddress addressBook address))))

; Theorem:
; If the address is not in the address book, then adding the address
; to the address book will return an address book with the length of 
; the original address book + 1.
(defthm address-is-not-in-book-add-length-test
	(implies (not (isInAddressBook addressBook address))
				(equal (+ (length addressBook) 1) 
						 (length (addAddress addressBook address)))))

; Theorem:
; If the address is in the address book, then removing the address from
; the address book will return an address book with the length of the 
; original address book - 1.
(defthm address-is-in-book-remove-length-test
	(implies (isInAddressBook addressBook address)
				(equal (- (length addressBook) 1)
						 (length (removeAddress addressBook address)))))

; Theorem:
; If the address is not in the address book, then remove the address from
; the address book results in the original address book (lengths are 
; equivalent).
(defthm address-is-not-in-address-book-remove-returns-original-book-test
	(implies (not (isInAddressbook addressBook address))
				(equal (length addressBook
						 (length (removeAddress addressBook address)))))
