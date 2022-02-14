package com.example.demo.parseEngine;

public interface Tokenizer {
    String consume() throws SyntaxErrorException;
    String peek();
}
