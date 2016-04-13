package com.guille.bbdd.jdbc.lab12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Program {

	private static Connection conn = null;
	private static Statement stat = null;
	private static ResultSet rs = null;

	public static final String PROTOCOL_JDBC = "jdbc";
	public static final String VENDOR_ORACLE = "oracle";
	public static final String DRIVER_THIN = "thin";
	public static final String DEFAULT_SERVER = "156.35.94.99";
	public static final String DEFAULT_PORT = "1521";
	public static final String DEFAULT_DATABASE = "DESA";

	private static final String DESA_USER = "UO236856";
	private static final String DESA_PASS = "UO236856";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// Ejemplos para leer por teclado
		/*
		 * System.out.println("Leer un entero por teclado"); int entero =
		 * ReadInt(); System.out.println("Leer una cadena por teclado"); String
		 * cadena = ReadString(); System.out.println("Entero: " + entero +
		 * " Cadena: " + cadena);
		 */

		// Connecting to the database.
		Class.forName("oracle.jdbc.OracleDriver");
		conn = openDESADADatabase(DESA_USER, DESA_PASS);
		exercise1_1();
		exercise1_2();
		exercise2("rojo");
		closeConnection(conn);
	}

	/*
	 * 1. Mostrar por pantalla los resultados de las consultas 21 y 32 de la
	 * Pr�ctica 2. 1.1. 21. Obtener el nombre y el apellido de los clientes que
	 * han adquirido un coche en un concesionario de Madrid, el cual dispone de
	 * coches del modelo gti.
	 */
	public static void exercise1_1() throws SQLException {
		String query = ("SELECT DISTINCT clientes.nombre, clientes.apellido " + "FROM clientes, ventas, concesionarios "
				+ "WHERE clientes.dni=ventas.dni " + "AND ventas.cifc=concesionarios.cifc "
				+ "AND concesionarios.cifc IN "
				+ "( SELECT concesionarios.cifc from concesionarios, distribucion, coches "
				+ "WHERE concesionarios.cifc=distribucion.cifc " + "AND distribucion.codcoche=coches.codcoche "
				+ "AND coches.modelo='gti' )");

		rs = executeSQLInConn(query, conn);

		System.out.println("----- 1-1 -----");
		while (rs.next()) {
			String name = rs.getString(1);
			String surname = rs.getString(2);
			System.out.println(name + " " + surname);
		}

		stat.close();

	}

	/*
	 * 1.2. 32. Obtener un listado de los concesionarios cuyo promedio de coches
	 * supera a la cantidad promedio de todos los concesionarios.
	 */
	public static void exercise1_2() throws SQLException {

		String query = "SELECT distribucion.cifc cif, nombrec nombre, ciudadc ciudad, AVG(cantidad) cantidad "
				+ "FROM distribucion, concesionarios " + "WHERE concesionarios.cifc=distribucion.cifc "
				+ "GROUP BY distribucion.cifc, concesionarios.nombrec, concesionarios.ciudadc "
				+ "HAVING AVG(cantidad) > (SELECT AVG(cantidad) FROM distribucion)";

		rs = executeSQLInConn(query, conn);

		System.out.println("----- 1-2 -----");
		while (rs.next()) {
			String cif = rs.getString("cif");
			String nombre = rs.getString("nombre");
			String ciudad = rs.getString("ciudad");
			String cantidad = rs.getString("cantidad");

			System.out.println(cif + " " + nombre + " " + ciudad + " " + cantidad + " ");
		}

		rs.close();
		stat.close();
	}

	/*
	 * 2. Mostrar por pantalla el resultado de la consulta 6 de la Pr�ctica 2 de
	 * forma el color de la b�squeda sea introducido por el usuario. 6. Obtener
	 * el nombre de las marcas de las que se han vendido coches de un color
	 * introducido por el usuario.
	 */
	public static void exercise2(String color) throws SQLException {
		String query = "SELECT DISTINCT nombrem " + "FROM marcas, marco, coches, ventas "
				+ "WHERE marcas.cifm=marco.cifm " + "AND marco.codcoche=coches.codcoche "
				+ "AND coches.codcoche=ventas.codcoche " + "AND ventas.color = ?";
		PreparedStatement psQuery = conn.prepareStatement(query);
		psQuery.setString(1, color);
		rs = psQuery.executeQuery();
		System.out.println("----- 2 -----");
		while (rs.next()) {
			String nombre = rs.getString(1);
			System.out.println(nombre);
		}
		rs.close();
		psQuery.close();
		stat.close();

	}

	/*
	 * 3. Realizar una aplicaci�n en Java para ejecutar la consulta 27 de la
	 * Pr�ctica 2 de forma que los l�mites la cantidad de coches sean
	 * introducidos por el usuario. 27. Obtener el cifc de los concesionarios
	 * que disponen de una cantidad de coches comprendida entre dos cantidades
	 * introducidas por el usuario, ambas inclusive.
	 */
	public static void exercise3() {

	}

	/*
	 * 4. Realizar una aplicaci�n en Java para ejecutar la consulta 24 de la
	 * Pr�ctica 2 de forma que tanto la ciudad del concesionario como el color
	 * sean introducidos por el usuario. 24. Obtener los nombres de los clientes
	 * que no han comprado coches de un color introducido por el usuario en
	 * concesionarios de una ciudad introducida por el usuario.
	 */
	public static void exercise4() {

	}

	/*
	 * 5. Realizar una aplicaci�n en Java que haciendo uso de la instrucci�n SQL
	 * adecuada: 5.1. Introduzca datos en la tabla coches cuyos datos son
	 * introducidos por el usuario
	 */
	public static void exercise5_1() {

	}

	/*
	 * 5.2. Borre un determinado coche cuyo c�digo es introducido por el
	 * usuario.
	 */
	public static void exercise5_2() {

	}

	/*
	 * 5.3. Actualice el nombre y el modelo para un determinado coche cuyo
	 * c�digo es introducido por el usuario.
	 */
	public static void exercise5_3() {

	}

	/*
	 * 6. Invocar la funci�n y el procedimiento del ejercicio 10 de la Pr�ctica
	 * 10 desde una aplicaci�n Java. 10. Realizar un procedimiento y una funci�n
	 * que dado un c�digo de concesionario devuelve el n�mero ventas que se han
	 * realizado en el mismo. 6.1. Funcion
	 */
	public static void exercise6_1() {

	}

	/*
	 * 6.1. Procedimiento
	 */
	public static void exercise6_2() {

	}

	/*
	 * 7. Invocar la funci�n y el procedimiento del ejercicio 11 de la Pr�ctica
	 * 10 desde una aplicaci�n Java. 11. Realizar un procedimiento y una funci�n
	 * que dada una ciudad que se le pasa como par�metro devuelve el n�mero de
	 * clientes de dicha ciudad. 7.1. Funcion
	 */
	public static void exercise7_1() {

	}

	/*
	 * 7.2. Procedimiento
	 */
	public static void exercise7_2() {

	}

	/**
	 * Reads an string from the console.
	 * 
	 * @return the input as an string.
	 */
	@SuppressWarnings("resource")
	private static String ReadString() {
		return new Scanner(System.in).nextLine();
	}

	/**
	 * Reads an integer from the console.
	 * 
	 * @return the input as an integer.
	 */
	@SuppressWarnings("resource")
	private static int ReadInt() {
		return new Scanner(System.in).nextInt();
	}

	/**
	 * Opens a database connection.
	 * 
	 * @param protocol
	 *            to use by the connection.
	 * @param vendor
	 *            of the database.
	 * @param driver
	 *            for the connection.
	 * @param server
	 *            address.
	 * @param port
	 *            where the connection must call.
	 * @param database
	 *            name.
	 * @param user
	 *            of the connection.
	 * @param password
	 *            of the connection.
	 * 
	 * @return the opened connection.
	 * @throws SQLException
	 *             if there is any error while opening the database.
	 */
	private static Connection openOracleDatabase(String protocol, String vendor, String driver, String server,
			String port, String databaseName, String user, String password) throws SQLException {
		String url = protocol + ":" + vendor + ":" + driver + ":@" + server + ":" + port + ":" + databaseName;
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	/**
	 * 
	 * @param user
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	private static Connection openDESADADatabase(String user, String pass) throws SQLException {
		return openOracleDatabase(PROTOCOL_JDBC, VENDOR_ORACLE, DRIVER_THIN, DEFAULT_SERVER, DEFAULT_PORT,
				DEFAULT_DATABASE, user, pass);
	}

	/**
	 * Closes a given connection.
	 * 
	 * @param connection
	 *            to be closed.
	 */
	private static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("The connection couldn't be closed.");
			e.printStackTrace();
		}
	}

	/**
	 * Executes a sql in a given connection.
	 * 
	 * @param sql
	 *            sentence to execute.
	 * @param connection
	 *            where the sql will be executed.
	 * @return the result set.
	 * 
	 * @throws SQLException
	 */
	private static ResultSet executeSQLInConn(String sql, Connection conn) throws SQLException {
		stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		return rs;
	}

}
