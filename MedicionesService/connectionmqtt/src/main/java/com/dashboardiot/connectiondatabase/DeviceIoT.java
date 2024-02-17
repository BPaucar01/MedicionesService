package com.dashboardiot.connectiondatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceIoT {
	
	private int idDevice;
	private int idGateway;
	private String nombre;
	private String deviceEUI;
	
	private static DatabaseConnection databaseConnection = new DatabaseConnection();

	public DeviceIoT(int idDevice, int idGateway, String nombre, String deviceEUI) {

		this.idDevice = idDevice;
		this.idGateway = idGateway;
		this.nombre = nombre;
		this.deviceEUI = deviceEUI;
	}

	public DeviceIoT(int idGateway, String nombre, String deviceEUI) {

		this.idGateway = idGateway;
		this.nombre = nombre;
		this.deviceEUI = deviceEUI;
	}

	public int getIdDevice() {
		return idDevice;
	}

	public int getIdGateway() {
		return idGateway;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDeviceEUI() {
		return deviceEUI;
	}
	
	public void createRecordDeviceIoT() {
		
		DeviceIoT existingDevice = getDeviceIoTByDeviceEUI(deviceEUI);
		Connection connection = null;
        PreparedStatement statement = null;
        
        //Comprobar que el dispositivo no este en la base de datos
		if(existingDevice != null) {
			return;
		}
		
		//Insertar el dispositivo a la base de datos.
        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO dispositivoiot (idGateway, nombre, EUI) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idGateway);
            statement.setString(2, nombre);
            statement.setString(3, deviceEUI);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registro de DeviceIoT insertado con éxito.");
            } else {
                System.out.println("No se pudo insertar el registro de DeviceIoT.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(connection);
        }
    }
	
	public static DeviceIoT getDeviceIoTByDeviceEUI(String deviceEUI) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM dispositivoiot WHERE EUI = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, deviceEUI);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Construir y devolver un nuevo objeto DeviceIoT con los datos del resultado
                int idDevice = resultSet.getInt("idDispositivoIoT");
                int idGateway = resultSet.getInt("idGateway");
                String nombre = resultSet.getString("nombre");

                return new DeviceIoT(idDevice, idGateway, nombre, deviceEUI);
            } else {
                System.out.println("No se encontró ningún dispositivo DeviceIoT con el deviceEUI: " + deviceEUI);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            databaseConnection.closeConnection(connection);
        }

        return null; // Devolver null si no se encuentra el dispositivo DeviceIoT
    }
	
	
	
}
