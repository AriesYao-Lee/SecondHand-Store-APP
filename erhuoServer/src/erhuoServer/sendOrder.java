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
 * Servlet implementation class sendOrder
 */
@WebServlet("/sendOrder")
public class sendOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.print("request is "+request.toString());
		Order order = new Order();
		String orderTime = request.getParameter("ordertime");
		orderTime.replace('_', ' ');
		String orderPlace = request.getParameter("orderplace");
		Integer itemId = Integer.parseInt(request.getParameter("itemid"));
		Integer BuyerId = Integer.parseInt(request.getParameter("buyerid"));
		String buyerPhone = request.getParameter("buyerphone");
		order.setBuyer_phone(buyerPhone);order.setItemId(itemId);
		order.setBuyerId(BuyerId);order.setPlace(orderPlace);
		order.setTime(orderTime);
		
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
        			"INSERT INTO item_order(buyer_id,item_id,order_time,place,buyerphone,status)"
        			+"VALUE(?,?,?,?,?,1)");
        	ps.setString(1, BuyerId+"");
        	ps.setString(2,itemId+"");
    		ps.setString(3,orderTime);
    		ps.setString(4, orderPlace);
    		ps.setString(5, buyerPhone);
    		int exeResult,sendOrderSuccess;
        	exeResult = ps.executeUpdate();
        	System.out.print("exesult is "+exeResult);
        	if(exeResult==1){
        		sendOrderSuccess = 1;
        		ps.close();
        		ps = (PreparedStatement) ct.prepareStatement(
        				"update item set state_id=2 where item_id=?");
        		ps.setString(1, itemId+"");
        		ps.executeUpdate();
        	}
        	else
        		sendOrderSuccess = -1;
        	JSONObject jsonObj = new JSONObject()
        			.put("sendOrderSuccess", sendOrderSuccess);
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
