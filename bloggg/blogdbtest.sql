drop database if exists blogdbtest;
create database blogdbtest;

use blogdbtest;
CREATE TABLE `User`
(
 `UserId`    int primary key auto_increment ,
 `Name` varchar(50) NOT NULL ,
 `UserName`  varchar(30) NOT NULL ,
 `Password`  varchar(50) NOT NULL ,
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
 TimePosted    datetime NOT NULL ,
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