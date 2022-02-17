package com.example.demo;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.CmdParser;
import com.example.demo.parseEngine.EvalError;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void programParsingTest() throws SyntaxErrorException, EvalError {
        Entity e = new Antibody("",new Pair<>(2, 2));
        CmdParser cp = new CmdParser(null,"",e);
        try {
            cp.parseProgram();
            throw new Exception("not working as intended");
        }catch (Exception ex){
            System.out.println("This one is illegal.");
        }

        CmdParser cp1 = new CmdParser(null,"where's my money",e);
        try {
            cp1.parseProgram();
            throw new Exception("kek");
        }catch (Exception ex){
            System.out.println("illegal.");
        }
    }

    @Test
    void assignmentStatementTests() throws SyntaxErrorException, EvalError {
        //checks assignmentStatement
        Entity e = new Antibody("",new Pair<>(2, 2));
        Map<String, Integer> vars = new HashMap<>();
        CmdParser cp1 = new CmdParser(vars, "aw = 10", e);
        cp1.parseStatement(true);
        System.out.println("aw = " + vars.get("aw"));
        CmdParser cp2 = new CmdParser(vars, "eh12 = 11", e);                    //number included
        cp2.parseStatement(true);
        System.out.println("eh12 = " + vars.get("eh12"));

        CmdParser cp3 = new CmdParser(vars, "ok! = 22222", e);                //"alien char included"
        try {
            cp3.parseStatement(true);
            throw new Exception("not working as intended");
        } catch (Exception ex) {
            System.out.println("it's illegal.");
        }

        CmdParser cp4 = new CmdParser(vars, "ak47 444 444", null);            //no "=" in between
        try {
            cp4.parseStatement(true);
            throw new Exception("not working as intended");
        } catch (Exception ex) {
            System.out.println("This one is also illegal");
        }

        CmdParser cp5 = new CmdParser(vars, "12r = 555", null);                //numbers before letters
        try {
            cp5.parseStatement(true);
            throw new Exception("not working as intended");
        } catch (Exception ex) {
            System.out.println("And this one is illegal too");
        }

        CmdParser cp6 = new CmdParser(vars,"aa = 100 b = 10+aa",null);             //2 vars assignments
        cp6.parseProgram();
        System.out.println("aa = " + vars.get("aa") + " " + "\nb = " + vars.get("b"));

        CmdParser cp7 = new CmdParser(vars,"a =",null);                             //nothing after =
        try{
            cp7.parseProgram();
            throw new Exception("Not working as intended");
        }catch (Exception ex){
            System.out.println("Confirmed to be illegal.");
        }

        CmdParser cp8 = new CmdParser(vars,"aee = topkek topkek = 20",null);                             //assignment using a var not assigned before
        try{
            cp8.parseProgram();
            throw new Exception("Not working as intended");
        }catch (Exception ex){
            System.out.println("Caught committing a crime.");
        }
    }

    @Test
    void actionTests() throws SyntaxErrorException, EvalError {

        Map<String, Integer> vars = new HashMap<>();
        Board b = new Board(10, 10);                                    //initialize the board
        Entity e = new Antibody("",new Pair<>(2, 2));
        CmdParser cp = new CmdParser(vars, "move right", e);
        cp.parseStatement(true);
        System.out.println("Entity went right");

        CmdParser cp1 = new CmdParser(vars, "move kek", e);                //direction not one of eight directions
        try {
            cp1.parseProgram();
            throw new Exception("not working as intended");
        } catch (Exception ex) {
            System.out.println("WHICH WAY!?!?");
        }

        CmdParser cp1_e = new CmdParser(vars, "move", e);                //no direction
        try {
            cp1_e.parseProgram();
            throw new Exception("not working as intended");
        } catch (Exception ex) {
            System.out.println("illegal.");
        }

        CmdParser cp1_1 = new CmdParser(vars, "shoot this guy", e);         //direction not one of eight directions
        try {
            cp1_1.parseProgram();
            throw new Exception("not working as intended");
        } catch (Exception ex) {
            System.out.println("who to shoot at?");
        }

        CmdParser cp1_11 = new CmdParser(vars, "shoot", e);         //no direction
        try {
            cp1_11.parseProgram();
            throw new Exception("not working as intended");
        } catch (Exception ex) {
            System.out.println("where do you want to shoot?");
        }

        CmdParser cp2 = new CmdParser(vars, "shoot down", e);            //tests shoot
        cp2.parseProgram();

        CmdParser cp3 = new CmdParser(vars,"move up move downright", e);
        cp3.parseProgram();
    }

    @Test
    void blockTest() throws SyntaxErrorException, EvalError {
        Map<String, Integer> vars = new HashMap<>();
        Entity e = new Antibody("",new Pair<>(2, 2));
        CmdParser cp = new CmdParser(vars, "{}", e);
        try{                                                        //Empty {}
            cp.parseStatement(true);
        } catch (SyntaxErrorException ex) {
            System.out.println("Where statement");
        } catch (EvalError ex) {
            ex.printStackTrace();
        }

        CmdParser cp1 = new CmdParser(vars,"}",e);              //{ missing
        try{
            cp1.parseStatement(true);
        }catch (Exception ex){
            System.out.println("Illegal");
        }

        CmdParser cp2 = new CmdParser(vars,"{",e);              //} missing
        try{
            cp1.parseStatement(true);
        }catch (Exception ex){
            System.out.println("Illegal");
        }

        CmdParser cp3 = new CmdParser(vars,"{a = 4 b = 13*a c = b^a}",e);   //multiple assignments
        cp3.parseProgram();
        System.out.println("a = " + vars.get("a"));
        System.out.println("b = " + vars.get("b"));
        System.out.println("c = " + vars.get("c"));
    }

    @Test
    void ifTest(){

    }

    @Test
    void whileTest(){

    }
}