package com.example.demo.parseEngine;

import java.util.Map;


public interface Expr {
    //for pretty Printing
    void prettyPrint(StringBuilder s);

    //for finds value
    int eval(Map<String, Integer> bindings) throws EvalError;
}
