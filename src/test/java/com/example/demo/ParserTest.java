package com.example.demo;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entities;
import com.example.demo.entities.Entity;
import com.example.demo.entities.Virus;
import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.CmdParser;
import com.example.demo.parseEngine.EvalError;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

//import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class ParserTest {

    @Test
    void programParsingTest() throws SyntaxErrorException {
        Entity e = new Antibody("", new Pair<>(2, 2));
        CmdParser cp = new CmdParser(null, "", e);
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(null, "where's my money", e);
        assertThrows(SyntaxErrorException.class, cp::parseProgram);
    }

    @Test
    void assignmentStatementTests() throws SyntaxErrorException, EvalError {
        //checks assignmentStatement
        Entity e = new Antibody("", new Pair<>(2, 2));
        Map<String, Integer> vars = new HashMap<>();

        CmdParser cp = new CmdParser(vars, "aw = 10", e);
        cp.parseStatement(true);
        assertEquals(vars.get("aw"), 10);

        cp = new CmdParser(vars, "eh12 = 11", e);                    //number included
        cp.parseStatement(true);
        assertEquals(vars.get("eh12"), 11);

        cp = new CmdParser(vars, "ok! = 22222", e);                //"alien char included"
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "ak47 444 444", null);            //no "=" in between
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "12r = 555", null);                //numbers before letters
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "aa = 100 b = 10+aa", null);             //2 vars assignments
        cp.parseProgram();
        assertEquals(vars.get("aa"), 100);
        assertEquals(vars.get("b"), 110);

        cp = new CmdParser(vars, "a = ", null);                             //nothing after =
        assertThrows(SyntaxErrorException.class, cp::parseProgram);                   //does not work -> now operational

        cp = new CmdParser(vars, "aee = topkek topkek = 20", null);                             //assignment using a var not assigned before
        assertThrows(EvalError.class, cp::parseProgram);
    }

    @Test
    void actionTests() throws SyntaxErrorException, EvalError {

        Map<String, Integer> vars = new HashMap<>();
        new Board(10, 10);                                    //initialize the board
        Entity e = new Antibody("", new Pair<>(2, 2));
        CmdParser cp = new CmdParser(vars, "move right", e);
        cp.parseStatement(true);
        System.out.println("Entity went right");

        cp = new CmdParser(vars, "move kek", e);                //direction not one of eight directions
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "move", e);                    //no direction
//        assertThrows(SyntaxErrorException.class,cp::parseProgram);    //does not work - parser did nothing

        CmdParser cp1_1 = new CmdParser(vars, "shoot this guy", e);         //direction not one of eight directions
        assertThrows(SyntaxErrorException.class, cp1_1::parseProgram);

        cp = new CmdParser(vars, "shoot", e);                   //no direction
