package com.pdgvisual;

public class Agent {
    // private int lifePoints;
    private boolean cooperator;
    public int[] connections;
    private boolean alive;

    public Agent() {
        connections = new int[4];
        this.cooperator = true;
        this.alive = true; // Agent is alive if lifePoints > 0
    }

    public void setCooperator(boolean bool) {
        this.cooperator = bool;
    }

    // public int getLifePoints() {
    // return this.lifePoints;
    // }

    public boolean isCooperator() {
        return this.cooperator;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean isAlive) {
        this.alive = isAlive;
    }

    public String printAgent() {
        String rtn = "";

        if (this.alive) {
            if (this.cooperator) {
                rtn += " (Cooperator)";
            } else {
                rtn += " (Deafector) ";
            }
        } else {
            rtn += " (Dead-RIP)  ";
        }

        rtn += " [ ";
        for (int i = 0; i < this.connections.length; i++) {
            rtn += this.connections[i] + " ";
        }
        rtn += "] ";

        return rtn;
    }

    // Christina
    // private boolean set_live() {
    // if (this.lifePoints > 0) {// possibly change from zero, depending
    // this.alive = true;
    // } else {
    // this.alive = false;
    // }
    // return false;
    // }

}
