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
 * Servlet implementation class viewItem
 */
@WebServlet("/viewItem")
public class viewItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html；charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String kindId = request.getParameter("kindId");
        //操作数据库
        Connection ct= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(
        			"jdbc:mysql://localhost:3306/erhuo","root","nihao123");
        	ps = (PreparedStatement) ct.prepareStatement(
        			"select item_id,item_name,item_desc,price from item"
        			+ " where type_id=?");
        	ps.setObject(1, kindId);
        	rs = ps.executeQuery();
        	String itemName, itemDesc;
        	Integer itemId;
        	Double itemPrice;
        	
        	List<Item> items = new ArrayList<Item>();
        	while(rs.next()){
        		//System.out.print(rs.toString());
        		Item item = new Item();
        		itemId = (Integer)rs.getInt(1);
        		itemName = (String)rs.getString(2);
        		itemDesc = (String)rs.getString(3);
        		itemPrice = (Double)rs.getDouble(4);
        		item.setId(itemId);item.setItemDesc(itemDesc);
        		item.setItemName(itemName);item.setPrice(itemPrice);
        		items.add(item);
        		//itr+=1;
        	}
        	JSONArray jsonArr = new JSONArray(items);
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
            closeSQL(rs);
            closeState(ps);
            
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
	private void closeSQL(ResultSet rs)
	{
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
	}
	private void closeState(PreparedStatement rs)
	{
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
