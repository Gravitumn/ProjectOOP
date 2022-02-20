package com.example.demo.utility;

import com.example.demo.parseEngine.SyntaxErrorException;

public class Increment {
    int ans;

    public Increment(int direction) {
        this.ans = direction;
    }

    public int xIncrement() throws SyntaxErrorException {
        switch (ans) {
            case 1:
            case 5:
                return 0;
            case 2:
            case 3:
            case 4:
                return 1;
            case 6:
            case 7:
            case 8:
                return -1;
            default:
                throw new SyntaxErrorException("direction should be integer between 1-8.");
        }
    }

    public int yIncrement() throws SyntaxErrorException {
        switch (ans) {
            case 1: case 2: case 8: return -1;
            case 3: case 7: return 0;
            case 6: case 5: case 4: return 1;
            default: throw new SyntaxErrorException("direction should be integer between 1-8.");
        }
    }
}
