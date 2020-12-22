package com.instahire.parkinglot;


import com.instahire.parkinglot.parkingslot.BaseParkingSlot;
import com.instahire.parkinglot.vehicle.BaseVehicle;
import com.instahire.parkinglot.vehicle.Vehicle;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;

public class ParkingLotApplication {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.println("Enter the number of vehicles parking slot can handle.");
        int n = sc.nextInt();
        ParkingLot parkingLot = new ParkingLot(n);

        System.out.println("Select options:");
        System.out.println("=> Enter 1 to create entry of vehicle.");
        System.out.println("=> Enter 2 to remove entry of vehicle.");
        System.out.println("=> Enter 3 for query of parking slot.");
        System.out.println("=> Enter any number to exit.");
        int selectedOption = sc.nextInt();

        while (selectedOption >= 1 && selectedOption <= 3) {
            switch (selectedOption) {
                case 1:
                    BaseVehicle vehicle = takeVehicleInput();
                    if (!parkingLot.isSlotAvailable()) {
                        System.out.println("[ERROR] : slot is not available");
                    } else {
                        parkingLot.enterVehicle(vehicle);
                    }
                    break;
                case 2:
                    String regNo = sc.next();
                    try {
                        parkingLot.exitVehicle(regNo);
                    } catch (Exception e) {
                        System.out.println("[ERROR] : " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Q: Enter 1 to get vehicle by color");
                    System.out.println("Q: Enter 2 get parking slot by vehicle number");
                    System.out.println("Q: Count of parking slot by vehicle color");
                    System.out.println("Q: Count of all available empty parking slot count");
                    int query = sc.nextInt();
                    String inputValue;
                    switch (query) {
                        case 1:
                            System.out.println("Enter color of vehicle:");
                            inputValue = sc.next();
                            List<BaseVehicle> vehicles = parkingLot.getVehicleByColor(inputValue);
                            List<String> registrationNos = vehicles.stream().map(vehicle1 -> vehicle1.getRegistrationNo()).collect(toList());
                            System.out.println(registrationNos.toString());
                            break;
                        case 2:
                            System.out.println("Enter registration number of vehicle:");
                            inputValue = sc.next();
                            try {
                                BaseParkingSlot slot = parkingLot.getParkingSlotByRegNo(inputValue);
                                System.out.println(slot.getParkingSlotId());
                            } catch (Exception e) {
                                System.out.println("[ERROR] : " + e.getMessage());
                            }
                            break;
                        case 3:
                            System.out.println("Enter color of vehicle:");
                            inputValue = sc.next();
                            List<BaseParkingSlot> parkingSlots = parkingLot.getParkingSlotsByVehicleColor(inputValue);
                            System.out.println(parkingSlots.size());
                            break;
                        case 4:
                            List<BaseParkingSlot> availableSlots = parkingLot.getAllEmptyParkingSlot();
                            System.out.println(availableSlots.size());
                            break;
                    }
                default:
                    break;
            }
            selectedOption = sc.nextInt();
        }
    }

    private static BaseVehicle takeVehicleInput() {
        String regNo = sc.next();
        String color = sc.next();
        return new Vehicle(regNo, color);
    }
}
