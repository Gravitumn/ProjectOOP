package com.example.demo.parseEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class CmdParser{
    private CmdTokenizer tkz;
    private Map<String, Integer> vars;
    Factory factory;
    private final ArrayList<String> reservedWord = new ArrayList<>(
            Arrays.asList("antibody","down","downleft","downright","else","if","left","move","nearby","right","shoot",
                    "then","up","upleft","upright","virus","while")
    );

    public CmdParser(CmdTokenizer tkz, Map<String, Integer> vars) {
        this.tkz = tkz;
        this.vars = vars;
        factory = Factory.instance();
    }

    public void parseStatement() throws SyntaxErrorException, EvalError {
        String next = tkz.peek();
        switch (next){
            case "{" -> parseBlock();
            case "if" -> parseIf();
            case "while" -> parseWhile();
            case "shoot", "move", "kek" -> parseCommand();
            default -> blame("Unknown characters/word: " + next);
        }
    }


    public void parseCommand() throws SyntaxErrorException, EvalError {
        String next = tkz.peek();
        switch (next){
            case "shoot","move" -> parseActionCmd();
            default -> parseStatement();
        }

    }

    public void parseAssignmentStatement() throws SyntaxErrorException, EvalError {
        String next = tkz.consume();                                //var's name
        //checks for reserved words and character outside of letters
        if (!isVariable(next)) throw new SyntaxErrorException("Variable must start with letter followed by alphanumeric Character.");
        if(reservedWord.contains(next))throw new SyntaxErrorException("Variable must not be named after reserved words.");

        tkz.consume("=");
        Expr value = parseExpr();                                   //value
        vars.put(next,value.eval(vars));
    }

    public void parseActionCmd() throws SyntaxErrorException {
        String next = tkz.peek();
        switch (next){
            case "shoot" -> parseATK();
            case "move" -> parseMove();
        }
    }


    public void parseMove() throws SyntaxErrorException {
        tkz.consume("move");
        parseDirection();
    }

    public void parseATK() throws SyntaxErrorException {
        tkz.consume("shoot");
        parseDirection();
    }

    public String parseDirection() throws SyntaxErrorException {
        String next = tkz.peek();
        //left blank for now - out of ideas
        switch(next){
            case "left", "right", "up", "down",
                    "upleft", "upright", "downleft",
                    "downright" -> {return next;}
            default -> blame("Incorrect direction: " + next);
        }
        blame("Unknown Error.");
        return null;
    }

    public void parseIf() throws SyntaxErrorException, EvalError {
        tkz.consume("if");
        tkz.consume("(");
        Expr ex = parseExpr();
        tkz.consume(")");
        if(ex.eval(vars) >= 0){
            tkz.consume("then");
            parseStatement();
        }
        //now how can we parse else?
        else{
            if(tkz.peek("shoot") || tkz.peek("move")) {
                tkz.consume();
                tkz.consume();
            }else if(tkz.peek("")){

            }else blame("Unknown error");

            tkz.consume("else");
            parseStatement();
        }
//        parseStatement();
//        if(tkz.peek("else")) tkz.consume();
//        parseStatement();
    }

    public void parseWhile() throws SyntaxErrorException, EvalError {
        tkz.consume("while");
        tkz.consume("(");
        Expr ex = parseExpr();
        tkz.consume(")");
        for(int i=0;i<1000 || ex.eval(vars)>=0;i++){
            parseStatement();
        }

    }

    public void parseBlock() throws SyntaxErrorException, EvalError {
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
        if (isVariable(tkz.peek())) {
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

    public Expr parseSensor() throws SyntaxErrorException {
        String next = tkz.consume();
        String direction = parseDirection();
        return null;
    }

    //String restriction checkers
    private boolean isLetter(String str) {
        char[] ac=str.toCharArray();
        for(char i : ac){
            if(!Character.isLetter(i)){
                return false;
            }
        }
        return true;
    }

    private boolean isVariable(String str) {
        char[] ac=str.toCharArray();
        if (!Character.isLetter(ac[0]))
            return false;
        for(char i : ac){
            if(!Character.isLetter(i) || !Character.isDigit(i)){
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