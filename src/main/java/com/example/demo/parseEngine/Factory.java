package com.example.demo.parseEngine;

public class Factory {

    private static Factory instance;

    public BinaryArithExpr newArithExpr(Expr left, String op, Expr right){
        return new BinaryArithExpr(left,op,right);
    }

    public IntLit newIntlit(int val){
        return new IntLit(val);
    }

    public Variable newVar(String name){
        return new Variable(name);
    }


    public static Factory instance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }


}
