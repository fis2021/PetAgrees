package org.fis.student.sre.exceptions;

public class WrongPasswordException extends Exception {

    public WrongPasswordException(){
        super("The password is incorrect. Please try again!");
    }
}
