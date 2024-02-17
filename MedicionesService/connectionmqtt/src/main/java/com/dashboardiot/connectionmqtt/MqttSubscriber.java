package com.dashboardiot.connectionmqtt;

import java.text.ParseException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.dashboardiot.connectiondatabase.DeviceIoT;
import com.dashboardiot.connectiondatabase.Measurement;
import com.google.gson.Gson;

public class MqttSubscriber implements MqttCallback{
	
	private Gson gson;
	
	

	public MqttSubscriber() {
		this.gson = new Gson();
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		String jsonPayload = new String(message.getPayload());
		
		 try {
	            // Convierte el JSON a la clase SensorData
	            SensorInformation sensorData = gson.fromJson(jsonPayload, SensorInformation.class);
	            System.out.println(jsonPayload);
	            saveDatabase(sensorData);	            

	        } catch (Exception e) {
	            // Si no se puede convertir a JSON, simplemente imprime el payload como cadena
	            System.out.println(e);
	        }
		
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
	
	public void saveDatabase(SensorInformation sensorData) throws ParseException {
		
		/*String nameGateway, macGateway = null;
		GatewayIoT gatewayIoT = null;
		//Comprueba que el elemento RxInfo no este vacio o nulo
        if (sensorData.getRxInfo() != null && !sensorData.getRxInfo().isEmpty()) {
        	nameGateway = sensorData.getRxInfo().get(0).getName();
        	macGateway = sensorData.getRxInfo().get(0).getMac();
            gatewayIoT = new GatewayIoT(nameGateway, macGateway);
            gatewayIoT.createRecordGateway();
            
        }
        
        //Guardar la información en la tabla dispositivoIoT
        DeviceIoT deviceIoT = new DeviceIoT(GatewayIoT.getGatewayByDireccionMAC(macGateway).getIdGateway(), sensorData.getDeviceName(), sensorData.getDevEUI());
        deviceIoT.createRecordDeviceIoT();*/
        
        //Guardar la información de las mediciones en la tabla mediciones
        int devEUI = DeviceIoT.getDeviceIoTByDeviceEUI(sensorData.getDevEUI()).getIdDevice();
        Measurement measurenmentIoT = new Measurement(devEUI, sensorData.getTemperature(), sensorData.getHumidity());
        measurenmentIoT.createMeasurementRecord();
	}

}
