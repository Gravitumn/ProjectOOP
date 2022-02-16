package com.example.demo.gameState;

import com.example.demo.entities.*;
import com.example.demo.utility.Pair;

import java.util.LinkedList;

public class Board{
    static int m;
    static int n;
    private static Entity[][] grid;
    private static LinkedList<Entity> queue = new LinkedList<>();

    public Board(int m,int n){
        Board.m = m;
        Board.n = n;
        grid = new Entity[m][n];
    }

    /**
     *
     * @param x1 x coordinate of the first point.
     * @param y1 y coordinate of the first point.
     * @param x2 x coordinate of the second point.
     * @param y2 y coordinate of the second point.
     * @return distance between two point on this grid.
     */
    public static int findDistance(int x1, int y1, int x2, int y2){
        return Math.max(Math.abs(x1-x2),Math.abs(y1-y2));
    }

    /**
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @return true if grid[x][y] is actually virus, otherwise, false.
     */
    public static boolean IsVirus(int x,int y){
        return grid[x][y].getClass().toString().equals("class com.example.demo.entities.Antibody");
    }

    /**
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @return true if grid[x][y] is actually antibody, otherwise, false.
     */
    public static boolean IsAntibody(int x,int y){
        return grid[x][y].getClass().toString().equals("class com.example.demo.entities.Virus");
    }

    /**
     * add new Entity to the board.
     * @param e Specific entity
     * @param location pair of x coordinate and y coordinate.
     */
    public static void addEntity(Entity e, Pair<Integer,Integer> location){
        addUnit(e);
        grid[location.fst()][location.snd()] = e;
    }

    private static void addUnit(Entity e){
        queue.add(e);
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
                newLocation.fst() >= 0 && newLocation.fst() <= n &&
                newLocation.snd() >= 0 && newLocation.snd() <= m)
        {
            grid[newLocation.fst()][newLocation.snd()] = e;
            return true;
        }
        else return false;
    }
}
