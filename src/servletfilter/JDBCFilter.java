package servletfilter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import connection.ConnectionUtils;
import utils.MyUtils;

@WebFilter(filterName="jdbcFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter{
	public JDBCFilter() {
    }
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	private boolean needJDBC(HttpServletRequest request) {
		System.out.println("JDBC filter ...");
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		String urlPattern = servletPath;
		if(pathInfo != null) {
			urlPattern = servletPath+"/*";
		}
		//liet ke nhung servlet
		Map<String, ? extends ServletRegistration> servletRegistration = request.getServletContext().getServletRegistrations();
		Collection<? extends ServletRegistration> values = servletRegistration.values();
		for(ServletRegistration sr:values) {
			Collection<String> mappings = sr.getMappings();
			if(mappings.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		if(this.needJDBC(req)) {
			System.out.println("open connection for: "+req.getServletPath());
			Connection conn=null;
			
			try {
				conn=ConnectionUtils.getConnection();
				conn.setAutoCommit(false);
				MyUtils.storeConnection(request, conn);
				chain.doFilter(request, response);
				conn.commit();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ConnectionUtils.rollbackQuietly(conn);
			} finally {
				ConnectionUtils.closeQuietly(conn);
			}
			
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
