

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MySQLServlet
 */
@WebServlet("/MySQLServlet")
public class MySQLServlet extends HttpServlet {
	
    public MySQLServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>データベーステスト</title>");
		out.println("</head>");
		out.println("<body>");
		
		//データを空にしておく
		Connection conn = null;
		String url = "jdbc:mysql://localhost/testdb";
		String user = "root";
		String password = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, password);
			
			//テーブルのデータを出力する
			Statement stmt = conn.createStatement();
			//指定したSQLを実行して結果を格納
			String sql = "SELECT * FROM test_table";
			//結果を返す
			ResultSet rs = stmt.executeQuery(sql);
		
			//次の行にカーソルが移動する間続ける
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				String userName = rs.getString("user_name");
				String userPassword = rs.getString("password");
				out.println("<p>");
				out.println("ユーザーID：" + userId + ",ユーザー名：" + userName + ",パスワード：" + userPassword);
				out.println("</p>");
			}
		
		rs.close();
		stmt.close();
		
		//指定のクラスが見つからない
		}catch (ClassNotFoundException e) {
			out.println("ClassNotFoundException:" + e.getMessage());
		//ドライバやDBサーバーのエラー
		}catch (SQLException e) {
			out.println("SQLException:" + e.getMessage());
		//ドライバやDBサーバーのエラー
		}catch (Exception e) {
			out.println("Exception:" + e.getMessage());
		//最後に問題が発生した場合もDBとの接続を切るために、必ず書くフレーズ
		}finally {
			try {
				if (conn !=null) {
					conn.close();
				}
			}catch (SQLException e) {
				out.println("SQLException:" + e.getMessage());
			}
		}
		
		out.println("</body>");
		out.println("</html>");
	}
}
