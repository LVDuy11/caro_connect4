package btl2;

import java.sql.*;

public class PointN {
	public static int ticTacToe_x=0;
	public static int ticTacToe_o=0;
	public static int caro_x=0;
	public static int caro_o=0;
	public static int caro_y=0;
	public static int cn4_r=0;
	public static int cn4_y=0;
	
	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/checkwin";
	static final String USER = "root";
	static final String PASS = "";
	
	public static void addPoint() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Statement stmt = null;
		
		try
		{
			//Register JDBC driver
			Class.forName(DRIVER_CLASS);
			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// Execute a query
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO point(TICTACTOE_X, TICTACTOE_O, CARO_X, CARO_O, CN4_RED, CN4_YELLOW) VALUES "
							+ "("+ticTacToe_x+"," +ticTacToe_o+ "," +caro_x+ "," +caro_o+ "," +cn4_r+ "," +cn4_y+")");
		}
		
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			// Close connection
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	public static void deletePoint() throws ClassNotFoundException, SQLException{
		Connection conn = null;
		Statement stmt = null;
		
		try
		{
			//Register JDBC driver
			Class.forName(DRIVER_CLASS);
			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// Execute a query
			stmt = conn.createStatement();
			
			stmt.executeUpdate("DELETE FROM `point` WHERE 1");
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			// Close connection
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
}
