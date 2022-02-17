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
            cp.parseStatement(true);
            throw new Exception("not working as intended");
        }catch (Exception ex){
            System.out.println("This one is illegal.");
        }

        CmdParser cp1 = new CmdParser(null,"where's my money",e);
        try {
            cp1.parseStatement(true);
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
            cp1.parseStatement(true);
            throw new Exception("not working as intended");
        } catch (Exception ex) {
            System.out.println("illegal.");
        }

        CmdParser cp2 = new CmdParser(vars, "shoot down", e);            //tests shoot - still WIP
        cp2.parseStatement(true);
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
    }

}
