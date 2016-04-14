package com.guille.bbdd.jdbc.lab12;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.guille.bbdd.jdbc.database.Database;
import com.guille.bbdd.jdbc.database.OracleDatabase;

public class Program {

	private static Database DESA = new OracleDatabase();
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
	
		// Connecting to the database.
		DESA.connectDatabase(PROTOCOL_JDBC, VENDOR_ORACLE, DRIVER_THIN, DEFAULT_SERVER, DEFAULT_PORT, DEFAULT_DATABASE, DESA_USER, DESA_PASS);
		
		//exercise1_1();
		//exercise1_2();
		//exercise2("rojo");
		//exercise5_1();
		//exercise5_2();
		printAllCars();
		//exercise5_3();
		
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

		rs = DESA.executeSQL(query);

		System.out.println("----- 1-1 -----");
		while (rs.next()) {
			String name = rs.getString(1);
			String surname = rs.getString(2);
			System.out.println(name + " " + surname);
		}


	}

	/*
	 * 1.2. 32. Obtener un listado de los concesionarios cuyo promedio de coches
	 * supera a la cantidad promedio de todos los concesionarios.
	 */
	public static void exercise1_2() throws SQLException {

		String query = "SELECT distribucion.cifc cif, nombrec nombre, ciudadc ciudad, AVG(cantidad) cantidad " 
				+ "FROM distribucion, concesionarios " + "WHERE concesionarios.cifc=distribucion.cifc "  //$NON-NLS-2$
				+ "GROUP BY distribucion.cifc, concesionarios.nombrec, concesionarios.ciudadc " 
				+ "HAVING AVG(cantidad) > (SELECT AVG(cantidad) FROM distribucion)"; 

		rs = DESA.executeSQL(query);

		System.out.println("----- 1-2 -----"); 
		while (rs.next()) {
			String cif = rs.getString("cif"); 
			String nombre = rs.getString("nombre"); 
			String ciudad = rs.getString("ciudad"); 
			String cantidad = rs.getString("cantidad"); 

			System.out.println(cif + " " + nombre + " " + ciudad + " " + cantidad + " ");  //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
	}

	/*
	 * 2. Mostrar por pantalla el resultado de la consulta 6 de la Pr�ctica 2 de
	 * forma el color de la b�squeda sea introducido por el usuario. 6. Obtener
	 * el nombre de las marcas de las que se han vendido coches de un color
	 * introducido por el usuario.
	 */
	public static void exercise2(String color) throws SQLException {
		String query = "SELECT DISTINCT nombrem " + "FROM marcas, marco, coches, ventas "  //$NON-NLS-2$
				+ "WHERE marcas.cifm=marco.cifm " + "AND marco.codcoche=coches.codcoche "  //$NON-NLS-2$
				+ "AND coches.codcoche=ventas.codcoche " + "AND ventas.color = ?";  //$NON-NLS-2$
		String[] parameters = new String[1];
		parameters[0] = color;
		rs = DESA.executeSQL(query, parameters);
		System.out.println("----- 2 -----"); 
		while (rs.next()) {
			String nombre = rs.getString(1);
			System.out.println(nombre);
		}

	}

	/*
	 * 3. Realizar una aplicaci�n en Java para ejecutar la consulta 27 de la
	 * Pr�ctica 2 de forma que los l�mites la cantidad de coches sean
	 * introducidos por el usuario. 27. Obtener el cifc de los concesionarios
	 * que disponen de una cantidad de coches comprendida entre dos cantidades
	 * introducidas por el usuario, ambas inclusive.
	 */
	public static void exercise3() throws SQLException {
		String sql = "SELECT DISTINCT concesionarios.cifc, SUM(distribucion.cantidad) stock " 
				+ "FROM distribucion, concesionarios " 
				+ "WHERE distribucion.cifc=concesionarios.cifc " 
				+ "GROUP BY concesionarios.cifc " 
				+ "HAVING SUM(distribucion.cantidad) >=? " 
				+ "AND SUM(distribucion.cantidad) <=?"; 
		System.out.print("Enter the lower ammount: "); 
		int lower = ReadInt();
		System.out.print("Enter the higer ammount: "); 
		int higher = ReadInt();
		
		Integer[] parameters = new Integer[2];
		parameters[0] = lower;
		parameters[1] = higher;
		
		rs = DESA.executeSQL(sql, parameters);
		
		while(rs.next()) {
			String cifc = rs.getString(1);
			int stock = rs.getInt(2);
			
			System.out.println(cifc + " : " + stock); 
		}
	}

	/*
	 * 4. Realizar una aplicaci�n en Java para ejecutar la consulta 24 de la
	 * Pr�ctica 2 de forma que tanto la ciudad del concesionario como el color
	 * sean introducidos por el usuario. 24. Obtener los nombres de los clientes
	 * que no han comprado coches de un color introducido por el usuario en
	 * concesionarios de una ciudad introducida por el usuario.
	 */
	public static void exercise4() throws SQLException {
		String sql = "SELECT  clientes.nombre FROM clientes " 
				+ "WHERE dni NOT IN " 
				+ "(SELECT clientes.dni FROM clientes, ventas, concesionarios " 
				+ "WHERE clientes.dni=ventas.dni " 
				+ "AND ventas.cifc=concesionarios.cifc " 
				+ "AND concesionarios.ciudadc = ?" 
				+ "AND ventas.color = ?)"; 
		
		System.out.print("Enter the city of the dealer: "); 
		String city = ReadString();
		System.out.print("Enter the color of the car: "); 
		String color = ReadString();
		
		String[] parameters = new String[2];
		parameters[0] = city;
		parameters[1] = color;
		
		rs = DESA.executeSQL(sql, parameters);
		
		while(rs.next()) {
			String nombre = rs.getString(1);
			System.out.println(nombre);
		}

	}

	/*
	 * 5. Realizar una aplicaci�n en Java que haciendo uso de la instrucci�n SQL
	 * adecuada: 5.1. Introduzca datos en la tabla coches cuyos datos son
	 * introducidos por el usuario
	 */
	public static void exercise5_1() {
		String[] parameters = new String[3];
		System.out.print("Enter the code of the car: ");
		parameters[0] = ReadString();
		System.out.print("Enter the name of the car: ");
		parameters[1] = ReadString();
		System.out.println("Enter the model of the car: ");
		parameters[2] = ReadString();
		
		String sql = "INSERT INTO coches VALUES(?,?,?)";
		try {
			DESA.executeSQL(sql, parameters);
			System.out.println("REGISTER CREATED: " + parameters[0] + ", " + parameters[1] + ", " + parameters[2]);
		} catch (SQLException e) {
			System.err.println("ERROR: register " + parameters[0] + ", " + parameters[1] + ", " + parameters[2] + "couldn't be deleted.");
			e.printStackTrace();
		}
	}

	/*
	 * 5.2. Borre un determinado coche cuyo c�digo es introducido por el
	 * usuario.
	 */
	public static void exercise5_2() throws SQLException {
		System.out.print("Enter the id of the car you want to delete: "); 
		int carID = ReadInt();
		String sql = "DELETE FROM coches"
					+ "WHERE codcoche = ?";
		Integer[] parameters = new Integer[1];
		parameters[0] = carID;
		DESA.executeUpdate(sql, parameters);
		System.out.println("Car with id: " + carID + "has been deleted.");
	}

	/*
	 * 5.3. Actualice el nombre y el modelo para un determinado coche cuyo
	 * c�digo es introducido por el usuario.
	 */
	public static void exercise5_3() throws SQLException {
		System.out.print("Enter the id of the car you want to modify: "); 
		int carID = ReadInt();
		System.out.print("Enter the new name for the car: ");
		String name = ReadString();
		System.out.println("Enter the new brand for the car: ");
		String brand = ReadString();
		
		String sql = "UPDATE coches "
				+ "SET nombrech = ?, modelo = ? "
				+ "WHERE codcoche = ?";
		Object[] parameters = new Object[3];
		parameters[0] = name;
		parameters[1] = brand;
		parameters[2] = carID;
		
		DESA.executeUpdate(sql, parameters);
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
	
	private static void printAllCars() throws SQLException {
		System.out.println("----- ALL THE CARS ----");
		String sql = "SELECT * FROM coches ORDER BY codcoche";
		rs = DESA.executeSQL(sql);
		
		while(rs.next()) {
			System.out.println(" REGISTER: " + rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3));
		}
	}

}
