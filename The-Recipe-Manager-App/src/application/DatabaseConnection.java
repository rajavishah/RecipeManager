package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	public Connection databaseLink;

	public Connection getConnection() {



		String databaseName = "Datahub_Dev";

		String databaseUser = "root";
		String databasePassword = "Pass3009@";



		String url = "jdbc:mysql://127.0.0.1:3306/" + databaseName;


		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
			if(databaseLink != null) {
				System.out.println("Connection successful");
			}else {
				System.out.println("Failed to connect to the db");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return databaseLink;
	}
}
