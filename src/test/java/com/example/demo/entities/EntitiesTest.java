package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.CmdParser;
import com.example.demo.parseEngine.EvalError;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EntitiesTest {

    Board board = new Board(10,10);
    Factory factory = Factory.instance();

    @Test
    void changeLocation() throws SyntaxErrorException, EvalError {
        Virus v = new Virus("move right",factory.newPair(2,2)); //expect result = 3,2
        Virus v1 = new Virus("move left",factory.newPair(3,2)); //expect result = 2,2
        //after parsing, both v and v1 shouldn't move. Their location should remain the same.
        //because both 3,2 and 2,2 are not available to move.
        Pair<Integer,Integer> vloc = v.location;
        Pair<Integer,Integer> v1loc = v1.location;
        CmdParser cmd = new CmdParser(new HashMap<>(),v.geneticCode,v);
        cmd = new CmdParser(new HashMap<>(),v1.geneticCode,v1);
        cmd.parseProgram();
        assertEquals(vloc,v.location);
        assertEquals(v1loc,v1.location);

        //lower boundary checking.
        Virus v2 = new Virus(" move  up  ",factory.newPair(0,0));
        Pair<Integer,Integer> v2loc = v2.location;
        cmd = new CmdParser(new HashMap<>(),v2.geneticCode,v2);
        cmd.parseProgram();
        assertEquals(v2loc,v2.location);

        //upper boundary checking.
        Virus v3 = new Virus("move down",factory.newPair(9,9));
        Pair<Integer,Integer> v3loc = v3.location;
        cmd = new CmdParser(null,v3.geneticCode,v3);
        cmd.parseProgram();
        assertEquals(v3loc,v3.location);

        //right location
        Virus v4 = new Virus("  {   move upright}",factory.newPair(6,2));
        cmd = new CmdParser(null, v4.geneticCode, v4);
        cmd.parseProgram();
        System.out.println(v4.getLocation());
        assertEquals(Board.getEntity(7,1),v4);
    }

    @Test
    void VirusAttackAntibody() throws SyntaxErrorException, EvalError {
        //set up environment
        Virus v = new Virus("shoot upright", factory.newPair(1,4));
        Antibody a = new Antibody(" ",factory.newPair(3,2));
        Virus dummy = new Virus(" ",factory.newPair(4,1));
        v.setAtk(30);v.setHP(100);v.setLifeGain(20);v.hp = 20;
        a.setAtk(20);a.setHP(100);
        dummy.setHP(100);dummy.setAtk(10);

        CmdParser cmd = new CmdParser(new HashMap<>(),v.geneticCode,v);
        cmd.parseProgram();

        assertEquals(70,a.hp);  //target should be Antibody a.
        assertEquals(100,dummy.hp); // Virus dummy shouldn't have been attacked. Hp should remain the same.
        assertEquals(40,v.hp);  //Virus v got some hp from successful attack.
    }

    @Test
    void antibodyAttackVirus() throws SyntaxErrorException, EvalError {
        //set up environment
        Virus v = new Virus("shoot upright", factory.newPair(1,4));
        Antibody a = new Antibody(" shoot downleft",factory.newPair(3,2));
        Virus dummy = new Virus(" ",factory.newPair(4,1));
        v.setAtk(30);v.setHP(100);v.setLifeGain(20);
        a.setAtk(20);a.setHP(100);
        dummy.setHP(100);dummy.setAtk(10);

        CmdParser cmd = new CmdParser(new HashMap<>(),a.geneticCode,a);
        cmd.parseProgram();

        assertEquals(80,v.hp);
        assertEquals(100,dummy.hp);
    }

    @Test
    void VirusAttackVirus() throws SyntaxErrorException, EvalError {
        //set up env
        Virus v = new Virus("shoot upright", factory.newPair(1,4));
        Virus dummy = new Virus("shoot downleft",factory.newPair(4,1));
        v.setAtk(40);v.setHP(100);
        dummy.setHP(100);dummy.setAtk(10);

        CmdParser cmd = new CmdParser(new HashMap<>(),v.geneticCode,v);
        cmd.parseProgram();
        cmd = new CmdParser(new HashMap<>(),dummy.geneticCode,dummy);
        cmd.parseProgram();

        assertEquals(60,dummy.hp);  //both virus attacked each other.
        assertEquals(90,v.hp);      //the effect of lifeGain shouldn't be shown.
    }

    @Test
    void AntibodyAttackAntibody() throws SyntaxErrorException, EvalError {
        //set up env
        Antibody a = new Antibody(" shoot down",factory.newPair(2,2));
        Antibody a1 = new Antibody(" shoot up",factory.newPair(2,4));
        a.setAtk(30);a.setHP(120);
        a1.setAtk(20);a1.setHP(60);

        CmdParser cmd = new CmdParser(new HashMap<>(),a.geneticCode,a);
        cmd.parseProgram();
        cmd = new CmdParser(new HashMap<>(),a1.geneticCode,a1);
        cmd.parseProgram();

        assertEquals(100,a.hp);
        assertEquals(30,a1.hp);
    }

    @Test
    void AntibodyKillAntibody() throws SyntaxErrorException, EvalError {
        //set up env
        Antibody a = new Antibody(" shoot down",factory.newPair(2,2));
        Antibody a1 = new Antibody(" shoot up",factory.newPair(2,4));
        a.setAtk(30);a.setHP(120);
        a1.setAtk(20);a1.setHP(30);

        assertEquals(Board.getEntity(2,4),a1);
        CmdParser cmd = new CmdParser(new HashMap<>(),a.geneticCode,a);
        cmd.parseProgram();
        assertEquals(120,a.hp); //a's hp should remain the same.
        assertEquals(0,a1.hp);
        assertNotEquals(Board.getEntity(2,4),a1);   //a1 should be deleted from grid.
    }

    @Test
    void AntibodyKillVirus() throws SyntaxErrorException, EvalError {
        //set up env
        Antibody a = new Antibody(" shoot down",factory.newPair(2,2));
        Virus a1 = new Virus(" shoot up",factory.newPair(2,4));
        a.setAtk(30);a.setHP(120);a.setKillGain(40);
        a1.setAtk(20);a1.setHP(30);

        assertEquals(Board.getEntity(2,4),a1);
        CmdParser cmd = new CmdParser(new HashMap<>(),a1.geneticCode,a1);
        cmd.parseProgram();
        assertEquals(100,a.hp); //a's hp reduced from a1's attack.
        cmd = new CmdParser(new HashMap<>(),a.geneticCode,a);
        cmd.parseProgram();
        assertEquals(120,a.hp); //a's hp should increased from killgain.
        assertEquals(0,a1.hp);
        assertNotEquals(Board.getEntity(2,4),a1);   //a1 should be deleted from grid.
    }

    @Test
    void VirusKillVirus() throws SyntaxErrorException, EvalError {
        //set up env
        Virus v = new Virus("shoot upright", factory.newPair(1,4));
        Virus dummy = new Virus("shoot downleft",factory.newPair(4,1));
        v.setAtk(40);v.setHP(100);v.setLifeGain(30);
        dummy.setHP(30);dummy.setAtk(50);

        CmdParser cmd = new CmdParser(new HashMap<>(),dummy.geneticCode,dummy);
        cmd.parseProgram();
        assertEquals(50,v.hp); //v's hp decreased from dummy's attack.
        cmd = new CmdParser(new HashMap<>(),v.geneticCode,v);
        cmd.parseProgram();

        assertEquals(-10,dummy.hp);  //both virus attacked each other.
        assertEquals(50,v.hp);      //the effect of lifeGain shouldn't be shown. v's hp should remain the same.
    }

    @Test
    void VirusKillAntibody() throws SyntaxErrorException, EvalError {
        //set up environment
        Virus v = new Virus("shoot upright", factory.newPair(1,4));
        Antibody a = new Antibody(" ",factory.newPair(3,2));
        v.setAtk(30);v.setHP(100);v.setLifeGain(20);v.hp = 20;
        a.setAtk(20);a.setHP(30);

        CmdParser cmd = new CmdParser(new HashMap<>(),v.geneticCode,v);
        cmd.parseProgram();

        assertEquals(0,a.hp);  //target should be Antibody a.
        assertEquals(40,v.hp);  //Virus v got some hp from successful attack.
        assertTrue(Board.getEntity(3,2) instanceof Virus);
        assertEquals(Board.getEntity(3,2).getCode(),v.geneticCode); //new turn-virus has same genetic code with v.
    }

    @Test
    void nearbyEntity() throws SyntaxErrorException {
        Virus v = new Virus("{}",factory.newPair(1,4));
        Antibody a = new Antibody("{}",factory.newPair(2,3));
        Antibody a1 = new Antibody("{}",factory.newPair(3,1));
        //search for nearby entity of v.
        assertEquals(12,v.nearbyEntity(2));assertEquals(0,v.nearbyEntity(1));assertEquals(0,v.nearbyEntity(3));
        assertEquals(0,v.nearbyEntity(4));assertEquals(0,v.nearbyEntity(5));assertEquals(0,v.nearbyEntity(6));
        assertEquals(0,v.nearbyEntity(7));assertEquals(0,v.nearbyEntity(8));

        //search for nearby entity of a.
        assertEquals(11,a.nearbyEntity(6));assertEquals(0,a.nearbyEntity(1));assertEquals(0,a.nearbyEntity(2));
        assertEquals(0,a.nearbyEntity(3));assertEquals(0,a.nearbyEntity(4));assertEquals(0,a.nearbyEntity(5));
        assertEquals(0,a.nearbyEntity(7));assertEquals(0,a.nearbyEntity(8));

        //search for nearby entity of a1 , should not found.
        assertEquals(0,a1.nearbyEntity(1));assertEquals(0,a1.nearbyEntity(2));assertEquals(0,a1.nearbyEntity(3));
        assertEquals(0,a1.nearbyEntity(4));assertEquals(0,a1.nearbyEntity(5));assertEquals(0,a1.nearbyEntity(6));
        assertEquals(0,a1.nearbyEntity(7));assertEquals(0,a1.nearbyEntity(8));
    }

    @Test
    void nearbyVirus() throws SyntaxErrorException {
        Virus v = new Virus("{}",factory.newPair(1,3));
        Antibody a = new Antibody("{}",factory.newPair(3,1));
        assertEquals(26,a.nearbyVirus());
    }

    @Test
    void VirusAfar() throws SyntaxErrorException {
        Virus v = new Virus("{}",factory.newPair(0,0));
        Virus v1 = new Virus("{}",factory.newPair(4,4));
        assertEquals(48,v1.nearbyVirus());
    }

    @Test
    void NoVirus() throws SyntaxErrorException {
        Antibody a = new Antibody("{}",factory.newPair(1,2));
        Antibody a1 = new Antibody("{}", factory.newPair(2,1));
        assertEquals(0,a.nearbyVirus());
        assertEquals(0,a1.nearbyVirus());
    }

    @Test
    void VirusNotInPath() throws SyntaxErrorException {
        Virus v = new Virus("{}",factory.newPair(1,3));
        Virus v1 = new Virus("{}",factory.newPair(3,3));
        Antibody a = new Antibody("{}",factory.newPair(2,1));
        assertEquals(0,a.nearbyVirus());
    }

    @Test
    void NearestVirus() throws SyntaxErrorException {
        Virus v = new Virus("{}",factory.newPair(2,0));
        Virus v1 = new Virus("{}",factory.newPair(3,0));
        Virus v2 = new Virus("{}",factory.newPair(4,0));
        Antibody a = new Antibody("{}",factory.newPair(3,1));
        assertEquals(11,a.nearbyVirus());
    }

    @Test
    void nearbyAntibody() throws SyntaxErrorException {
        Antibody a = new Antibody("{}",factory.newPair(2,1));
        Antibody a1 = new Antibody("{}",factory.newPair(4,3));
        assertEquals(24,a.nearbyAntibody());
    }

    @Test
    void NoAntibody() throws SyntaxErrorException {
        Antibody a = new Antibody("{}",factory.newPair(2,0));
        Virus v = new Virus("{}",factory.newPair(1,0));
        assertEquals(0,a.nearbyAntibody());
        assertEquals(13,v.nearbyAntibody());
    }

    @Test
    void AntibodyNotInPath() throws SyntaxErrorException {
        Virus v = new Virus("{}",factory.newPair(1,1));
        Antibody a = new Antibody("{}",factory.newPair(0,3));
        Antibody a1 = new Antibody("{}",factory.newPair(2,4));
        assertEquals(0,v.nearbyAntibody());
    }

    @Test
    void NearestAntibody() throws SyntaxErrorException {
        Virus v = new Virus("{}",factory.newPair(0,0));
        Antibody a = new Antibody("{}",factory.newPair(2,0));
        Antibody a1 = new Antibody("{}",factory.newPair(2,2));
        assertEquals(23,v.nearbyAntibody());
    }
}