package com.guille.bbdd.jdbc.lab13;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.guille.bbdd.jdbc.database.Database;
import com.guille.bbdd.jdbc.database.OracleDatabase;

public class Report {

    private static Database DESA = new OracleDatabase();
    private static ResultSet rs = null;

    public static void main(String[] args) throws SQLException {
	try {
	    DESA.connectDatabase(Database.PROTOCOL_JDBC,
		    Database.VENDOR_ORACLE, Database.DRIVER_THIN,
		    Database.DEFAULT_SERVER, Database.DEFAULT_PORT,
		    Database.DEFAULT_DATABASE, Database.DESA_USER,
		    Database.DESA_PASS);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	listarCochesPorClientes();
	
	DESA.closeConnection();
	
    }

    /**
     * Prints a report for every client and its cars.
     * 
     * @throws SQLException
     */
    public static void listarCochesPorClientes() throws SQLException {
	String sql = "SELECT clientes.dni, clientes.nombre, clientes.apellido FROM CLIENTES";
	String carForClient = "SELECT coches.codcoche, coches.nombrech, coches.modelo, ventas.color "
		+ "FROM coches, ventas "
		+ "WHERE ventas.codcoche = coches.codcoche "
		+ "AND ventas.dni = ?";
	rs = DESA.executeSQL(sql);
	
	while(rs.next()) {
	    Integer dni = rs.getInt(1);
	    String name = rs.getString(2);
	    String surname = rs.getString(3);
	    System.out.println("Customer: " + name + " " + surname);
	    Object[] parameters = new Object[1];
	    parameters[0] = dni;
	    ResultSet subsearch = DESA.executeSQL(carForClient, parameters);
	    
	    while(subsearch.next()) {
		System.out.println("-----> Car: " + subsearch.getInt(1) + " " + subsearch.getString(2) + " " + subsearch.getString(3) + " " + subsearch.getString(4));
	    }
	}
    }
}
