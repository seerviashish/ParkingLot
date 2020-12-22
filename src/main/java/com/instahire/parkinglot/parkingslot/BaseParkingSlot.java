package com.instahire.parkinglot.parkingslot;

import com.instahire.parkinglot.vehicle.BaseVehicle;

public abstract class BaseParkingSlot {
    private final Integer parkingSlotId;
    private BaseVehicle vehicle;

    public BaseParkingSlot(Integer parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    public void fillVehicle(BaseVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void removeVehicle() {
        this.vehicle = null;
    }

    public boolean isParkingSlotAvailable() {
        return this.vehicle == null;
    }

    public BaseVehicle getVehicle() {
        return this.vehicle;
    }

    public Integer getParkingSlotId() {
        return parkingSlotId;
    }
}
