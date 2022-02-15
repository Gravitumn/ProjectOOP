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
}
