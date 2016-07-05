package erhuoServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserInfo user = new UserInfo();
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		user.setPassword(password);
		user.setUsername(username);
		user.setPhone(phone);
		user.setEmail(email);
		response.setContentType("text/html；charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        //操作数据库
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://localhost:3306/erhuo","root","nihao123");
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select username from user_info where username=?");
        	ps.setObject(1, username);
        	
        	rs = ps.executeQuery();
        	Integer regSuccess = -1;//-1 indicates name repeated, 1 means success
        	if(rs.next()){
        		regSuccess = -1;
        		//session.setAttribute("userId", userId);
        	}
        	else{
        		regSuccess = 1;
        		//session.setAttribute("userId", userId);
        	}
        	
        	if(regSuccess>0){
        		if(ps!=null) ps.close();
        		//String query = ;
        		//query += (" VALUES("+username+","+password+","+email+")");
        		ps = (PreparedStatement) ct.prepareStatement(
        				"INSERT INTO user_info(username,userpass,email,phone) VALUES(?,?,?,?)");
        		//ps = (PreparedStatement) ct.prepareStatement(query);
        		ps.setString(1, username);
        		ps.setString(2, password);
        		ps.setString(3, email);
        		ps.setString(4, phone);
        		ps.executeUpdate();
        	}
        	JSONObject jsonObj = new JSONObject()
        			.put("regSuccess", regSuccess);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
