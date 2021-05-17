package com.example.proiecttppa.models;

public class Alarm {
    public String name;
    public String values;
    public boolean isActive;

    public Alarm(String name, String values, boolean isActive) {
        this.name = name;
        this.values = values;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "name='" + name + '\'' +
                ", values='" + values + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
