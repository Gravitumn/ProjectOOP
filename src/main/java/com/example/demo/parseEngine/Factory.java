package com.example.demo.parseEngine;

import com.example.demo.utility.Pair;

public class Factory {

    private static Factory instance;

    private Factory(){}                 //locked for this class only

    public BinaryArithExpr newArithExpr(Expr left, String op, Expr right){
        return new BinaryArithExpr(left,op,right);
    }

    public IntLit newIntlit(int val){
        return new IntLit(val);
    }

    public Variable newVar(String name){
        return new Variable(name);
    }

    public Pair<Integer,Integer> newPair(int fst,int snd) {return new Pair<>(fst,snd);}

    public static Factory instance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }
}
