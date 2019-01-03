<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.Date,java.text.*"%>
    
<%
  Date now = new Date();
 
  DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss SSS");
  
  
%>
 
<h4>Current Time:</h4>
<%=df.format(now)%>