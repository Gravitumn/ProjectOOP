package com.example.demo.entities;

public class Antibody implements Entity {
    private String geneticCode;

    @Override
    public String getCode() {
        return this.geneticCode;
    }
}
