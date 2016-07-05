package erhuoServer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Servlet;

import com.jspsmart.upload.SmartUpload;  

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/uploadImage")
public class UploadServlet extends HttpServlet implements Servlet {  
	private static final long serialVersionUID = 1L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
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
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
  
        response.setContentType("text/html,charset=UTF-8");  
  
        SmartUpload smartUpload = new SmartUpload();  
  
        try {  
            smartUpload.initialize(this.getServletConfig(), request, response);  
            smartUpload.upload();  
            com.jspsmart.upload.File smartFile = smartUpload.getFiles().getFile(0);  
            if (!smartFile.isMissing()) {  
                String saveFileName = "/data/" + smartFile.getFileName();  
                smartFile.saveAs(saveFileName, smartUpload.SAVE_PHYSICAL);  
                response.getWriter().print("ok:" + saveFileName + ", msg:" + smartUpload.getRequest().getParameter("msg"));  
            } else {  
                response.getWriter().print("missing...");  
            }  
        } catch (Exception e) {  
            response.getWriter().print(e);  
        }  
  
    }  
  
}  