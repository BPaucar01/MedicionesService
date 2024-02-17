package com.dashboardiot.connectiondatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String url = "jdbc:mysql://localhost:3306/iotsmartlink-orion";
	private static final String user = "root";
	private static final String password = "";


	public Connection getConnection() {
		
		Connection connection = null;
		try {

			// Establecer la conexión
			connection = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	// Método para cerrar la conexión a la base de datos
	public void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
