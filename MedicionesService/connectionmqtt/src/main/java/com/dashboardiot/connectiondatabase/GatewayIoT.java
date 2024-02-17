package com.dashboardiot.connectiondatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GatewayIoT {
	
	private String nombre;
	private String direccionMAC;
	private static DatabaseConnection databaseConnection = new DatabaseConnection();
	private int idGateway;
	
	
	public GatewayIoT(String nombre, String direccionMAC) {

		this.nombre = nombre;
		this.direccionMAC = direccionMAC;
	}
	
	
	
	public GatewayIoT(int idGateway, String nombre, String direccionMAC) {
		this.nombre = nombre;
		this.direccionMAC = direccionMAC;
		this.idGateway = idGateway;
	}



	public void createRecordGateway() {
		
		GatewayIoT existingGateway = getGatewayByDireccionMAC(direccionMAC);

	    if (existingGateway != null) {
	        return; // No realizar la inserción si ya existe
	    }
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = databaseConnection.getConnection();
			String query = "INSERT INTO gatewayiot (idSucursal, nombre, direccionMAC) VALUES (?, ?, ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, 1);
			statement.setString(2, nombre);
			statement.setString(3, direccionMAC);
		
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			databaseConnection.closeConnection(connection);
		}
		
	}
	
	public static GatewayIoT getGatewayByDireccionMAC(String direccionMAC) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM gatewayiot WHERE direccionMAC = ?")) {

            preparedStatement.setString(1, direccionMAC);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Construir y devolver un nuevo objeto GatewayIoT con los datos del resultado
                    int id = resultSet.getInt("idGateway");
                    String nombre = resultSet.getString("nombre");
                    return new GatewayIoT(id, nombre, direccionMAC);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Devolver null si no se encuentra el Gateway
    }



	public String getNombre() {
		return nombre;
	}



	public String getDireccionMAC() {
		return direccionMAC;
	}



	public int getIdGateway() {
		return idGateway;
	}
	
	
	
	
}
