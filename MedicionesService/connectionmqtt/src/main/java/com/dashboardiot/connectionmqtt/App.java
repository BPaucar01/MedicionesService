package com.dashboardiot.connectionmqtt;

import org.eclipse.paho.client.mqttv3.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String broker = "tcp://192.168.100.109";
        String clientId = "AppMqttSuscriber";
        String topic = "UG63/milesight/uplink";
        MqttSubscriber mqttSubscriber = new MqttSubscriber();
        
        try (MqttClient client = new MqttClient(broker, clientId)) {
            client.setCallback(mqttSubscriber);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected");

            System.out.println("Subscribing to topic: " + topic);
            client.subscribe(topic);

        } catch (Exception 	e) {
            e.printStackTrace();
        }
    }
}
