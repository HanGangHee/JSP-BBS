package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	private PreparedStatement  pstmt;
	private ResultSet rs;
	
	
	public UserDAO(){
		try {
			String dbURL = "jdbc:mysql://localhost:3307/BBS";
			String dbID ="root";
			String dpPassword = "132935gks";
			Class.forName("com.mysql.jdbc.Driver"); //마이sql에 접근할 수 있도록 해주는 매개체
			conn = DriverManager.getConnection(dbURL, dbID, dpPassword);
			
			}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int login(String userID, String userPassword) {
		
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1;//로그인 성공
				}
				else
					return 0; //비밀번호 불일치
			}
			return -1;//ID가 없을 떄
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	public int join(User user) {
		String sql = "INSERT INTO USER VALUES(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,user.getUserID());
			pstmt.setString(2,user.getUserPassword());
			pstmt.setString(3,user.getUserName());
			pstmt.setString(4,user.getUserGender());
			pstmt.setString(5,user.getUserEmail());
			return pstmt.executeUpdate(); //무조건 0이상
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터 베이스 오류
	}
}
			