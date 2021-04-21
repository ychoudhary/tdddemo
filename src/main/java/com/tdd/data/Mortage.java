package com.tdd.data;

public class Mortage {

    private final double interest;
    private final double total;

    public Mortage(double interest, double total) {
        this.interest = interest;
        this.total = total;
    }


    public double getInterest() {
        return interest;
    }


    public double getTotal() {
        return total;
    }
}