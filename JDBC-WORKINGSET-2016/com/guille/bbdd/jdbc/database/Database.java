package com.guille.bbdd.jdbc.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database {

	protected Connection conn = null;
	protected Statement stat = null;
	protected ResultSet rs = null;

	protected Database() {}

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
	public abstract Connection connectDatabase(String protocol, String vendor, String driver, String server,
			String port, String databaseName, String user, String password) throws SQLException;

	/**
	 * Closes a given connection.
	 * 
	 * @param connection
	 *            to be closed.
	 */
	public void closeConnection() {
		try {
			this.conn.close();
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
	public ResultSet executeSQL(String sql) throws SQLException {
		this.stat = this.conn.createStatement();
		this.rs = this.stat.executeQuery(sql);
		return getResultSet();
	}
	
	/**
	 * Executes a sql prepared sentence in a given connection.
	 * 
	 * @param sql to be executed
	 * @param parameters for the sentence.
	 * @return the result of the sentence execution.
	 * @throws SQLException if any error.
	 */
	public ResultSet executeSQL(String sql, Object[] parameters) throws SQLException {
		PreparedStatement psQuery = this.conn.prepareStatement(sql);
		for(int i = 0; i < parameters.length; i++) {
			psQuery.setObject(i+1, parameters[i]);
		}
		this.rs = psQuery.executeQuery();
		return getResultSet();
	}

	public void executeUpdate(String sql, Object[] parameters) throws SQLException {
		PreparedStatement psQuery = this.conn.prepareStatement(sql);
		for(int i = 0; i < parameters.length; i++) {
			psQuery.setObject(i+1, parameters[i]);
		}
		psQuery.executeUpdate();
	}
	
	/**
	 * Returns the result set object.
	 * 
	 * @return the resultset object.
	 */
	public ResultSet getResultSet() {
		return this.rs;
	}
}
