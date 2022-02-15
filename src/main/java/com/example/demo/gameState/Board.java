package com.example.demo.gameState;

import com.example.demo.entities.*;

public class Board{
    //for now, assume that the grid contains int.
    //which 0 = empty column.
    //1 = has virus.
    //2 = has antibody.
    private static Entity[][] grid;

    public Board(int m,int n){
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
}
