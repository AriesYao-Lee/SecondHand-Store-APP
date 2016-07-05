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
 * Servlet implementation class deliver
 */
@WebServlet("/deliver")
public class deliver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deliver() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Item item = new Item();
		String itemName = request.getParameter("itemname");
		String itemDesc = request.getParameter("itemdesc");
		Double price = Double.parseDouble(request.getParameter("itemprice"));
		Integer typeId = Integer.parseInt(request.getParameter("typeId"));
		Integer sellerId = Integer.parseInt(request.getParameter("sellerid"));
		Integer status = Integer.parseInt(request.getParameter("status"));
		String imgSrc = request.getParameter("imgSrc");
		item.setItemName(itemName);item.setItemDesc(itemDesc);
		item.setPrice(price);item.setSellerId(sellerId);
		item.setStatus(status);item.setTypeId(typeId);
		item.setImgSrc(imgSrc);
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
        			//"INSERT INTO auction_user(username,userpass,email) VALUES(?,?,?)",
        			"INSERT INTO item(item_name,item_desc,type_id,"
        			+ "price,add_time,owner_id,state_id,img_src) "
        			+"VALUES (?, ?, ?, ?, CURDATE(), ?, ?,?)");
        	ps.setString(1, itemName);
        	ps.setString(2,itemDesc);
    		ps.setInt(3,typeId.intValue());
    		ps.setDouble(4, price);
    		ps.setInt(5, sellerId.intValue());
    		ps.setInt(6, status.intValue());
    		ps.setString(7, imgSrc);
    		
        	int addItemSuccess = ps.executeUpdate();
        	JSONObject jsonObj = new JSONObject()
        			.put("addItemSuccess", addItemSuccess);
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
