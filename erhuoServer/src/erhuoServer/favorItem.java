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
 * Servlet implementation class favorItem
 */
@WebServlet("/favorItem")
public class favorItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public favorItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userid");
		String itemId = request.getParameter("itemid");
		response.setContentType("text/html；charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        //操作数据库
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        int favorSuccess = 1;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://localhost:3306/erhuo","root","nihao123");
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select * from favor where user_id=? and item_id=?");
        	ps.setString(1, userId);
        	ps.setString(2,itemId);
        	rs = ps.executeQuery();
        	if(rs.next())favorSuccess = -1;
        	else{
        		ps.close();ps=null;
        		ps = (PreparedStatement) ct.prepareStatement(
            			"INSERT INTO favor(user_id,item_id)VALUES(?,?)");
            	ps.setInt(1, Integer.parseInt(userId));
            	ps.setInt(2,Integer.parseInt(itemId));
            	System.out.print(userId+" "+itemId);
            	ps.executeUpdate();
        	}
        	JSONObject jsonObj = new JSONObject()
        			.put("favorSuccess", favorSuccess);
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
