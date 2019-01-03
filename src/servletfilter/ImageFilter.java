package servletfilter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "*.png", "*.jpeg", "*.gif" }, initParams = {
        @WebInitParam(name = "blossom", value = "/images/blossom.jpeg") })
public class ImageFilter implements Filter{
	private String blossom;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("ImageFilter destroys!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req= (HttpServletRequest) request;
		String servletPath = req.getServletPath();		
		String realPath = request.getServletContext().getRealPath("");
		System.out.println("imageRealPath: "+realPath);
		File file=new File(realPath);
		if(file.exists()) {
			chain.doFilter(request, response);			
		} else {
			 {			
				System.out.println("da vao code vi "+servletPath.equals(this.blossom));
				HttpServletResponse resp = (HttpServletResponse)response;
				System.out.println("path error="+req.getContextPath()+this.blossom);
				resp.sendRedirect(req.getContextPath()+this.blossom);
			}
		}
		System.out.println("file ton tai ? "+file.exists());
		//loi file k kiem tra duoc file ton tai hay k
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		blossom = filterConfig.getInitParameter("blossom");
		System.out.println("init error file name"+ this.blossom);
	}
	
}
