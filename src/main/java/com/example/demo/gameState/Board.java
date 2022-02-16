package com.example.demo.gameState;

import com.example.demo.entities.*;
import com.example.demo.parseEngine.Factory;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Board{
    private static Factory factory = Factory.instance();
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
                newLocation.fst() >= 0 && newLocation.fst() <= n &&
                newLocation.snd() >= 0 && newLocation.snd() <= m)
        {
            grid[newLocation.fst()][newLocation.snd()] = e;
            return true;
        }
        else return false;
    }

    /**
     * looking for an entity that is closest to this entity in the given direction.
     * @param e the entity that will be center of radius.
     * @param direction integer 1-8
     *                  which 1 = up,2 = upright,..., 8 = upleft.
     * @return  distance between entity e and the founded entity, return 0 if not found.
     */
    public static int nearbyEntity(Entity e,int direction){
        int startX = e.getLocation().snd();
        int startY = e.getLocation().fst();

        int xIncrement = direction == 2||direction == 3||direction ==4? 1:direction == 6||direction==7||direction == 8? -1:0;
        int yIncrement = direction == 1||direction == 2||direction ==8? 1:direction == 4||direction==5||direction == 6? -1:0;

        startX += xIncrement;
        startY += yIncrement;
        while(!IsVirus(startX,startY) && !IsAntibody(startX,startY)){
            startX+=xIncrement;
            startY+=yIncrement;
            if(startX > n || startY > m || startX < 0 || startY < 0){
                return 0;
            }
        }
        int distance = findDistance(e.getLocation(), factory.newPair(startX,startY));
        if(IsVirus(startX,startY))
            return 10*distance +1;
        else
            return 10*distance+2;
    }

    /**
     * looking for a virus that is closest to this entity.
     * @param e the entity that will be center of radius.
     * @return  distance between entity e and the founded virus, return 0 if not found.
     */
    public static int nearbyVirus(Entity e){
        ArrayList<Integer> distance = new ArrayList<>();
        int startX = e.getLocation().snd();
        int startY = e.getLocation().fst();

        for(int i=1;i<=8;i++){
            int xIncrement = i == 2||i == 3||i ==4? 1:i == 6||i==7||i == 8? -1:0;
            int yIncrement = i == 1||i == 2||i ==8? 1:i == 4||i==5||i == 6? -1:0;
            while(!IsVirus(startX,startY)){
                startX += xIncrement;
                startY += yIncrement;
                startX+=xIncrement;
                startY+=yIncrement;
                if(startX > n || startY > m || startX < 0 || startY < 0){
                    break;
                }
            }
            distance.add(10*findDistance(e.getLocation(), factory.newPair(startX,startY))+1);
        }
        if(distance.isEmpty())
            return 0;
        else
            return Collections.min(distance);
    }

    /**
     * looking for an antibody that is closest to this entity.
     * @param e the entity that will be center of radius.
     * @return  distance between entity e and the founded antibody, return 0 if not found.
     */
    public static int nearbyAntibody(Entity e){
        ArrayList<Integer> distance = new ArrayList<>();
        int startX = e.getLocation().snd();
        int startY = e.getLocation().fst();

        for(int i=1;i<=8;i++){
            int xIncrement = i == 2||i == 3||i ==4? 1:i == 6||i==7||i == 8? -1:0;
            int yIncrement = i == 1||i == 2||i ==8? 1:i == 4||i==5||i == 6? -1:0;
            while(!IsAntibody(startX,startY)){
                startX += xIncrement;
                startY += yIncrement;
                startX+=xIncrement;
                startY+=yIncrement;
                if(startX > n || startY > m || startX < 0 || startY < 0){
                    break;
                }
            }
            distance.add(10*findDistance(e.getLocation(), factory.newPair(startX,startY))+2);
        }
        if(distance.isEmpty())
            return 0;
        else
            return Collections.min(distance);
    }
}
