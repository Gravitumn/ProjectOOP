package com.example.demo.parseEngine;

public class SyntaxErrorException extends Exception{
    public SyntaxErrorException() { super(); }

    public SyntaxErrorException(String s) { super(s); }
}
