package com.example.demo.parseEngine;

import java.util.Map;

public class IntLit implements Expr{
    private int val;
    public IntLit(int val) {
        this.val = val;
    }
    public int eval(Map<String, Integer> bindings) {
        return val;
    }
    public void prettyPrint(StringBuilder s) {
        s.append(val);
    }

}
