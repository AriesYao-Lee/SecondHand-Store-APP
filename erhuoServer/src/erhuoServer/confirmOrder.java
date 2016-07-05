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
 * Servlet implementation class confirmOrder
 */
@WebServlet("/confirmOrder")
public class confirmOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public confirmOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Integer isBuyer = Integer.parseInt(request.getParameter("isBuyer"));
		int status=-1;
		response.setContentType("text/html；charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        //操作数据库
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        int confirmSuccess = -1;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://localhost:3306/erhuo","root","nihao123");
        	if(isBuyer>0){//是买家
        		ps = (PreparedStatement) ct.prepareStatement(
            			"select status from item_order where order_id=?");
        		ps.setInt(1, orderId);
        		rs = ps.executeQuery();
        		if(rs.next())status = rs.getInt(1);
        		if(status==1){
        			ps.close();
        			ps = (PreparedStatement) ct.prepareStatement(
        					"update item_order set status=2 where order_id=?");
        			ps.setInt(1, orderId);
        			confirmSuccess = ps.executeUpdate();//1为成功
        		}
        		if(status==2){
        			confirmSuccess=2;//已确认
        		}
        	}
        	else{
        		ps = (PreparedStatement) ct.prepareStatement(
            			"select status from item_order where order_id=?");
        		ps.setInt(1, orderId);
        		rs = ps.executeQuery();
        		if(rs.next())status = rs.getInt(1);
        		if(status==2){
        			ps.close();
        			ps = (PreparedStatement) ct.prepareStatement(
        					"update item_order set status=3 where order_id=?");
        			ps.setInt(1, orderId);
        			confirmSuccess = ps.executeUpdate();//1为成功
        		}
        		if(status==3){
        			confirmSuccess=2;//已确认
        		}
        	}
        	
        	JSONObject jsonObj = new JSONObject()
        			.put("confirmSuccess", confirmSuccess);
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
