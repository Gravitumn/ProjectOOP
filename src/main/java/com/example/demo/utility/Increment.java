package com.example.demo.utility;

import com.example.demo.parseEngine.SyntaxErrorException;

public class Increment {
    int ans;
    public Increment(int direction){
        this.ans = direction;
    }

    public int xIncrement() throws SyntaxErrorException {
        return switch (ans) {
            case 1, 5 -> 0;
            case 2, 3, 4 -> 1;
            case 6, 7, 8 -> -1;
            default -> throw new SyntaxErrorException("direction should be integer between 1-8.");
        };
    }

    public int yIncrement() throws SyntaxErrorException {
        return switch (ans) {
            case 1,2,8 -> -1;
            case 3,7 -> 0;
            case 6,5,4 -> 1;
            default -> throw new SyntaxErrorException("direction should be integer between 1-8.");
        };
    }
}
