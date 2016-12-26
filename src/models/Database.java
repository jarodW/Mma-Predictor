package models;

import java.sql.DriverManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

public class Database {
  static Connection connection = null;
  
  public static Connection createDatabase(){

	//System.out.println("-------- MySQL JDBC Connection Testing ------------");
	String script = "database.sql";
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("Where is your MySQL JDBC Driver?");
		e.printStackTrace();
		return null;
	}

	//System.out.println("MySQL JDBC Driver Registered!");

	try {
		connection = DriverManager
		.getConnection("jdbc:mysql://localhost:3306/","root", "password");

	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return null;
	}
	
    try {
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setLogWriter(null);
        scriptRunner.runScript(new BufferedReader(new FileReader(script)));
    } catch (Exception e) {
        System.err.println(e);
    }
    
	if (connection != null) {
		System.out.println("You made it, take control your database now!");
	} else {
		System.out.println("Failed to make connection!");
	}
	return connection;
  }
  
  public static Connection getDB(){
	  return connection;
  }
}

