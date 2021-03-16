create database present_cloud;
use present_cloud;
create table user (
id int auto_increment not null primary key,
username varchar(32) not null,
password varchar(64) not null,
avatar varchar(64) not null,
phone varchar(16),
enable boolean not null
);

create table role(
id int auto_increment not null primary key,
name varchar(16) not null 
);

create table user_role(
user_id int not null references user(id),
role_id int not null references role(id),
primary key(user_id,role_id)
)
