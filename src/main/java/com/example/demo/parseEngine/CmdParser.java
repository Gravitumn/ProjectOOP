package com.example.demo.parseEngine;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.gameState.Board;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class CmdParser{
    private CmdTokenizer tkz;
    public Map<String, Integer> vars;
    Factory factory;
    private final Entity current_unit;
    private Random rng;
    public boolean tired;

    private final ArrayList<String> reservedWord = new ArrayList<>(
            Arrays.asList("antibody","down","downleft","downright","else","if","left","move","nearby","right","shoot",
                    "then","up","upleft","upright","virus","while")
    );

    public CmdParser(Map<String, Integer> vars,String cmd,Entity e) throws SyntaxErrorException {
        this.tkz = new CmdTokenizer(cmd);
        this.current_unit = e;
        this.vars = vars;
        factory = Factory.instance();
        rng = new Random();
        tired=false;
    }

    public void parseProgram() throws SyntaxErrorException, EvalError {
        if(tkz.peek("")){
            blame("There are noting...");
        }
        while (!tkz.atEndOfSauce()){
            parseStatement(true);
        }
    }

    public void parseStatement(boolean exist) throws SyntaxErrorException, EvalError {
        String next = tkz.peek();
        switch (next){
            case  "":
                blame("There is no Statement");
            case "{" :
                parseBlock(exist);
                break;
            case "if" :
                parseIf(exist);
                break;
            case "while" :
                parseWhile(exist);
                break;
            default :
                parseCommand(exist);
        }
    }


    public void parseCommand(boolean exist) throws SyntaxErrorException, EvalError {
        String next = tkz.peek();
        switch (next){
            case "shoot":
            case "move" :
                parseActionCmd(exist);
                break;
            default :
                parseAssignmentStatement(exist);
        }

    }

    public void parseAssignmentStatement(boolean exist) throws SyntaxErrorException, EvalError {
        String next = tkz.consume();                                //var's name
        //checks for reserved words and character outside of letters
        if (!isVariable(next)) throw new SyntaxErrorException("Variable must start with letter followed by alphanumeric Character.");
        if(reservedWord.contains(next))throw new SyntaxErrorException("Variable must not be named after reserved words.");

        tkz.consume("=");
        Expr value = parseExpr();//value

        //exclusive for when someone tries to assign its value into something else
        if(exist){
            vars.put(next, value.eval(vars));
        }
    }

    public void parseActionCmd(boolean exist) throws SyntaxErrorException {
        String next = tkz.peek();
        switch (next){
            case "shoot" :
                parseATK(exist);
                break;
            case "move" :
                parseMove(exist);
                break;
        }
    }


    public void parseMove(boolean exist) throws SyntaxErrorException {
        tkz.consume("move");
        int direction = parseDirection();
        if (exist && tired){
            current_unit.changeLocation(direction);
            tired=true;
        }
    }

    public void parseATK(boolean exist) throws SyntaxErrorException {
        tkz.consume("shoot");
        int direction = parseDirection();
        if (exist && tired){
            current_unit.Attack(direction);
            tired=true;
        }
    }

    public Integer parseDirection() throws SyntaxErrorException {
        String next = tkz.consume();
        switch(next){
            case "up":
                return 1;
            case "upright":
                return 2;
            case "right":
                return 3;
            case "downright":
                return 4;
            case "down":
                return 5;
            case "downleft":
                return 6;
            case "left":
                return 7;
            case "upleft":
                return 8;
            default :
                blame("Incorrect direction: " + next);
        }
        return null;
    }

    public void parseIf(boolean exist) throws SyntaxErrorException, EvalError {
        tkz.consume("if");
        tkz.consume("(");
        Expr ex = parseExpr();
        tkz.consume(")");
        boolean condition;
        if(ex.eval(vars) > 0 && exist){
            condition=true;
        }
        else
            condition=false;
        tkz.consume("then");
        parseStatement(condition);
        tkz.consume("else");
        parseStatement(!condition);

    }

    public void parseWhile(boolean exist) throws SyntaxErrorException, EvalError {
        tkz.consume("while");
        tkz.consume("(");
        Expr ex = parseExpr();
        int returnPos = tkz.currentPos();
        if(tkz.atEndOfSauce()) blame();
        while(tkz.charAt(returnPos)==32) {
            returnPos++;
        }
        tkz.consume(")");

        for(int i=0;i<1000 && ex.eval(vars)>0;i++){
            parseStatement(exist);
            if(ex.eval(vars)>0) tkz.goTo(returnPos);
        }

    }

    public void parseBlock(boolean exist) throws SyntaxErrorException, EvalError {
        tkz.consume("{");
        while(!tkz.peek("}")){                  //this runs until it reaches }
            parseStatement(exist);
        }
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
            tkz.consume("^");
            v = factory.newArithExpr(v,"^",parsePower());
        }
        return v;
    }


    public Expr parsePower() throws SyntaxErrorException{
        if(tkz.peek("")){
            blame("There are no expression");
        }
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
        switch (next) {
            case "virus":
                return factory.newIntlit(current_unit.nearbyVirus());
            case "antibody":
                return factory.newIntlit(current_unit.nearbyAntibody());
            case "nearby":
                int direction = parseDirection();
                return factory.newIntlit(current_unit.nearbyEntity(direction));
            default:
                throw new SyntaxErrorException("SensorCommand Grammar : virus | antibody | nearby+direction ");
        }
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

    private boolean isVariable(String str){
        char[] ac=str.toCharArray();
        if (!Character.isLetter(ac[0]))
            return false;
        for(char i : ac){
            if(!Character.isLetter(i) && !Character.isDigit(i)){
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