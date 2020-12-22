package com.instahire.parkinglot;

import com.instahire.parkinglot.log.Log;
import com.instahire.parkinglot.parkingslot.BaseParkingSlot;
import com.instahire.parkinglot.parkingslot.ParkingSlot;
import com.instahire.parkinglot.vehicle.BaseVehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingLot {
    private final int totalParkingSlot;
    private static final Log logger = Log.getInstance();
    private final List<BaseParkingSlot> parkingSlotList;
    private final HashMap<String, Integer> vehicleToSlot;
    private final HashMap<String, BaseVehicle> vehicleByRegNo;
    private final HashMap<Integer, BaseParkingSlot> parkingSlotByNo;

    public ParkingLot(int totalParkingSlot) {
        this.totalParkingSlot = totalParkingSlot;
        this.vehicleByRegNo = new HashMap<>();
        this.parkingSlotByNo = new HashMap<>();
        this.vehicleToSlot = new HashMap<>();
        this.parkingSlotList = new ArrayList<>(totalParkingSlot);
        createParkingSlots();
    }

    private void createParkingSlots() {
        for (int i = 0; i < totalParkingSlot; i++) {
            BaseParkingSlot parkingSlot = new ParkingSlot(i + 1);
            parkingSlotList.add(parkingSlot);
            parkingSlotByNo.put(i + 1, parkingSlot);
        }
    }

    public boolean isSlotAvailable() {
        return vehicleToSlot.size() < totalParkingSlot;
    }

    public void enterVehicle(BaseVehicle vehicle) {
        BaseParkingSlot parkingSlot = getEmptyParkingSlot();
        parkingSlot.fillVehicle(vehicle);
        vehicleToSlot.put(vehicle.getRegistrationNo(), parkingSlot.getParkingSlotId());
        vehicleByRegNo.put(vehicle.getRegistrationNo(), vehicle);
        logger.writeMessage("[ENTER] vehicle : " + vehicle.getRegistrationNo() + " into slot :" + parkingSlot.getParkingSlotId());
    }

    public void exitVehicle(String regNo) throws Exception {
        if (vehicleByRegNo.containsKey(regNo)) {
            BaseVehicle vehicle = vehicleByRegNo.get(regNo);
            BaseParkingSlot parkingSlot = getParkingSlotOfVehicle(vehicle);
            parkingSlot.removeVehicle();
            vehicleToSlot.remove(vehicle.getRegistrationNo());
            vehicleByRegNo.remove(vehicle.getRegistrationNo());
            logger.writeMessage("[EXIT] vehicle : " + vehicle.getRegistrationNo() + " from slot :" + parkingSlot.getParkingSlotId());
            return;
        }
        throw  new RuntimeException("Vehicle does not exist");
    }

    public BaseParkingSlot getEmptyParkingSlot() {
        for (BaseParkingSlot slot : parkingSlotList) {
            if (slot.isParkingSlotAvailable()) {
                return slot;
            }
        }
        throw new IllegalStateException("Parking slot not available");
    }

    public BaseParkingSlot getParkingSlotOfVehicle(BaseVehicle vehicle) throws Exception {
        if (vehicleToSlot.containsKey(vehicle.getRegistrationNo())) {
            return parkingSlotByNo.get(vehicleToSlot.get(vehicle.getRegistrationNo()));
        }
        throw new Exception("Vehicle is not parked");
    }


    public List<BaseVehicle> getVehicleByColor(String color) {
        List<BaseVehicle> result = new ArrayList<>();
        for (BaseVehicle vehicle : vehicleByRegNo.values()) {
            if (vehicle.getColor().equals(color)) {
                result.add(vehicle);
            }
        }
        return result;
    }

    public BaseParkingSlot getParkingSlotByRegNo(String regNo) throws Exception {
        if (vehicleByRegNo.containsKey(regNo)) {
            return getParkingSlotOfVehicle(vehicleByRegNo.get(regNo));
        }
        throw new Exception("Vehicle is not parked");
    }

    public List<BaseParkingSlot> getParkingSlotsByVehicleColor(String color) {
        List<BaseParkingSlot> result = new ArrayList<>();
        for (BaseParkingSlot parkingSlot : parkingSlotList) {
            if (parkingSlot.getVehicle() != null && parkingSlot.getVehicle().getColor().equals(color)) {
                result.add(parkingSlot);
            }
        }
        return result;
    }


    public List<BaseParkingSlot> getAllEmptyParkingSlot() {
        List<BaseParkingSlot> result = new ArrayList<>();
        for (BaseParkingSlot slot : parkingSlotList) {
            if (slot.isParkingSlotAvailable()) {
                result.add(slot);
            }
        }
        return result;
    }


}
