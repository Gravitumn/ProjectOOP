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
        Entity e = null;
        try{
           e = grid[x][y];
           return e;
        }catch (ArrayIndexOutOfBoundsException | NullPointerException err){
            throw new RuntimeException("Cannot locate entity outside grid range.");
        }
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
        return grid[x][y]instanceof Virus;
    }

    /**
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @return true if grid[x][y] is actually antibody, otherwise, false.
     */
    public static boolean IsAntibody(int x,int y){
        if(grid[x][y]==null) return false;
        return grid[x][y]instanceof Antibody;
    }

    /**
     * add new Entity to the board.
     * @param e Specific entity
     * @param location pair of x coordinate and y coordinate.
     */
    public static boolean addEntity(Entity e, Pair<Integer,Integer> location){
        if(isAvailable(location)) {
            queue.add(e);
            grid[location.fst()][location.snd()] = e;
            return true;
        }
        return false;
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
        if(isAvailable(newLocation))
        {
            grid[newLocation.fst()][newLocation.snd()] = e;
            return true;
        }
        else return false;
    }

    public static Pair<Integer,Integer> size(){
        return factory.newPair(Board.x,Board.y);
    }

    private static boolean isAvailable(Pair<Integer,Integer> location){
        return location.fst() >= 0 && location.fst() <= x &&
                location.snd() >= 0 && location.snd() <= y &&
                !IsVirus(location.fst(), location.snd()) &&
                !IsAntibody(location.fst(), location.snd());
    }
}
