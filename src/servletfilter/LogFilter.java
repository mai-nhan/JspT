package servletfilter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LogFilter implements Filter{
	private String logFile;
		
	public LogFilter() {
		super();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("LogFilter destroy!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		/*HttpServletRequest req = (HttpServletRequest)request;		 
        String servletPath = req.getServletPath();
        System.out.println("#INFO " + new Date() + " - ServletPath :" + servletPath //
                + ", URL =" + req.getRequestURL());
        // Cho phép request được đi tiếp. (Vượt qua Filter này).
        chain.doFilter(request, response);*/
		
		if (this.logFile != null) {
            // Ghi thông tin Log vào File.
            this.logToFile(this.logFile);
        }
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.logFile = arg0.getInitParameter("logFile");
		System.out.println("LogFilter init!");
	}
	private void logToFile(String filename) {
		System.out.println("write log to file "+filename);
	}
}
