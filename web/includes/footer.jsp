<%@page import="java.util.GregorianCalendar, java.util.Calendar" %>
<%
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentYear = currentDate.get(Calendar.YEAR);
%>

<p class="foot">&copy; Copyright <%=currentYear %> Khanh Vong</p>
</body>
    <script src="https://kit.fontawesome.com/a775dce2c6.js" crossorigin="anonymous"></script>
    <script src="./js/bookmarking.js"></script>
    <script src="./js/searchbar.js"></script>
</html>