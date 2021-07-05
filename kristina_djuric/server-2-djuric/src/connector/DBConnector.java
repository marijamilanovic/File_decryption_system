package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.InteractionLog;

public class DBConnector {

	public static Connection connect() {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite3");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public static String decryptionKeyForFile(String fileName) {
		Connection con = DBConnector.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String decryptionKey = "";

		try {
			String sql = "SELECT KEY from AES_KEYS WHERE FILE = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, fileName);
			rs = ps.executeQuery();
			decryptionKey = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return decryptionKey;
	}

	public static void logZipInteraction(InteractionLog interactionLog) {
		Connection con = DBConnector.connect();
		PreparedStatement ps = null;
		try {
			String sql = "INSERT INTO INTERACTIONS(interaction_date, action_type)" + " VALUES (?, ? );";
			ps = con.prepareStatement(sql);
			ps.setString(1, interactionLog.getDate().toString());
			ps.setString(2, interactionLog.getActionType().toString());
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void logInteraction(InteractionLog interactionLog) {
		Connection con = DBConnector.connect();
		PreparedStatement ps = null;
		try {
			String sql = "INSERT INTO INTERACTIONS(interaction_date, file, action_type)" + " VALUES (?, ?, ?);";
			ps = con.prepareStatement(sql);
			ps.setString(1, interactionLog.getDate().toString());
			ps.setString(2, interactionLog.getFileName());
			ps.setString(3, interactionLog.getActionType().toString());
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
