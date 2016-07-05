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
 * Servlet implementation class getItem
 */
@WebServlet("/getItem")
public class getItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getItem() {
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
        String itemId = request.getParameter("itemId");
        String userId = request.getParameter("userId");
        Integer hasFavored=1;
        //操作数据库
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://localhost:3306/erhuo","root","nihao123");
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select item_name,item_desc,price,owner_id,state_id,img_src from item"
        			+ " where item_id=?");
        	ps.setObject(1, itemId);
        	rs = ps.executeQuery();
        	String itemName, itemDesc,imgSrc;
        	Integer sellerId,status;
        	Double itemPrice;
        	Item item = new Item();
        	if(rs.next()){
        		//System.out.print(rs.toString());
        		itemName = (String)rs.getString(1);
        		itemDesc = (String)rs.getString(2);
        		itemPrice = (Double)rs.getDouble(3);
        		sellerId = (Integer)rs.getInt(4);
        		status = (Integer)rs.getInt(5);
        		imgSrc = (String)rs.getString(6);
        		item.setSellerId(sellerId);item.setItemDesc(itemDesc);
        		item.setItemName(itemName);item.setPrice(itemPrice);
        		item.setStatus(status);item.setImgSrc(imgSrc);
        		//itr+=1;
        	}
        	JSONObject jsonObj = new JSONObject(item);
        	if(ps!=null){
        		ps.close();
        		if(rs!=null)rs.close();
        		ps = (PreparedStatement) ct.prepareStatement("select favor_id from favor"
        				+" where user_id=? and item_id=?");
        		ps.setObject(1, userId);
        		ps.setObject(2, itemId);
        		rs = ps.executeQuery();
        		if(rs.next()) hasFavored = 1;
        		else hasFavored=-1;
        	}
        	jsonObj.put("hasFavored", hasFavored);
        	//System.out.print(jsonObj.toString());
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
