package com.example.demo.parseEngine;

import java.util.Map;

public class BinaryArithExpr implements Expr{
    private Expr left, right;
    private String op;
    public BinaryArithExpr(Expr left, String op, Expr right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }



    public int eval(Map<String, Integer> bindings) throws EvalError{
        int lv = left.eval(bindings);
        int rv = right.eval(bindings);
        if (op.equals("+")) return lv + rv;
        if (op.equals("-")) return lv - rv;
        if (op.equals("*")) return lv * rv;
        if (op.equals("/")) return lv / rv;
        if (op.equals("%")) return lv % rv;
        throw new EvalError("unknown op: " + op);
    }
    public void prettyPrint(StringBuilder s) {
        s.append("(");
        left.prettyPrint(s);
        s.append(op);
        right.prettyPrint(s);
        s.append(")");
    }

}
