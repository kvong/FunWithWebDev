/***********************************************************
* Create the database named homepage, and a bookmark table
************************************************************/

-- Create a new database, delete if there's an existing database already
DROP DATABASE IF EXISTS homepage;

CREATE DATABASE homepage;

USE homepage;

-- Create a table to store our data
CREATE TABLE bookmark (
    BookmarkID INT NOT NULL AUTO_INCREMENT,
    Category VARCHAR(50),
    Name VARCHAR(50),
    Url VARCHAR(255),
    Icon VARCHAR(50),
    Logo VARCHAR(50),

    PRIMARY KEY(BookmarkID)
);


-- Insert Default bookmark entries

INSERT INTO bookmark
    (Category, Name, Url, Icon, Logo)
VALUES
    ('Media', 'Youtube', 'https://www.youtube.com/', 'fab fa-youtube', 'youtube_logo.png'),
    ('Coding', 'GitHub', 'https://github.com', 'fab fa-github', 'github_logo.png'),
    ('Shopping', 'Amazon', 'https://www.amazon.com', 'fab fa-amazon', 'amazon_logo.png'),
    ('Google', 'Google Drive', 'https://drive.google.com/drive/my-drive', 'fab fa-google-drive', 'google_logo.png'),
    ('Coding', 'GitHub Guide', 'https://akrabat.com/the-beginners-guide-to-contributing-to-a-github-project/', 'fas fa-bookmark', ''),
    ('Coding', 'StackEdit', 'https://stackedit.io/app#', 'fas fa-bookmark', ''),
    ('Media', 'Read Manga', 'https://manganelo.com/bookmark', 'fas fa-bookmark', ''),
    ('Google', 'Gmail', 'https://mail.google.com/mail/u/0/#inbox', 'fas fa-envelope', ''),
    ('Google', 'Google Colab', 'https://colab.research.google.com/notebooks/intro.ipynb#recent=true', 'fas fa-infinity', ''),
    ('Shopping', 'Ebay', 'https://www.ebay.com/', 'fas fa-shopping-cart', ''),
    ('School', 'Canvas', 'https://umsl.instructure.com/', 'fas fa-chalkboard-teacher', ''),
    ('School', 'Deep Learning', 'https://github.com/badriadhikari/DL-2020spring', 'fas fa-brain', ''),
    ('School', 'UMSL Email', 'https://login.microsoftonline.com', 'fas fa-mail-bulk', ''),
    ('School', 'Pearson', 'https://pi.pearsoned.com/v1/piapi/piui/signin?client_id=NimtqV7BcT00BM9xOXCQUFJYwF3RzEIk&login_success_url=https:%2F%2Fconsole.pearson.com%2Fconsole%2Fhome', 'fas fa-bookmark', ''),
    ('Coding', 'CodeWar', 'https://www.codewars.com/dashboard', 'fas fa-bookmark', ''),
    ('School', 'MyView', 'https://myview.umsl.edu/psp/csprds/?cmd=login&languageCd=ENG&', 'fas fa-bookmark', '');

-- Create a user to have specific access to library
DELIMITER //
CREATE PROCEDURE drop_user_if_exists()
BEGIN
    DECLARE userCount BIGINT DEFAULT 0 ;

    SELECT COUNT(*) INTO userCount FROM mysql.user
    WHERE User = 'webmaster' and  Host = 'localhost';

    IF userCount > 0 THEN
        DROP USER webmaster@localhost;
    END IF;
END ; //
DELIMITER ;

CALL drop_user_if_exists() ;

-- Create user with password;
-- Use this to connect to the database
CREATE USER webmaster@localhost IDENTIFIED BY 'gochujang';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP
ON homepage.*
TO webmaster@localhost;

USE homepage;
