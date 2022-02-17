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
        StringBuilder s = new StringBuilder();
        while (pos < cmd.length() && Character.isWhitespace(cmd.charAt(pos)))
            pos++;  // ignore whitespace
        if(pos==cmd.length()) return;
        char c = cmd.charAt(pos);
        if (Character.isDigit(c)) {  // start of number
            s.append(c);
            for (pos++; pos < cmd.length() &&
                    Character.isDigit(cmd.charAt(pos)); pos++)
                s.append(cmd.charAt(pos));
        }
        else if (Character.isLetter(c)) {  // start of string
            s.append(c);
            for (pos++; pos < cmd.length() &&
                    Character.isLetter(cmd.charAt(pos)) ||
                    Character.isDigit(cmd.charAt(pos)); pos++) {
                s.append(cmd.charAt(pos));
                if (pos == cmd.length()-1) break;
            }
        }
        else if (c == '+' || c == '-' || c == '*' || c == '/'
                || c == '%' || c == '(' || c == ')' || c == '^'
                || c == '=' || c == '{' || c == '}') {
            s.append(c);
            pos++;
        }
        else {
            throw new SyntaxErrorException("unknown character: " + c);
        }
        next = s.toString();

    }

    //verifies if it reaches the end
    public boolean atEndOfSauce(){
        return pos >= cmd.length();
    }
}
