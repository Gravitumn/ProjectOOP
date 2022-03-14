package com.example.demo.utility;

public class Pair<T1, T2> {
    protected T1 fst;
    protected T2 snd;

    public Pair(T1 fst, T2 snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public T1 getfst() {
        return fst;
    }


    public T2 getsnd() {
        return snd;
    }

    @Override
    public String toString() {
        return "(" + fst + ", " + snd + ")";
    }
}
