package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import model.Lesson;
import model.MailData;
import model.ReserveData;



public class LessonDAO {
	final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	final String JDBC_URL = "jdbc:mysql://localhost/pilates";
	final String DB_USER = "root";
	final String DB_PASS = "root";
	Connection conn = null;


	public boolean check(Calendar day) {

		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			// entrantテーブルにデータを挿入
			String sql = "SELECT*FROM lesson where date=?";
			PreparedStatement pStmt1 = conn.prepareStatement(sql);
			pStmt1.setString(1, day);




			ResultSet rs = pStmt1.executeQuery();
			if (rs.next()) {// rs.next()⇒1行目があればfalseを返すメソッド
				return false;
			} else {// rs.next()⇒1行目がなければtrueを返すメソッド
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
	}

	public boolean insert(Lesson lesson, ReserveData rData) {
		Connection conn = null;
		int firstSQLResult = 0;// 帰ってきた件数チェック(0件なら入ってない)
		boolean result = false;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			// entrantテーブルにデータを挿入
			String sql = "insert into reservation (date,time,family_name,first_name,mail,tel,memo)"
					+ "value(?,?,?,?,?,?,?)";
			PreparedStatement pStmt1 = conn.prepareStatement(sql);
			pStmt1.setString(1, lesson.getDate());
			pStmt1.setString(2, lesson.getTime());
			pStmt1.setString(3, rData.getFamily_name());
			pStmt1.setString(4, rData.getFirst_name());
			pStmt1.setString(5, rData.getMail());
			pStmt1.setString(6, rData.getTel());
			pStmt1.setString(7, rData.getMemo());

			firstSQLResult = pStmt1.executeUpdate();// 成功時は必ず1、失敗時は0

			// 1回目がfalse、2回目がtrueのときも通っちゃうからダメ
			if (firstSQLResult > 0) {
				result = true;
			}
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
	}

	public MailData findNum(Lesson lesson, ReserveData rData) {
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			MailData mailData = new MailData();

			//
			String sql = "SELECT*FROM RESERVATION WHERE DATE=? AND TIME=? AND MAIL=? ORDER BY NUMBER DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, lesson.getDate());
			pStmt.setString(2, lesson.getTime());
			pStmt.setString(3, rData.getMail());

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {// rs.next()⇒1行目があればnullを返すメソッド
				String number = rs.getString("number");
				String date = rs.getString("date");
				String time = rs.getString("time");
				String family_name = rs.getString("family_name");
				String first_name = rs.getString("first_name");
				String mail = rs.getString("mail");

				mailData.setNumber(number);
				mailData.setDate(date);
				mailData.setTime(time);
				mailData.setFamily_name(family_name);
				mailData.setFirst_name(first_name);
				mailData.setMail(mail);
				return mailData;

			} else {// rs.next()⇒1行目がなければnullを返すメソッド
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}




		public String[] findDay(String day){
			Connection conn =null;
			int I=0;
			String[] FDL= new String[]{"無し","無し"};
			try{Class.forName(DRIVER_NAME);
			conn=DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);

			String sql="SELECT l_date,l_time FROM lesson WHERE l_date=?";
			PreparedStatement pStmt=conn.prepareStatement(sql);
			pStmt.setString(1, day);
			ResultSet rs=pStmt.executeQuery();
			while (rs.next()) {

	     		String l_time=rs.getString("l_time");
	     		FDL[I]=l_time;
	     		I=I+1;
			}
			return FDL;


			}catch(SQLException e){
				e.printStackTrace();
				return null;
			}catch(ClassNotFoundException e){
				e.printStackTrace();
				return null;
			}finally{
				if(conn !=null){
					try{
						conn.close();
					}catch(SQLException e){
						e.printStackTrace();
						return null;
					}
				}
			}
		}

		public boolean create(Lesson lesson){
			Connection conn=null;
			try{
				conn=DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);
				String sql="INSERT INTO MUTTER(l_day,l_time) VALUES(?,?)";
				PreparedStatement pStmt=conn.prepareStatement(sql);
				pStmt.setString(1,lesson.getDate());
				pStmt.setString(2,lesson.getTime());

				int result=pStmt.executeUpdate();
				if(result !=1){
					return false;
				}
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}finally{
				if(conn !=null){
					try{

					conn.close();
					}catch(SQLException e){
						e.printStackTrace();

					}

				}
			}return true;
		}
		}