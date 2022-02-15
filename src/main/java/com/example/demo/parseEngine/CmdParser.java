package com.example.demo.parseEngine;

import java.util.Map;

public class CmdParser{
    private CmdTokenizer tkz;
    private Map<String, Integer> vars;
    Factory factory;

    public CmdParser(CmdTokenizer tkz, Map<String, Integer> vars) {
        this.tkz = tkz;
        this.vars = vars;
        factory = Factory.instance();
    }

    public void parseStatement() throws SyntaxErrorException {
        String next = tkz.peek();
        switch (next){
            case "{" -> parseBlock();
            case "if" -> parseIf();
            case "while" -> parseWhile();
            case "attack", "move", "kek" -> parseCommand();
            default -> blame("Unknown characters/word: " + next);
        }
    }


    public void parseCommand() throws SyntaxErrorException {
        String next = tkz.peek();
        switch (next){
            case "attack","move" -> parseActionCmd();
            default -> blame();
        }

    }

    public void parseAssignmentStatement() {
        String next = tkz.peek();
    }

    public void parseActionCmd() throws SyntaxErrorException {
        String next = tkz.peek();
        switch (next){
            case "attack" -> parseATK();
            case "move" -> parseMove();
        }
    }


    public void parseMove() throws SyntaxErrorException {
        tkz.consume("move");
        parseDirection();
    }

    public void parseATK() throws SyntaxErrorException {
        tkz.consume("attack");
        parseDirection();
    }

    public void parseDirection() throws SyntaxErrorException {
        String next = tkz.peek();
        //left blank for now - out of ideas
        switch(next){
            case "left" -> {}
            case "right" -> {}
            case "up" -> {}
            case "down" -> {}
            case "upleft" -> {}
            case "upright" -> {}
            case "downleft" -> {}
            case "downright" -> {}
            default -> blame("Incorrect direction: " + next);
        }
    }

    public void parseIf() throws SyntaxErrorException {
        tkz.consume("if");
        tkz.consume("(");
        parseExpr();
        tkz.consume(")");
//        parseStatement();
//        if(tkz.peek("else")) tkz.consume();
//        parseStatement();
    }

    public void parseWhile() throws SyntaxErrorException {
        tkz.consume("while");
        tkz.consume("(");
        parseExpr();
        tkz.consume(")");
        parseStatement();
    }

    public void parseBlock() throws SyntaxErrorException {
        tkz.consume("{");
        parseStatement();
        tkz.consume("}");
    }

    public Expr parseExpr() throws SyntaxErrorException {
        Expr ex = parseTerm();
        boolean plus = false;
        boolean minus = false;
        if(!tkz.atEndOfSauce()){
            plus = tkz.peek("+");
            minus = tkz.peek("-");
        }
        while (plus || minus) {
            tkz.consume();
            if (plus) ex = factory.newArithExpr(ex,"+",parseTerm());
            if (minus) ex = factory.newArithExpr(ex,"-",parseTerm());
            if(!tkz.atEndOfSauce()){
                plus = tkz.peek("+");
                minus = tkz.peek("-");
            }
            if (tkz.atEndOfSauce()) break;
        }
        return ex;
    }

    public Expr parseTerm() throws SyntaxErrorException {
        Expr ex = parseFactor();
        boolean mul = false;
        boolean div = false;
        boolean mod = false;
        if (!tkz.atEndOfSauce()) {
            mul = tkz.peek("*");
            div = tkz.peek("/");
            mod = tkz.peek("%");
        }
        while (mul || div || mod) {
            tkz.consume();
            if (mul) ex = factory.newArithExpr(ex, "*", parseFactor());
            if (div) ex = factory.newArithExpr(ex, "/", parseFactor());
            if (mod) ex = factory.newArithExpr(ex, "%", parseFactor());
            if (!tkz.atEndOfSauce()) {
                mul = tkz.peek("*");
                div = tkz.peek("/");
                mod = tkz.peek("%");
            }
            if (tkz.atEndOfSauce()) break;
        }
        return ex;
    }

    public Expr parseFactor() throws SyntaxErrorException{
        Expr v = parsePower();
        while (tkz.peek("^")){
            tkz.consume();
            v = factory.newArithExpr(v,"^",parsePower());
        }
        return v;
    }


    public Expr parsePower() throws SyntaxErrorException{
        if (isNumber(tkz.peek())) {
            return factory.newIntlit(Integer.parseInt(tkz.consume()));
        }
        if (isLetter(tkz.peek())) {
            if(tkz.peek("virus") || tkz.peek("antibody") || tkz.peek("nearby"))
                return parseSensor();
            else
                return factory.newVar(tkz.consume());
        }
        else if(tkz.peek("(")) {
            tkz.consume();
            Expr v = parseExpr();
            if (tkz.peek(")"))
                tkz.consume();
            else
                blame("SyntaxError");
            return v;
        }
        else{
            throw new SyntaxErrorException("SyntaxError");
        }
    }

    public Expr parseSensor() {
        return null;
    }

    private boolean isLetter(String str) {
        char[] ac=str.toCharArray();
        for(char i : ac){
            if(!Character.isLetter(i)){
                return false;
            }
        }
        return true;
    }


    private boolean isNumber(String str) {
        char[] ac=str.toCharArray();
        for(char i : ac){
            if(!Character.isDigit(i)){
                return false;
            }
        }
        return true;
    }

    //throw exceptions here and it's a lot shorter
    private void blame() throws SyntaxErrorException {
        throw new SyntaxErrorException();
    }

    private void blame(String msg) throws SyntaxErrorException{
        throw new SyntaxErrorException(msg);
    }
}