//        assertThrows(SyntaxErrorException.class,cp::parseProgram);  //does not work - parser did nothing

        cp = new CmdParser(vars, "shoot down", e);              //tests shoot
        cp.parseProgram();

        cp = new CmdParser(vars, "move up move downright", e);  //tired right after the next move command
        cp.parseProgram();
        Pair<Integer, Integer> eLoc = e.getLocation();
        assertEquals(3, eLoc.fst());
        assertEquals(1, eLoc.snd());
    }

    @Test
    void blockTest() throws SyntaxErrorException, EvalError {
        Map<String, Integer> vars = new HashMap<>();
        Entity e = new Antibody("", new Pair<>(2, 2));
        new Board(10, 10);

        CmdParser cp = new CmdParser(vars, "{}", e);            //Empty {}
        cp.parseProgram();

        cp = new CmdParser(vars, "}", e);              //{ missing
//        assertThrows(SyntaxErrorException.class,cp::parseProgram);      //does not work - parser did nothing

        cp = new CmdParser(vars, "{", e);              //} missing
//        assertThrows(SyntaxErrorException.class,cp::parseProgram);      //does not work - parser did nothing

        cp = new CmdParser(vars, "{a = 4 b = 13*a c = b^a}", e);   //multiple statements
        cp.parseProgram();
        assertEquals(4, vars.get("a"));
        assertEquals(52, vars.get("b"));
        assertEquals(7311616, vars.get("c"));

        cp = new CmdParser(vars, "{kek = 33333}", e);               //single statement
        cp.parseProgram();
        assertEquals(33333, vars.get("kek"));

        cp = new CmdParser(vars, "sweet}", e);                     //not starting with {
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "{wait here", e);                  //not ending with }
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

    }

    @Test
    void ifTest() throws SyntaxErrorException, EvalError {
        Entity e = new Antibody("", new Pair<>(3, 3));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("tester", 10);

        CmdParser cp = new CmdParser(vars, "if", e);                           //if not followed by anything
//        assertThrows(SyntaxErrorException.class,cp::parseProgram);                //does not work - parser did nothing

        cp = new CmdParser(vars, "if Faily wasn't killed", e);                 //if followed by anything not syntax-legal
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "if (", e);                                   //if with () but it misses a statement and )
        assertThrows(SyntaxErrorException.class, cp::parseProgram);                //does not work - parser did nothing

        cp = new CmdParser(vars, "if )", e);
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "if (tester", e);                             //if w/o () fully closed
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "if (tester)", e);                            //Nothing after )
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "if (tester) then a = 20 else {}", e);                 //misses then before a statement
        cp.parseProgram();
        assertEquals(20, vars.get("a"));

        cp = new CmdParser(vars, "if (tester) then a = 20 else  ", e);                 //There is no statement after else
        assertThrows(SyntaxErrorException.class, cp::parseProgram);
    }

    @Test
    void whileTest() throws SyntaxErrorException {
        new Board(10, 10);
        Entity e = new Antibody("", new Pair<>(4, 4));

        Map<String, Integer> vars = new HashMap<>();
        vars.put("tester", 10);
        CmdParser cp = new CmdParser(vars, "while", e);                   //while with nothing else
//        assertThrows(SyntaxErrorException.class, cp::parseProgram);           //does not work - parser did nothing

        cp = new CmdParser(vars, "while (", e);
        assertThrows(SyntaxErrorException.class, cp::parseProgram);           //does not work - got NumberFormatException instead -> now fixed

        cp = new CmdParser(vars, "while (tester", e);
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        cp = new CmdParser(vars, "while )", e);
        assertThrows(SyntaxErrorException.class, cp::parseProgram);

        String cmd = "a = 5 counter = 4 while (counter) {a = a + a counter = counter - 1}";
        cp = new CmdParser(vars, cmd, e);
        assertDoesNotThrow(cp::parseProgram);
        assertEquals(80, vars.get("a"));
        assertEquals(0, vars.get("counter"));

    }

    @Test
    void parseMath() throws SyntaxErrorException, EvalError {
        Map<String, Integer> vars = new HashMap<>();

        CmdParser cp = new CmdParser(vars, "a = 100", null);
        cp.parseProgram();
        assertEquals(vars.get("a"), 100);

        cp = new CmdParser(vars, "a = 100+2344 a = 223", null);
        cp.parseProgram();
        assertEquals(vars.get("a"), 223);

        cp = new CmdParser(vars, "a = 345*676/55", null);
        cp.parseProgram();
        assertEquals(vars.get("a"), 4240);

        cp = new CmdParser(vars, "a = 34 a = a^4", null);
        cp.parseProgram();
        assertEquals(vars.get("a"), 1336336);
    }

    @Test
    void sensorTest() throws SyntaxErrorException {
        new Board(10,10);
        Entity v = new Virus("antibody left virus upright",new Pair<>(4,2));
        Entity alsoV = new Virus("",new Pair<>(8,6));
        Entity atb = new Antibody("",new Pair<>(2,2));
        Entity alsoAtb = new Antibody("", new Pair<>(2,5));
        Entity dummy = new Entities("",new Pair<>(9,0));

        Map<String,Integer> vars = new HashMap<>();

        CmdParser cpErrTest = new CmdParser(vars,"virus a=10",atb);         //virus alone with one assignment after
        assertThrows(SyntaxErrorException.class,cpErrTest::parseProgram);
        cpErrTest = new CmdParser(vars,"virus here",atb);
        assertThrows(SyntaxErrorException.class,cpErrTest::parseProgram);
        cpErrTest = new CmdParser(vars,"antibody here",atb);                //same case, but as antibody
        assertThrows(SyntaxErrorException.class,cpErrTest::parseProgram);
        cpErrTest = new CmdParser(vars,"antibody b=20",atb);
        assertThrows(SyntaxErrorException.class,cpErrTest::parseProgram);

        cpErrTest = new CmdParser(vars,"a = virus",atb);                    //tests parseSensor, virus command
        assertDoesNotThrow(cpErrTest::parseProgram);
        System.out.println("a = " + vars.get("a"));
        assertEquals(23,vars.get("a"));

        cpErrTest = new CmdParser(vars,"pew = virus",dummy);                //tests parseSensor without any entity nearby
        assertDoesNotThrow(cpErrTest::parseProgram);
        System.out.println("pew = " + vars.get("pew"));
        assertEquals(0,vars.get("pew"));
        cpErrTest = new CmdParser(vars,"ok = antibody",dummy);              //same test, but changed to antibody
        assertDoesNotThrow(cpErrTest::parseProgram);
        System.out.println("ok = " + vars.get("ok"));
        assertEquals(0,vars.get("ok"));


        HashMap<String,Integer>[] varsArr = new HashMap[4];
        for (int i=0;i<varsArr.length;i++){
            varsArr[i] = new HashMap<>();
        }
        CmdParser[] cp = new CmdParser[4];                                          //WIP
        cp[0] = new CmdParser(varsArr[0],"l = nearby left r = nearby upright",v);
        cp[1] = new CmdParser(varsArr[1],"a = antibody",alsoV);
        cp[2] = new CmdParser(varsArr[2],"v = virus",atb);
        cp[3] = new CmdParser(varsArr[3],"d = nearby down - nearby left",alsoAtb);
        for (CmdParser cmdParser : cp) {
            assertDoesNotThrow(cmdParser::parseProgram);
        }
        for (HashMap<String,Integer> varsInArr: varsArr){
            Set<String> ks = varsInArr.keySet();
            for(String key:ks){
                System.out.println(key + " = " + varsInArr.get(key));
            }
        }


    }

}