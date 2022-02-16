package com.example.demo.gameState;

import com.example.demo.entities.*;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Board{
    private static final Factory factory = Factory.instance();
    static int x;
    static int y;
    private static Entity[][] grid;
    private static LinkedList<Entity> queue = new LinkedList<>();

    public Board(int m,int n){
        Board.x = m;
        Board.y = n;
        grid = new Entity[n][m];
    }

    public static Entity getEntity(int x,int y){
        return grid[x][y];
    }

    /**
     *
     * @param firstloc a pair of x and y coordinate for first location on grid.
     * @param secondloc a pair of x and y coordinate for second location on grid.
     * @return distance between these locations.
     */
    public static int findDistance(Pair<Integer,Integer> firstloc,Pair<Integer,Integer> secondloc){
        return Math.max(Math.abs(firstloc.fst()-secondloc.fst()),Math.abs(firstloc.snd()-secondloc.snd()));
    }

    /**
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @return true if grid[x][y] is actually virus, otherwise, false.
     */
    public static boolean IsVirus(int x,int y){
        if(grid[x][y]==null) return false;
        return grid[x][y].getClass().toString().equals("class com.example.demo.entities.Antibody");
    }

    /**
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @return true if grid[x][y] is actually antibody, otherwise, false.
     */
    public static boolean IsAntibody(int x,int y){
        if(grid[x][y]==null) return false;
        return grid[x][y].getClass().toString().equals("class com.example.demo.entities.Virus");
    }

    /**
     * add new Entity to the board.
     * @param e Specific entity
     * @param location pair of x coordinate and y coordinate.
     */
    public static void addEntity(Entity e, Pair<Integer,Integer> location){
        queue.add(e);
        grid[location.fst()][location.snd()] = e;
    }

    /**
     * make the entity passed as parameter move.
     * @param e entity which need to move
     * @param newLocation the new location for this entity
     * @return true if the new location is available for new entity(no entity in that location).
     *          otherwise, return false.
     * Side-effect : upon returning true, change location of this entity.
     */
    public static boolean move(Entity e,Pair<Integer,Integer> newLocation){
        if(!IsVirus(newLocation.fst(), newLocation.snd()) && !IsAntibody(newLocation.fst(), newLocation.snd()) &&
                newLocation.fst() >= 0 && newLocation.fst() <= x &&
                newLocation.snd() >= 0 && newLocation.snd() <= y)
        {
            grid[newLocation.fst()][newLocation.snd()] = e;
            return true;
        }
        else return false;
    }

    public static Pair<Integer,Integer> size(){
        return factory.newPair(Board.x,Board.y);
    }
}
