/***********************************************************
* Create the database named homepage, and a bookmark table
************************************************************/

-- Create a new database, delete if there's an existing database already
DROP DATABASE IF EXISTS homepage;

CREATE DATABASE homepage;

USE homepage;

-- Create a table to store web bookmarks
CREATE TABLE bookmark (
    Type VARCHAR(50),
    Name VARCHAR(50),
    Url VARCHAR(255),
    Icon VARCHAR(50),
    Logo VARCHAR(50),
    Display BOOLEAN
);

-- Insert Default bookmark entries
INSERT INTO bookmark
    (Type, Name, Url, Icon, Logo, Display)
VALUES
('Update', '56', '', 'fas fa-bookmark', '', FALSE),
('Media', 'Youtube', 'https://www.youtube.com/', 'fab fa-youtube', 'youtube_logo.png', TRUE),
('Coding', 'GitHub', 'https://github.com', 'fab fa-github', 'github_logo.png', TRUE),
('Shopping', 'Amazon', 'https://www.amazon.com', 'fab fa-amazon', 'amazon_logo.png', TRUE),
('Google', 'Google Drive', 'https://drive.google.com/drive/my-drive', 'fab fa-google-drive', 'google_logo.png', TRUE),
('Coding', 'GitHub Guide', 'https://akrabat.com/the-beginners-guide-to-contributing-to-a-github-project/', 'fas fa-bookmark', '', TRUE),
('Coding', 'StackEdit', 'https://stackedit.io/app#', 'fas fa-bookmark', '', TRUE),
('Media', 'Read Manga', 'https://manganelo.com/bookmark', 'fas fa-bookmark', '', TRUE),
('Google', 'Gmail', 'https://mail.google.com/mail/u/0/#inbox', 'fas fa-envelope', '', TRUE),
('Google', 'Google Colab', 'https://colab.research.google.com/notebooks/intro.ipynb#recent=true', 'fas fa-infinity', '', TRUE),
('Shopping', 'Ebay', 'https://www.ebay.com/', 'fas fa-shopping-cart', '', TRUE),
('School', 'Canvas', 'https://umsl.instructure.com/', 'fas fa-chalkboard-teacher', '', TRUE),
('School', 'UMSL Email', 'https://login.microsoftonline.com', 'fas fa-mail-bulk', '', TRUE),
('Coding', 'CodeWar', 'https://www.codewars.com/dashboard', 'fas fa-bookmark', '', TRUE),
('School', 'MyView', 'https://myview.umsl.edu/psp/csprds/?cmd=login&languageCd=ENG&', 'fas fa-bookmark', '', TRUE),
('Misc.', 'Font Awesome', 'https://fontawesome.com/', 'fab fa-font-awesome', '', TRUE),
('Misc.', 'Router Login', 'http://192.168.1.1', 'fas fa-bookmark', '', TRUE),
('Misc.', 'PDF Download Sites', 'https://www.easepdf.com/topics/sites-like-library-genesis-pdf-ebooks.html', 'fas fa-bookmark', '', TRUE),
('Coding', 'LeetCode', 'https://leetcode.com/', 'fas fa-bookmark', '', TRUE),
('Media', 'Unixporn', 'https://www.reddit.com/r/unixporn/', 'fab fa-reddit-alien', '', TRUE),
('Media', 'Facebook', 'https://www.facebook.com/', 'fab fa-facebook-f', '', TRUE),
('Media', 'Instagram', 'https://www.instagram.com/', 'fab fa-instagram', '', TRUE),
('Work', 'WP Codex', 'https://codex.wordpress.org/', 'fas fa-bookmark', '', TRUE),
('Work', 'WP Developer', 'https://developer.wordpress.org/', 'fas fa-bookmark', '', TRUE),
('Work', 'WP CLI', 'https://wp-cli.org/', 'fas fa-bookmark', '', TRUE);


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
