package com.dashboardiot.connectiondatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;

public class Measurement {
	private int idMeasurenment;
	private int idDeviceIoT;
	private double temperature;
	private double humidity;

	private static DatabaseConnection databaseConnection = new DatabaseConnection();

	public Measurement(int idDeviceIoT, double temperature, double humidity) {

		this.idDeviceIoT = idDeviceIoT;
		this.temperature = temperature;
		this.humidity = humidity;
	}

	public int getIdMeasurenment() {
		return idMeasurenment;
	}

	public int getIdDeviceIoT() {
		return idDeviceIoT;
	}

	public double getTemperature() {
		return temperature;
	}

	public double getHumidity() {
		return humidity;
	}
	
	public void createMeasurementRecord() throws ParseException {
		
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO medicion (iddispositivoiot, temperatura, humedad, fecha_creacion) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idDeviceIoT);
            statement.setDouble(2, temperature);
            statement.setDouble(3, humidity);
            
         // Parsear la cadena de tiempo y convertirla a java.sql.Date
            java.util.Date currentDate = new java.util.Date();
            Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
            statement.setTimestamp(4, currentTimestamp);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registro de medida insertado con éxito.");
            } else {
                System.out.println("No se pudo insertar el registro de medida.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(connection);
        }
    }
	
}
