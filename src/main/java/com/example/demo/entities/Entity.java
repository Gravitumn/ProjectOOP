package com.example.demo.entities;

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
    void changeLocation(int direction);
}
