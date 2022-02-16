package com.example.demo.parseEngine;

import java.util.Map;
import java.util.Random;

public class Variable implements Expr{
    private String name;
    public Variable(String name) {
        this.name = name;
    }
    public int eval(Map<String, Integer> bindings) throws EvalError{
        if(name.equals("random")){
            Random rand=new Random();
            return rand.nextInt(100);
        }
        if (bindings.containsKey(name))
            return bindings.get(name);
        throw new EvalError("undefined variable: " + name);
    }
    public void prettyPrint(StringBuilder s) {
        s.append(name);
    }

}
