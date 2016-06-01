package it.polito.tdp.dizionario.DAO;

import java.sql.*;

public class DBConnect {
static private final String jdbcURL= "jdbc:mysql://localhost/dizionario?user=root&password=";
	
	static private Connection connection=null;
	
	public static Connection getConnection(){
		
		try {
		if(connection==null){
			
			connection = DriverManager.getConnection(jdbcURL);
			}
				return connection;}
		
			 catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Cannot get connection " + jdbcURL, e );
			}
	}
	
}



