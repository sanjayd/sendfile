package net.sanjayd.sendfile;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class SendfileServlet extends HttpServlet {

	private static final long serialVersionUID = -1410583926504570524L;

	private static final Logger logger = Logger.getLogger(SendfileServlet.class);
	
	private String dataDir = System.getProperty("sendfile.data.directory");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("sendfile supported: " + req.getAttribute("org.apache.tomcat.sendfile.support"));
		
		String filename = dataDir + File.separator + req.getParameter("filename") + ".gz";
		File file = new File(filename);
		
		if (file.exists()) {
			Long fileSize = file.length();
			
			req.setAttribute("org.apache.tomcat.sendfile.filename", filename);
			req.setAttribute("org.apache.tomcat.sendfile.start", new Long(0l));
			req.setAttribute("org.apache.tomcat.sendfile.end", fileSize);
			
			resp.setContentLength(fileSize.intValue());
			resp.setHeader("Content-Encoding", "gzip");
			
		} else {
			resp.setStatus(404);
		}
	}
}
