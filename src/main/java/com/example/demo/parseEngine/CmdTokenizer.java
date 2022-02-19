package com.example.demo.parseEngine;

public class CmdTokenizer implements Tokenizer{

    private String cmd;
    private String next;
    private int pos;

    public CmdTokenizer(String cmd) throws SyntaxErrorException {
        this.cmd = cmd;
        pos = 0;
        if(cmd.length()!=0)
            getNext();
        else
            next="";
    }


    @Override
    public String consume() throws SyntaxErrorException {
        String result = next;
        if(!atEndOfSauce())
            getNext();
        else
            next="";
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
            char c = cmd.charAt(pos);
            if (Character.isDigit(c)) {  // start of number
                s.append(c);
                for (pos++; pos < cmd.length() &&
                        Character.isDigit(cmd.charAt(pos)); pos++)
                    s.append(cmd.charAt(pos));

            } else if (Character.isLetter(c)) {  // start of string
                s.append(c);
                for (pos++; pos < cmd.length() &&
                        (Character.isLetter(cmd.charAt(pos)) ||
                        Character.isDigit(cmd.charAt(pos))); pos++) {
                    s.append(cmd.charAt(pos));
                }

            } else if (c == '+' || c == '-' || c == '*' || c == '/'
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


    //for while loop
    public char charAt(int index){
        return cmd.charAt(index);
    }

    public int currentPos(){
        return pos;
    }

    public void goTo(int desiredPos) throws SyntaxErrorException {
        pos = desiredPos;
        getNext();
    }

    //verifies if it reaches the end
    public boolean atEndOfSauce(){
    int tempPos=pos;
    if(tempPos>=0){
        while (tempPos < cmd.length() &&Character.isWhitespace(cmd.charAt(tempPos)))
            tempPos++;
    }
    return tempPos >= cmd.length();
    }
}
