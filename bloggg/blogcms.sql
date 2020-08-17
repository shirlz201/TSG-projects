drop database if exists blogdb;
create database blogdb;

use blogdb;
CREATE TABLE `User`
(
 `UserId`    int primary key auto_increment ,
 `Name` varchar(50) NOT NULL ,
 `UserName`  varchar(30) NOT NULL ,
 `Password`  varchar(100) NOT NULL ,
 `Email`     varchar(50) NOT NULL ,
 `Enabled`   tinyint NOT NULL 
);
CREATE TABLE `Role`
(
 RoleId int primary key auto_increment ,
 Role varchar(45) NOT NULL
);
CREATE TABLE HashTag
(
 HashTagId int primary key auto_increment ,
 `Name`      varchar(30) NOT NULL
);

CREATE TABLE BlogPost
(
 BlogPostId    int primary key auto_increment ,
 BlogTitle     varchar(45) NOT NULL ,
 Content       mediumtext NOT NULL ,
 ImagePath 	   varchar(300),
 TimePosted    datetime,
 IsPublished   tinyint NOT NULL ,
 IsStatic      tinyint NOT NULL ,
 UserId        int NOT NULL ,
    FOREIGN KEY fk_BlogPost_User_userId(UserId) references `User`(UserId)
);

CREATE TABLE BlogPostHashTag(
	HashTagId INT,
    BlogPostId INT,
    PRIMARY KEY(HashTagId, BlogPostId),
    FOREIGN KEY fk_BlogPostHashTag_HashTag(HashTagId) references HashTag(HashTagId),
	FOREIGN KEY fk_BlogPostHashTag_BlogPost(BlogPostId) references BlogPost(BlogPostId)
);

CREATE TABLE User_Role(
UserId int not null,
RoleId int not null,
primary key(UserId,RoleId),
foreign key (UserId) references `user`(UserId),
foreign key (RoleId) references `role`(RoleId)
);

CREATE TABLE Comment (
 CommentId int primary key auto_increment,
 TimePosted datetime NOT NULL,
 Content varchar(200) NOT NULL,
 UserId  int NOT NULL,
 FOREIGN KEY fk_Comment_User_userId(UserId) references `User`(UserId),
 BlogPostId  int NOT NULL,
 FOREIGN KEY fk_Comment_BlogPost_BlogPostId(BlogPostId) references BlogPost(BlogPostId)
);
insert into User(UserId,name, username,password,email,enabled)
    values(1,"shirley","admin", "password","shirley123@gmail.com", true),
        (2,"john","user","password","john123@gmail.com",true),
	(3,"kim","contentmanager","password","kim123@gmail.com",true);

insert into Role(RoleId,Role)
    values(1,"ROLE_ADMIN"), (2,"ROLE_USER"),(3,"ROLE_CONTENTMANAGER");
    
insert into User_Role(userId,roleId)
    values(1,1),(1,2),(2,2),(3,3);

UPDATE user SET password = '$2a$10$S8nFUMB8YIEioeWyap24/ucX.dC6v9tXCbpHjJVQUkrXlrH1VLaAS' WHERE userId = 1;
UPDATE user SET password = '$2a$10$S8nFUMB8YIEioeWyap24/ucX.dC6v9tXCbpHjJVQUkrXlrH1VLaAS' WHERE userId = 2;
UPDATE user SET password = '$2a$10$S8nFUMB8YIEioeWyap24/ucX.dC6v9tXCbpHjJVQUkrXlrH1VLaAS' WHERE userId = 3;

insert into HashTag(name) values ("new");