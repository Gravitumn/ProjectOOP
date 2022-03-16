package com.example.demo.gameState;

import com.example.demo.entities.*;
import com.example.demo.parseEngine.Factory;
import com.example.demo.utility.Pair;

import java.util.*;

public class Board{
    GameState state;
    private static Map<Integer,Board> instances = new HashMap<>();

    private static final Factory factory = Factory.instance();
    private int x;
    private int y;
    private int universe;
    private final Entity[][] grid;
    private Set<Pair<Integer,Integer>> availableSpace = new HashSet<>();
    private ConfigReader cf = new ConfigReader();

    //interning
    public static Board instance(int universe){
        if(!instances.containsKey(universe)){
            //instantiate for first use
            instances.put(universe,new Board(universe));
        }
        return instances.get(universe);
    }

    private Board(int u){
        this.universe = u;
        int m = cf.getCoordinate().getfst();
        int n = cf.getCoordinate().getsnd();
        this.state = GameState.instance(u);
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
        return Math.max(Math.abs(firstloc.getfst()-secondloc.getfst()),Math.abs(firstloc.getsnd()-secondloc.getsnd()));
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
            grid[location.getsnd()][location.getfst()] = e;
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
            grid[e.getLocation().getsnd()][e.getLocation().getfst()] = null;
            grid[newLocation.getsnd()][newLocation.getfst()] = e;
            availableSpace.remove(newLocation);
            return true;
        }
        else return false;
    }

    public Pair<Integer,Integer> size(){
        return factory.newPair(this.x,this.y);
    }

    private boolean isAvailable(Pair<Integer,Integer> location){
        return location.getfst() >= 0 && location.getfst() < x &&
                location.getsnd() >= 0 && location.getsnd() < y &&
                !IsVirus(location.getfst(), location.getsnd()) &&
                !IsAntibody(location.getfst(), location.getsnd());
    }

    public void delete(Entity e){
        Pair<Integer,Integer> loc = e.getLocation();
        grid[loc.getsnd()][loc.getfst()] = null;
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
        Virus v = new Virus(geneticCode,loc,universe);
    }

    public void setSpace(){
        for(int i = 0;i<x;i++){
            for(int j = 0;j<y;j++){
                availableSpace.add(factory.newPair(i,j));
            }
        }
    }

    public Set<Pair<Integer, Integer>> getAvailableSpace() {
        return availableSpace;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Entity[][] getGrid() {
        return grid;
    }
}
