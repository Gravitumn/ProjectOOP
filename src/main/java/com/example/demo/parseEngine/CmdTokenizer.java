package com.example.demo.parseEngine;

public class CmdTokenizer implements Tokenizer{

    private String cmd;
    private String next;
    private int pos;

    public CmdTokenizer(String cmd) throws SyntaxErrorException {
        this.cmd = cmd;
        pos = 0;
        getNext();
    }


    @Override
    public String consume() throws SyntaxErrorException {
        String result = next;
        getNext();
        return result;
    }

    public String consume(String target) throws SyntaxErrorException {
        if(peek(target)) return consume();
        else throw new SyntaxErrorException();
    }

    @Override
    public String peek() { return next; }

    public boolean peek(String target){ return next.equals(target); }

    public void getNext() throws SyntaxErrorException {
        StringBuilder sb = new StringBuilder();
        char c = cmd.charAt(pos);
        while(pos<cmd.length()){
            if(isBlock(c)){
                pos++;
                break;
            }else if(isWhiteSpace(c)){
                pos++;
            }else{
                throw new SyntaxErrorException("Unknown Character: ");
            }

        }
        next = sb.toString();
    }

    private boolean isBlock(char c) { return c == '{' || c == '}'; }

    private boolean isWhiteSpace(char c) { return c == 32; }
}
