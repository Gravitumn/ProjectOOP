package com.example.demo.entities;

public class Virus implements Entity {
    private String geneticCode;

    @Override
    public String getCode() {
        return this.geneticCode;
    }
}
