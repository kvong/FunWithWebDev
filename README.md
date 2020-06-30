# FunWithWebDev

## Description:
 This is how to guide written from class notes about how to create a dynamic webpage using HTML, CSS, JavaScript, Java Servlets, JSP, JSTL, and MySQL. 
 ## Requirements:
 Java JDK 1.8.0 (Set this as environment  variable)
 Apache Tomcat-8.0.27 (Installed during the Netbeans Installation;
 MySQL database;
 [Netbeans IDE 8.2 - Java EE Bundle](https://netbeans.org/downloads/old/8.2/);
 [Typescripter Animation by eddiewentw](https://github.com/eddiewentw/TypeWriting.js);

### Basic Setup:
- Add different libraries necessary via Netbeans.
	- Go to the project panel in Netbeans and locate the `Libraries` folder.
		1. Right click on the folder and choose `Add Library...`
			- Now add `JSTL 1.2.1`, and `MySQL JDBC Driver`
		2. Right click on the folder and choose `Add JAR/Folder...`
			- Now add `apache-tomcat-8.0.27/lib/tomcat-jdbc.jar`.
- Setting up a database:
    - Use `create_database*.sql` as a reference to create a database.
    - Remember the database's name `databasename`.
    - Remember the table name(s) `databaseuser`.
    - Practice to inserting test entries in the script.
    - Having an ID is important better to have it than not.
    - Use ID as primary key: may not need it but do anyway.
    - Use attribute from other tables using foreign key.
- Setting up a connection to the database:
	- Use `web/META-INF/context.xml` as a reference to form connection.
		- Database information should be in a <Resource\> tag.
			- The variables we are interested in are:
				- `name` - Name we will use to connect to the database.
				- `url` - Address of the database. 
					- Format is `jdbc:mysql://localhost:3306/[databasename]`
				- `username` - The name of the user created when the database was created.
					- `databaseuser`
				- `password`- Password the user identified by in MySQL script.
- Setting up a connection from webpage to Servlets:
	- Use `web/WEB-INF/web.xml` to map POST submission to Servlet.
		- `<servlet>` Holds the servlet's name.
			- <servlet-name\> tag holds the name of the servlet .
			- <servlet-class\> tag hods the location of the servlet. (Java importing syntax: FolderName.Filename)
		- `<servlet-mapping>` Maps a URL to a servlet.
			- <servlet-name\> tag holds the name of the servlet we wish the URL to map to.
			- <url-pattern\> tag hold the newly added part to the URL after POST submission is made.
				- For example, `books.com/home` becomes `books.com/home/library`, the **url-pattern** will be `/library`.
		- <welcome-file-list\> tag holds a list of files to run when site is loaded. Default is *index.[ jsp| html]*
- Working with mySQL database in Java:
	- Connecting to a database:
		- `src/java/database/ConnectionPool.java` will lookup the database using the JDBC driver.
			- Copy this file over to use.
			- The only part we will be changing is the ending of the String used in `ic.lookup()`
				- `"java:/comp/env/[name from context.xml]"`
	- Working with databases:
		- When working with MySQL, we should be cautious against MySQL-Injections.
			- MySQL-Injection works by adding a SQL script to a textbox whose input will be used as argument in a MySQL command.
			- To guard our app against this, we will use PreparedStatements.
				- Copy `src/java/database/DBUtil.java` and place it in the same directory as `ConnectionPool.java`.
		- Using `src/java/database/BookmarkDB.java` as a reference, create your very own *objectDB.java* file.
			- This is where we will be executing the MySQL commands using Java.
- Start tomcat automatically on startup in `/etc/rc.local`.
	- Set `JAVA_HOME` to proper Java-JDK.
	- Run start script from `apache-tomcat-8.0.27/bin/startup.sh`

- Additional resources:
    - ![Enable Remote access to MariaDB/MySQL](https://webdock.io/en/docs/how-guides/how-enable-remote-access-your-mariadbmysql-database)
