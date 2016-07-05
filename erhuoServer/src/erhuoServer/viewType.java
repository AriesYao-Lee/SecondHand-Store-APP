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
//import javax.servlet.http.HttpSession;

import org.json.JSONArray;
//import org.json.JSONObject;

/**
 * Servlet implementation class viewType
 */
@WebServlet("/viewKind")
public class viewType extends HttpServlet {
	private static final long serialVersionUID = 2L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewType() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        			"select * from item_type");
        	
        	rs = ps.executeQuery();
        	String typeName, typeDesc,typeId;
        	
        	List<itemType> kinds = new ArrayList<itemType>();
        	while(rs.next()){
        		//System.out.print(rs.toString());
        		itemType kind = new itemType();
        		typeId = (String)rs.getString(1);
        		typeName = (String)rs.getString(2);
        		typeDesc = (String)rs.getString(3);
        		kind.setId(typeId);kind.setKindDesc(typeDesc);
        		kind.setKindName(typeName);
        		kinds.add(kind);
        		//itr+=1;
        	}
        	JSONArray jsonArr = new JSONArray(kinds);
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
