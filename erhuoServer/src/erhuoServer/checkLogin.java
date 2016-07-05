package erhuoServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;

/**
 * Servlet implementation class checkLogin
 */
@WebServlet("/checkLogin")
public class checkLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpSession session = request.getSession(true);
		UserInfo user = new UserInfo();
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		user.setPassword(password);
		user.setUsername(username);
		response.setContentType("text/html；charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String key = "sfci50a7cvm3i";
		String secret = "3KcYchmx1v";

		SdkHttpResult result = null;
        //操作数据库
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://localhost:3306/erhuo","root","nihao123");
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select user_id from user_info where username=? and userpass=?");
        	ps.setObject(1, username);
        	ps.setObject(2, password);
        	
        	rs = ps.executeQuery();
        	Integer userId = new Integer(-1);
        	if(rs.next()){
        		userId = (Integer)rs.getInt(1);
        		

        		result = ApiHttpClient.getToken(key, secret, userId+"", username,
        				"http://aa.com/a.png", FormatType.json);
        	
        		//session.setAttribute("userId", userId);
        	}
        	else{
        		userId = -1;
        		//session.setAttribute("userId", userId);
        	}
        	JSONObject jsonObj = new JSONObject()
        			.put("userId", userId);
        	JSONObject tokenObj = new JSONObject(result.getResult());
        	jsonObj.put("token", tokenObj);
        	out.println(jsonObj.toString());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
            //关闭资源
            if(rs!=null)
            {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                rs=null;
            }
            if(ps!=null)
            {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ps=null;
            }
            if(ct!=null)
            {
                try {
                    ct.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ct=null;
            }
        }
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
