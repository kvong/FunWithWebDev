<%@page import="java.util.GregorianCalendar, java.util.Calendar" %>
<%
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentYear = currentDate.get(Calendar.YEAR);
%>

<p class="foot">&copy; Copyright <%=currentYear %> Khanh Vong</p>
</body>
</html>