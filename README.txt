* Setting up a database:
    - Use create_database_bookmarks.sql as a reference to create a database.
    - Remember the database's name.
    - Remember the table name(s).
    - Practice to inserting test entries in the script.
    - Having an ID is important better to have it than not.
    - Use ID as primary key: may not need it but do anyway.

'src' directory contains all the Java source code:
    - Keep different types of JavaBeans in their respective directory.
    - Files involving database is in 'database' directory:
        - ConnecionPool.java
            - Copy and paste this class to desire location and modify accordingly.
            - The most important part here is line 16.
            - "java:/comp/env/jdbc/homepage"
            - We should only be changing the 'jdbc/homepage' portion.
                - This portion is the name assigned in 'MyHomepage/web/META-INF/context.xml'
        - DBUtil.java
            - Copy and paste this class to desire location and modify accordingly.
        - ObjectDB.java:
            - The purpose of this class is to update, delete, insert, and get the object from the database.
            - Connection to the database must be working for this to work.
            - Use PreparedStatement to prevent SQL-Injection.
    - Create an Object class to hold database entries information.
        - Make sure to have getters and setters for all attributes.
    - Servlets
        - Create different servlets for specific jobs.
            - For example UserServlet to log user registration, and another log bookmarks into their respectibe databases.
            - Servlet doPost method will be called when a POST request is made.
                - A hidden form will be used to pass argument through POST. Taking a look at the argument from POST, we will process the command accordingly using switch statement.
                - Set session attribute so we can access it in the JSP files.
            - Forward the URL at the end of the method.

* 'web' directory contains all the JSP files:
    - META-INF/context.xml
        - Copy this file and modify accordingly.
            - Resource name is the name for InitialContext.lookup().
            - url is as follow "jdbc:mysql:[ADDRESS]:[PORT]/[DATABASENAME]"
            - username and password is the same as the ones used when creating the database.
    - WEB-INF/web.xml
        - This file is to map the url to specific servlets.
    - web/includes/
        - Holds header.html and footer.jsp
        - Automate header and footer creation for JSP.
    - web/js/
        - Holds the JavaScript files.
    - web/style/
        - Holds CSS files.
    - web/index.jsp
        - Default webpage.
        - We can call a method init() method if we have <load-on-startup>1</load-on-startup> where 1 is the first servlet on servlet list. Unfortunately I could not figure out how to set session attributes on startup.
    - web/bookmarks.jsp
        - Uses JSTL tag to loop through data list.

