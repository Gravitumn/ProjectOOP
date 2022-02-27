package com.example.demo.gameState;

import com.example.demo.entities.*;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public class Board{
    GameState state;
    private static final Factory factory = Factory.instance();
    int x;
    int y;
    private final Entity[][] grid;
    private Set<Pair<Integer,Integer>> availableSpace = new HashSet<>();

    public Board(int m,int n,GameState state){
        this.state = state;
        this.x = m;
        this.y = n;
        grid = new Entity[n][m];
        setSpace();
    }

    public Entity getEntity(int x,int y){
        Entity e = null;
        try{
           e = grid[y][x];
           return e;
        }catch (ArrayIndexOutOfBoundsException | NullPointerException err){
            return null;
        }
    }

    /**
     *
     * @param firstloc a pair of x and y coordinate for first location on grid.
     * @param secondloc a pair of x and y coordinate for second location on grid.
     * @return distance between these locations.
     */
    public int findDistance(Pair<Integer,Integer> firstloc,Pair<Integer,Integer> secondloc){
        return Math.max(Math.abs(firstloc.fst()-secondloc.fst()),Math.abs(firstloc.snd()-secondloc.snd()));
    }

    /**
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @return true if grid[x][y] is actually virus, otherwise, false.
     */
    public boolean IsVirus(int x,int y){
        if(x >= this.x || y >= this.y || y < 0 || x < 0)return false;
        if(grid[y][x]==null) return false;
        return grid[y][x]instanceof Virus;
    }

    /**
     *
     * @param x x coordinate of the point.
     * @param y y coordinate of the point.
     * @return true if grid[x][y] is actually antibody, otherwise, false.
     */
    public boolean IsAntibody(int x,int y){
        if(x >= this.x || y >= this.y || y < 0 || x < 0)return false;
        if(grid[y][x]==null) return false;
        return grid[y][x]instanceof Antibody;
    }

    /**
     * add new Entity to the board.
     * @param e Specific entity
     * @param location pair of x coordinate and y coordinate.
     */
    public boolean addEntity(Entity e, Pair<Integer,Integer> location){
        if(isAvailable(location)) {
            if(e instanceof Antibody){
                state.credits -= ((Antibody) e).getCost();
                state.antibodyList.add((Antibody) e);
            }
            else if (e instanceof Virus){
                state.virusList.add((Virus) e);
            }
            availableSpace.remove(location);
            state.queue.add(e);
            grid[location.snd()][location.fst()] = e;
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
    public boolean move(Entity e,Pair<Integer,Integer> newLocation){
        if(isAvailable(newLocation))
        {
            availableSpace.add(e.getLocation());
            grid[e.getLocation().snd()][e.getLocation().fst()] = null;
            grid[newLocation.snd()][newLocation.fst()] = e;
            availableSpace.remove(newLocation);
            return true;
        }
        else return false;
    }

    public Pair<Integer,Integer> size(){
        return factory.newPair(this.x,this.y);
    }

    private boolean isAvailable(Pair<Integer,Integer> location){
        return location.fst() >= 0 && location.fst() < x &&
                location.snd() >= 0 && location.snd() < y &&
                !IsVirus(location.fst(), location.snd()) &&
                !IsAntibody(location.fst(), location.snd());
    }

    public void delete(Entity e){
        Pair<Integer,Integer> loc = e.getLocation();
        grid[loc.snd()][loc.fst()] = null;
        availableSpace.add(loc);
        if(e instanceof Virus)
            state.virusList.remove(e);
        else if(e instanceof Antibody)
            state.antibodyList.remove(e);
        state.queue.remove(e);
    }

    public void turnVirus(Entity e,String geneticCode){
        Pair<Integer,Integer> loc = e.getLocation();
        this.delete(e);
        Virus v = new Virus(geneticCode,loc,this);
    }

    public void setSpace(){
        for(int i = 0;i<x;i++){
            for(int j = 0;j<x;j++){
                availableSpace.add(factory.newPair(i,j));
            }
        }
    }

    public Set<Pair<Integer, Integer>> getAvailableSpace() {
        return availableSpace;
    }
}
