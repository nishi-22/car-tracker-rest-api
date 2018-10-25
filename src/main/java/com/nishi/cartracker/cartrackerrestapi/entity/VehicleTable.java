package com.nishi.cartracker.cartrackerrestapi.entity;

public interface VehicleTable {
    String getVin();
    String getMake();
    String getYear();
    String getModel();
    String getRedlineRpm();
    String getMaxFuelVolume();
    String getLastServiceDate();
    String getHighAlerts();
}
