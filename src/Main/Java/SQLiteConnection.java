package GigsterPractical;

import java.sql.*;
import java.util.Properties;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.sql.SQLException;

public class SQLiteConnection
{
	private Statement statement;
	private Connection connection;


	public SQLiteConnection() {
		try
		{
			Class.forName("org.sqlite.JDBC");
			Properties properties = new Properties();
			properties.setProperty("useSSL", "true");
			connection = DriverManager.getConnection("jdbc:sqlite:GigsterPracticalDB.db", properties);
			statement = connection.createStatement();
      		statement.setQueryTimeout(30);  // set timeout to 30 sec.
      	}
      	catch(Exception e)
      	{
      		System.err.println(e);
      	}
      }


      public ResultSet executeQuery(String sqlStatement) {
      	ResultSet rs = null;
      	try{
      		rs = statement.executeQuery(sqlStatement);
      		return rs;
      	}catch(Exception e) {
      		System.err.println(e);
      		return rs;
      	}
      }


      public Integer executeUpdate(String sqlStatement) {
      	try{
      		statement.executeUpdate(sqlStatement);
      		return 0;
      	}catch(SQLException e) {
      		System.err.println(e);
      		return e.getErrorCode();
      	}
      }

  }