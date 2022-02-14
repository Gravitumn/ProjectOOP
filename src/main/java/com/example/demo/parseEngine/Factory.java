package com.example.demo.parseEngine;

public class Factory {

    private static Factory instance;

    public BinaryArithExpr newExpr1(Expr left, String op, Expr right){
        return new BinaryArithExpr(left,op,right);
    }

    public IntLit newExpr2(int val){
        return new IntLit(val);
    }

    public Variable newExpr3(String name){
        return new Variable(name);
    }


    public static Factory instance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }


}
