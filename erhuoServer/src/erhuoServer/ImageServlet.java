package erhuoServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取输入流，是HTTP协议中的实体内容
	    ServletInputStream  sis=request.getInputStream();
	    int imgnum=0;
	    File file = new File(request.getSession().getServletContext().getRealPath
	    		 				("/"),"img_"+0+".jpg");
	     for (imgnum = 0;file.exists();imgnum++)
	     {
	      file  = new File(request.getSession().getServletContext().getRealPath
	    		  			("/"),"img_"+imgnum+".jpg");
	     }
	     //缓冲区
	     byte buffer[]=new byte[1024];
	     FileOutputStream fos=new FileOutputStream(file);
	     int len=sis.read(buffer, 0, 1024);
	     System.out.println(buffer.toString());
	     //把流里的信息循环读入到文件中
	     while( len!=-1 )
	     {
	         fos.write(buffer, 0, len);
	         len=sis.readLine(buffer, 0, 1024);
	      }
	      fos.close();
	      sis.close();
	      response.getWriter().append(imgnum+"");
		//doGet(request, response);
	}

}
