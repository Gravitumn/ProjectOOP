package com.example.demo;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.CmdParser;
import com.example.demo.parseEngine.EvalError;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void programParsingTest() throws SyntaxErrorException {
        Entity e = new Antibody("",new Pair<>(2, 2));
        CmdParser cp = new CmdParser(null,"",e);
        parseWithErrors(cp,"This one is illegal.");

        CmdParser cp1 = new CmdParser(null,"where's my money",e);
        parseWithErrors(cp1,"Illegal.");
    }

    @Test
    void assignmentStatementTests() throws SyntaxErrorException, EvalError {
        //checks assignmentStatement
        Entity e = new Antibody("",new Pair<>(2, 2));
        Map<String, Integer> vars = new HashMap<>();

        CmdParser cp1 = new CmdParser(vars, "aw = 10", e);
        cp1.parseStatement(true);
        assertEquals(vars.get("aw"),10);

        CmdParser cp2 = new CmdParser(vars, "eh12 = 11", e);                    //number included
        cp2.parseStatement(true);
        assertEquals(vars.get("eh12"),11);

        CmdParser cp3 = new CmdParser(vars, "ok! = 22222", e);                //"alien char included"
        parseWithErrors(cp3,"It's illegal.");

        CmdParser cp4 = new CmdParser(vars, "ak47 444 444", null);            //no "=" in between
        parseWithErrors(cp4,"This one is also illegal");

        CmdParser cp5 = new CmdParser(vars, "12r = 555", null);                //numbers before letters
        parseWithErrors(cp5,"And this one is illegal too.");

        CmdParser cp6 = new CmdParser(vars,"aa = 100 b = 10+aa",null);             //2 vars assignments
        cp6.parseProgram();
        assertEquals(vars.get("aa"),100);
        assertEquals(vars.get("b"),110);

        CmdParser cp7 = new CmdParser(vars,"a =",null);                             //nothing after =
        parseWithErrors(cp7,"Confirmed to be illegal.");

        CmdParser cp8 = new CmdParser(vars,"aee = topkek topkek = 20",null);                             //assignment using a var not assigned before
        parseWithErrors(cp8,"Caught committing a crime.");
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
        parseWithErrors(cp1,"WHICH WAY!?!?!");

        CmdParser cp1_e = new CmdParser(vars, "move", e);                //no direction
        parseWithErrors(cp1_e,"move to which direction?");

        CmdParser cp1_1 = new CmdParser(vars, "shoot this guy", e);         //direction not one of eight directions
        parseWithErrors(cp1_1,"but where to?");

        CmdParser cp1_11 = new CmdParser(vars, "shoot", e);         //no direction
        parseWithErrors(cp1_11,"where to shoot?");

        CmdParser cp2 = new CmdParser(vars, "shoot down", e);            //tests shoot
        cp2.parseProgram();

        CmdParser cp3 = new CmdParser(vars,"move up move downright", e);
        cp3.parseProgram();
    }

    @Test
    void blockTest() throws SyntaxErrorException, EvalError {
        Map<String, Integer> vars = new HashMap<>();
        Entity e = new Antibody("",new Pair<>(2, 2));
        CmdParser cp = new CmdParser(vars, "{}", e);            //Empty {}
        parseWithErrors(cp,"emty");

        CmdParser cp1 = new CmdParser(vars,"}",e);              //{ missing
        parseWithErrors(cp1,"Illegal");

        CmdParser cp2 = new CmdParser(vars,"{",e);              //} missing
        parseWithErrors(cp2,"Also illegal");

        CmdParser cp3 = new CmdParser(vars,"{a = 4 b = 13*a c = b^a}",e);   //multiple assignments
        cp3.parseProgram();
        assertEquals(vars.get("a"),4);
        assertEquals(vars.get("b"),52);
        assertEquals(vars.get("c"),7311616);
    }

    @Test
    void ifTest() throws SyntaxErrorException {
        Entity e = new Antibody("",new Pair<>(3,3));
        Map<String,Integer> vars = new HashMap<>();
        vars.put("tester",10);

        CmdParser cp = new CmdParser(vars,"if",e);                                      //if not followed by anything
        parseWithErrors(cp,"Illegal, m8");

        CmdParser cp1 = new CmdParser(vars,"if Faily wasn't killed",e);                 //if followed by anything not syntax-legal
        parseWithErrors(cp1,"if what");

        CmdParser cp2 = new CmdParser(vars,"if (",e);                                   //if with () but it misses a statement and )
        parseWithErrors(cp2,"what's after (");

        CmdParser cp3 = new CmdParser(vars,"if (tester",e);                             //if w/o () fully closed
        parseWithErrors(cp3,"not closed 🙁");

        CmdParser cp4 = new CmdParser(vars,"if (tester)",e);

        CmdParser cp5 = new CmdParser(vars,"if (tester) a = 20",e);
//        assertEquals(vars.get("a"),20);

//        CmdParser cp6 = new CmdParser()



    }

    @Test
    void whileTest(){

    }

    /**
     * <p>Parses something with error(s) in the command to get an error. If an exception occurs,
     * the parser terminates, showing a message from {@code errMsg}.</p>
     *
     * <p>Side effect: if an error message does not appear, throw an exception to notify
     * the parser does not fail as intended.</p>
     * @param cp a parser to be used for tests
     * @param errMsg a message to indicate if an error happens due to syntax during parsing
     */
    private void parseWithErrors(CmdParser cp,String errMsg){
        try {
            cp.parseProgram();
            throw new Exception("not working as intended");
        }catch (Exception ex){
            System.out.println(errMsg);
        }
    }
}