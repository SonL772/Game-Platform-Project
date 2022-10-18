import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JSpinner;

public class MySQLConnection {
	
	public ResultSetMetaData rsm;
	Connection conn;
	Statement state;
	ResultSet rs;
	PreparedStatement prepState;
	
	public MySQLConnection(){
		connect();
	}
	
	private void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String host = "localhost:3306";
		String database = "stim";
		String username = "root";
		String password = "";
		String connectionString = 
				String.format("jdbc:mysql://%s/%s", host, database);

		try {
			conn = DriverManager.getConnection(connectionString, username, password);
			state = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
	
		try {
			rs = state.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void execUpdate(String query) {
		try {
			state.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void execRegister(String username, String password, String gender, String country, String role) {
		try {
			prepState = conn.prepareStatement("INSERT INTO user(username, password, gender, country, role) VALUES (?,?,?,?,?)");
			prepState.setString(1, username);
			prepState.setString(2, password);
			prepState.setString(3, gender);
			prepState.setString(4, country);
			prepState.setString(5, role);
			
			prepState.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execBuyTable(String userId, String gameId, int gameQuantity) {
		try {
			prepState = conn.prepareStatement("INSERT INTO transaction(userId, gameId, gameQuantity) VALUES (?,?,?)");
			prepState.setString(1, userId);
			prepState.setString(2, gameId);
			prepState.setInt(3, gameQuantity);
			
			prepState.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void execUpdateQuantity(int quantity, int userId, String gameId) {
		try {
			prepState = conn.prepareStatement("UPDATE transaction SET gameQuantity = ? WHERE userId = ? AND gameId = ? ");
			prepState.setInt(1, quantity);
			prepState.setInt(2, userId);
			prepState.setString(3, gameId);
			
			prepState.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execUpdateStock(int stock, String gameId) {
		try {
			prepState = conn.prepareStatement("UPDATE game SET quantity= ? WHERE gameId = ? ");
			prepState.setInt(1, stock);
			prepState.setString(2, gameId);
			
			prepState.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execDeveloperInsert(String gameId, String name, int price, String genreId, int quantity) {
		try {
			prepState = conn.prepareStatement("INSERT INTO game(gameId, name, price, genreId, quantity) VALUES (?,?,?,?,?)");
			prepState.setString(1, gameId);
			prepState.setString(2, name);
			prepState.setInt(3, price);
			prepState.setString(4, genreId);
			prepState.setInt(5, quantity);
			
			prepState.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void execDeveloperUpdate(String gameId, String name, int price, String genreId, int Quantity) {
		try {
			prepState = conn.prepareStatement("UPDATE game SET name = ?, price = ?, genreId = ?, quantity = ? WHERE gameId = ?");
			prepState.setString(1, name);
			prepState.setInt(2, price);
			prepState.setString(3, genreId);
			prepState.setInt(4, Quantity);
			prepState.setString(5, gameId);
			
			prepState.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void execDeveloperDelete(String gameId){
		try {
			prepState = conn.prepareStatement("DELETE FROM game WHERE gameId = ?");
			prepState.setString(1, gameId);
			
			prepState.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void execGenreUpdate(String genreId, String genreName) {
		try {
			prepState = conn.prepareStatement("UPDATE `genre` SET `genreName` = ? WHERE `genre`.`genreId` = ?;");
			prepState.setString(1, genreName);
			prepState.setString(2, genreId);
			
			prepState.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void execGenreDelete (String genreId) {
		try {
			prepState = conn.prepareStatement("DELETE FROM genre WHERE genreId = ?");
			prepState.setString(1, genreId);
			
			prepState.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void execGenreInsert(String genreId, String genreName) {
		try {
			prepState = conn.prepareStatement("INSERT INTO genre (genreId, genreName) VALUES (?,?)");
			prepState.setString(1, genreId);
			prepState.setString(2, genreName);
			
			prepState.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
}
