<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>	
<%@ page import="java.util.Random,java.text.*"%>
<!DOCTYPE html>
<%!
  public int sum(int a, int b) {
      return a + b;
  }
%>
<html>
<head>
<meta charset="UTF-8">
<title>random test</title>
</head>
<body>
  <%
     Random random = new Random();
     // Trả về ngẫu nhiên (0, 1 hoặc 2).
     int randomInt = random.nextInt(3);
     if (randomInt == 0) {
  %>
  
  <h1>Random value =<%=randomInt%></h1>
  
  <%
      } else if (randomInt == 1) {
  %>
  
  <h2>Random value =<%=randomInt%></h2>
 
  <%
      } else {
  %>
   <h3>Random value =<%=randomInt%></h3>
  <%
      }
  %>
  <a href="<%= request.getRequestURI() %>">Try Again</a>
  <h1>
      1 + 2 =    <%=sum(1, 2)%>
  </h1>
</body>
</html>

<!-- 
+) Các method được khai báo bên trong <%!%>
+) jsp directive 
		(<%@ include ... %>) ho phép bạn nhúng một trang vào JSP tại thời điểm biên dịch JSP thành Servlet
		<%@ page ... %>  
		<%@ taglib ... %> để khai báo các thẻ JSP mở rộng 
						hoặc các thẻ tùy biến của bạn sẽ được sử dụng trong trang JSP này.
	<jsp:include ..> cho phép bạn nhúng một trang vào JSP tại thời điểm request
	
	
 -->

