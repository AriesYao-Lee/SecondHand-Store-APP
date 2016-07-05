package erhuoServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

/**
 * Servlet implementation class viewOrder
 */
@WebServlet("/viewOrder")
public class viewOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html；charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String userId = request.getParameter("userId");
        //操作数据库
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://localhost:3306/erhuo","root","nihao123");
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select a.item_name,a.owner_id,b.order_time,b.order_id,b.status,b.buyer_id from item a JOIN item_order b on"+ 
        			" a.item_id=b.item_id where a.owner_id=? or b.buyer_id=?");
        	ps.setObject(1, userId);
        	ps.setObject(2, userId);
        	rs = ps.executeQuery();
        	String itemName, orderTime;
        	Integer orderId,ownerId,status,buyerId;
        	
        	List<Order> orders = new ArrayList<Order>();
        	while(rs.next()){
        		//System.out.print(rs.toString());
        		Order order = new Order();
        		itemName = (String)rs.getString(1);
        		ownerId = (Integer)rs.getInt(2);
        		orderTime = (String)rs.getString(3);
        		orderId = (Integer)rs.getInt(4);
        		status = (Integer)rs.getInt(5);
        		buyerId = (Integer)rs.getInt(6);
        		order.setId(orderId);order.setTime(orderTime);
        		order.setStatus(status);order.setItemName(itemName);
        		order.setOwnerId(ownerId);order.setBuyerId(buyerId);
        		orders.add(order);
        		//itr+=1;
        	}
        	JSONArray jsonArr = new JSONArray(orders);
        	System.out.print(jsonArr.toString());
        	out.println(jsonArr.toString());
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
