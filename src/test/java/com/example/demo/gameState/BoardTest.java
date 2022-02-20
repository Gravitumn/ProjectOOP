package com.example.demo.gameState;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Virus;
import com.example.demo.parseEngine.CmdParser;
import com.example.demo.parseEngine.EvalError;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Factory factory = Factory.instance();

    @Test
    void getEntity() {
        Board board = new Board(10,10);
        Virus v = new Virus("kek",factory.newPair(1,1));
        assertNull(Board.getEntity(8,1));
        assertNull(Board.getEntity(10,10));
        assertEquals(v,Board.getEntity(1,1));
    }

    @Test
    void isVirus() {
        Board board = new Board(10,10);
        //case1 : empty location.
        assertFalse(Board.IsVirus(1,1));

        //case2 : out of upper bound.
        assertFalse(Board.IsVirus(10,10));

        //case3 : out of lower bound.
        assertFalse(Board.IsVirus(-1,-1));

        //case4 : right place.
        Virus v1 = new Virus("",factory.newPair(7,6));
        assertTrue(Board.IsVirus(7,6));

        //case 5 : replacement.
        Antibody a = new Antibody("",factory.newPair(5,5));
        Virus v2 = new Virus("",factory.newPair(5,5));
        assertFalse(Board.IsVirus(5,5));
    }

    @Test
    void isAntibody() {
        Board board = new Board(10,10);
        //case1 : empty location.
        assertFalse(Board.IsAntibody(0,1));

        //case2 : out of upper bound.
        assertFalse(Board.IsVirus(10,9));

        //case3 : out of lower bound.
        assertFalse(Board.IsVirus(-1,0));

        //case4 : right place.
        Antibody a1 = new Antibody("",factory.newPair(4,2));
        assertTrue(Board.IsAntibody(4,2));

        //case 5 : replacement.
        Virus v2 = new Virus("",factory.newPair(5,5));
        Antibody a = new Antibody("",factory.newPair(5,5));
        assertFalse(Board.IsAntibody(5,5));
    }

    @Test
    void moveTest() throws SyntaxErrorException, EvalError {
        Board board = new Board(10,10);
        Virus v1 = new Virus("move downright",factory.newPair(1,0));
        Antibody A1 = new Antibody("move down",factory.newPair(2,1));
        Antibody A2 = new Antibody("move right",factory.newPair(1,3));


        // action 1 : try to move v1 to unavailable grid.
        CmdParser cmd = new CmdParser(new HashMap<>(),v1.getCode(),v1);
        cmd.parseProgram();
        assertNotEquals(Board.getEntity(2,1),v1);
        assertEquals(Board.getEntity(1,0),v1);  //v1 remain the same location.

        //action 2 : try to move a1.
        cmd = new CmdParser(new HashMap<>(),A1.getCode(),A1);
        cmd.parseProgram();
        assertEquals(Board.getEntity(2,2),A1);
        assertNotEquals(Board.getEntity(2,1),A1);

        //action 3 : try to move v1 again
        cmd = new CmdParser(new HashMap<>(),v1.getCode(),v1);
        cmd.parseProgram();
        assertNotEquals(Board.getEntity(1,0),v1);
        assertEquals(Board.getEntity(2,1),v1);

        //action 4 : move a2
        cmd = new CmdParser(new HashMap<>(), A2.getCode(), A2);
        cmd.parseProgram();
        assertEquals(Board.getEntity(2,3),A2);
        assertNotEquals(Board.getEntity(1,3),A2);

        //action 5 : try to move a1 again.
        cmd = new CmdParser(new HashMap<>(),A1.getCode(),A1);
        cmd.parseProgram();
        assertNotEquals(Board.getEntity(2,3),A1);
        assertEquals(Board.getEntity(2,2),A1);
        assertEquals(Board.getEntity(2,3),A2);
    }

    @Test
    void moveOutofBoundTest() throws SyntaxErrorException, EvalError {
        Board board = new Board(10,10);
        Virus v1 = new Virus("move up",factory.newPair(2,0));
        Virus v2 = new Virus("move upleft",factory.newPair(0,2));
        Antibody a3 = new Antibody("move upright",factory.newPair(9,1));
        Antibody a4 = new Antibody("move right",factory.newPair(9,9));

        Map<String,Integer> map = new HashMap<>();
        CmdParser cmd = new CmdParser(map,v1.getCode(),v1);
        CmdParser cmd1 = new CmdParser(map,v2.getCode(),v2);
        CmdParser cmd2 = new CmdParser(map,a3.getCode(),a3);
        CmdParser cmd3 = new CmdParser(map,a4.getCode(),a4);
        cmd.parseProgram();
        cmd1.parseProgram();
        cmd2.parseProgram();
        cmd3.parseProgram();

        assertEquals(Board.getEntity(2,0),v1);
        assertEquals(Board.getEntity(0,2),v2);
        assertEquals(Board.getEntity(9,1),a3);
        assertEquals(Board.getEntity(9,9),a4);
    }
}