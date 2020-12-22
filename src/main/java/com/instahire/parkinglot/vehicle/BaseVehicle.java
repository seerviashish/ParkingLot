package com.instahire.parkinglot.vehicle;

public abstract class BaseVehicle {
    private final String registrationNo;
    private final String color;

    public BaseVehicle(String registrationNo, String color) {
        this.registrationNo = registrationNo;
        this.color = color;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public String getColor() {
        return color;
    }
}
