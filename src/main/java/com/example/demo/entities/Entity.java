package com.example.demo.entities;

import com.example.demo.gameState.ConfigReader;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

public interface Entity {

    /**
     *
     * @return Genetic code of this Entity
     */
    String getCode();

    /**
     *
     * @return Pair of x coordinate and y coordinate of this entity.
     */
    Pair<Integer,Integer> getLocation();

    /**
     * change location of this entity on grid
     * @param direction should be integer 1-8 for which, 1 = up , 2 = upright ,..., 8 = upleft.
     *
     */
    void changeLocation(int direction) throws SyntaxErrorException;

    /**
     * perform a shoot in the given direction.
     * @param direction should be integer 1-8 for which, 1 = up , 2 = upright ,..., 8 = upleft.
     * @Side-effect The action performed to the nearest entity in the given direction.
     * @return true if target's hp reach 0, otherwise, false.
     */
    boolean Attack(int direction) throws SyntaxErrorException;

    /**
     * looking for an entity that is closest to this entity in the given direction.
     * @param direction integer 1-8
     *                  which 1 = up,2 = upright,..., 8 = upleft.
     * @return  distance between entity e and the founded entity, return 0 if not found.
     */
    int nearbyEntity(int direction) throws SyntaxErrorException;

    /**
     * looking for a virus that is closest to this entity.
     * @return  distance between entity e and the founded virus, return 0 if not found.
     */
    int nearbyVirus() throws SyntaxErrorException;

    /**
     * looking for an antibody that is closest to this entity.
     * @return  distance between entity e and the founded antibody, return 0 if not found.
     */
    int nearbyAntibody() throws SyntaxErrorException;


    /**
     * perform an action that this unit got attacked by an Entity(attacker).
     * @param attacker the entity which attacked this entity.
     * @Side-effect Decrease this entity's health. this entity will be deleted if hp reaches 0.
     * @return true if this entity's hp reach 0, otherwise, false.
     */
    boolean attacked(Virus attacker);

    boolean attacked(Antibody attacker);

    /**
     * set up status of this entity by using data from configuration file.
     * @param cf the configreader that used for set-up.
     */
    void setUpEntity(ConfigReader cf);
}
