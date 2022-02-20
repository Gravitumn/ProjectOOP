package com.example.demo.gameState;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Virus;
import com.example.demo.parseEngine.Factory;
import org.junit.jupiter.api.Test;

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
}