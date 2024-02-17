package com.dashboardiot.connectionmqtt;

import java.util.List;

public class SensorInformation {
	private String devEUI;
    private String deviceName;
    private double humidity;
    private double temperature;
    private List<RxInfo> rxInfo;
    private String time;

    public String getDevEUI() {
        return devEUI;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemperature() {
        return temperature;
    }


    public List<RxInfo> getRxInfo() {
		return rxInfo;
	}

	public String getTime() {
        return time;
    }
}
