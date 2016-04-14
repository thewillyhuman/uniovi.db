package com.guille.bbdd.jdbc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDatabase extends Database {
	
	public static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	
	public OracleDatabase() {
		try {
			Class.forName(ORACLE_DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("Error while registering the Oracle Driver");
			e.printStackTrace();
		}
	}

	@Override
	public Connection connectDatabase(String protocol, String vendor, String driver, String server, String port,
			String databaseName, String user, String password) throws SQLException {
		String url = protocol + ":" + vendor + ":" + driver + ":@" + server + ":" + port + ":" + databaseName;
		this.conn = DriverManager.getConnection(url, user, password);
		return this.conn;
	}

}
