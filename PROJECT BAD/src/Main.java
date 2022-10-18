import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

	static String userNow = "";
	
	public Main() {
		
		MySQLConnection sql = new MySQLConnection();
		new FormLogin();
//		new FormRegister();
//		new PlayerMainForm();
//		new PlayerBuyGameForm();
//		new MySQLConnection();
//		new PlayerOwnedGamesForm();
//		new DeveloperManageGameForm();
//		new DeveloperManageGenreForm();

	}
	
	public static void main(String[] args) {
		new Main();
	}

	
}
