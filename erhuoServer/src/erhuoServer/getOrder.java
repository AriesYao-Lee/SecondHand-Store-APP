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

//import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class getOrder
 */
@WebServlet("/getOrder")
public class getOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getOrder() {
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
        String orderId = request.getParameter("orderId");
        String userId = request.getParameter("userId");
        //操作数据库
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try{
        	Order order = new Order();
        	UserInfo buyer=new UserInfo(),seller = new UserInfo();
        	Item item = new Item();
        	String time,place,buyerphone;
        	Integer isBuyer,item_id=0,buyer_id=0,status_order,sellerId = new Integer(0);
        	String buyer_name;
        	
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://localhost:3306/erhuo","root","nihao123");
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select * from item_order where order_id=? and buyer_id=?");
        	ps.setObject(1, orderId);
        	ps.setObject(2, userId);
        	rs = ps.executeQuery();
        	if(rs.next())isBuyer = 1;
        	else isBuyer=-1;
        	ps.close();rs.close();
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select buyer_id,item_id,order_time,place,buyerphone,status from item_order"
        			+ " where order_id=?");
        	ps.setObject(1, orderId);
        	rs = ps.executeQuery();
        	if(rs.next()){
        		buyer_id = (Integer)rs.getInt(1);
        		item_id = (Integer)rs.getInt(2);
        		time = (String)rs.getString(3);
        		place = (String)rs.getString(4);
        		buyerphone = (String)rs.getString(5);
        		status_order = (Integer)rs.getInt(6);
        		order.setBuyer_phone(buyerphone);
        		order.setPlace(place);
        		order.setTime(time);
        		order.setStatus(status_order);
        	}
        	if(buyer_id>0){
        		ps.close();rs.close();
        		ps = (PreparedStatement) ct.prepareStatement(
        				"select username from user_info"+
        				" where user_id=?");
        		ps.setInt(1, buyer_id);
        		rs = ps.executeQuery();
        		if(rs.next()){
        			buyer_name = rs.getString(1);
        			buyer.setUsername(buyer_name);
        		}
        	}
        	if(item_id>0){
        		String itemName, itemDesc;
            	Double itemPrice;
        		ps.close();rs.close();
        		ps = (PreparedStatement) ct.prepareStatement(
        				"select item_name,item_desc,price,owner_id from item"
        				+" where item_id=?");
        		ps.setInt(1, item_id);
        		rs = ps.executeQuery();
            	if(rs.next()){
            		itemName = (String)rs.getString(1);
            		itemDesc = (String)rs.getString(2);
            		itemPrice = (Double)rs.getDouble(3);
            		sellerId = (Integer)rs.getInt(4);
            		
            		item.setSellerId(sellerId);item.setItemDesc(itemDesc);
            		item.setItemName(itemName);item.setPrice(itemPrice);
            	}
        	}
        	if(sellerId>0){
        		ps.close();rs.close();
        		ps = (PreparedStatement) ct.prepareStatement(
        				"select username,phone from user_info"+
        				" where user_id=?");
        		ps.setInt(1, sellerId);
        		rs = ps.executeQuery();
        		if(rs.next()){
        			String seller_name = rs.getString(1);
        			String sellerPhone = rs.getString(2);
        			seller.setUsername(seller_name);
        			seller.setPhone(sellerPhone);
        		}
        	}
        	JSONObject result = new JSONObject(),temp_json;
        	result.put("isBuyer", isBuyer);
        	temp_json = new JSONObject(item);
        	result.put("item", temp_json);
        	temp_json = new JSONObject(buyer);
        	result.put("buyer", temp_json);
        	temp_json = new JSONObject(seller);
        	result.put("seller", temp_json);
        	temp_json = new JSONObject(order);
        	result.put("order", temp_json);
        	out.println(result.toString());
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
