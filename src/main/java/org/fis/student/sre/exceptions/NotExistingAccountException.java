package org.fis.student.sre.exceptions;

public class NotExistingAccountException extends Exception {

    public NotExistingAccountException() {
        super("This username doesn't exist!");
    }
}
