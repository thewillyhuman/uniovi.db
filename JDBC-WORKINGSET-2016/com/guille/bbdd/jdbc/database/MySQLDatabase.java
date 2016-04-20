package com.guille.bbdd.jdbc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabase extends Database {

	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	public MySQLDatabase() {
		try {
			Class.forName(MYSQL_DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("Error while registering MySQL Driver");
			e.printStackTrace();
		}
	}

	@Override
	public Connection connectDatabase(String protocol, String vendor, String driver, String server, String port,
			String databaseName, String user, String password) throws SQLException {
		String url = protocol + ":" + vendor + "://" + server + ":" + port + "/" + databaseName;
		this.conn = DriverManager.getConnection(url, user, password);
		System.out.println("--> Connection " + this.conn.toString() + " has been oppened succesfully.");
		return this.conn;
	}

}
