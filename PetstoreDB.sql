use sys;
drop database if exists SSMTestDB;
create database SSMTestDB;
use SSMTestDB;
#create table SSM_empTab(
	#empNumber varchar(20) primary key ,
#   empName varchar(20),
#    empSex varchar(5),
#    education varchar(5),
#    empMonthly varchar(20)
#);

create table SSM_Category(
	id int primary key auto_increment,
    `name` varchar(20)
);
create table `SSM_User`(
	id int primary key auto_increment,
    userName varchar(20),
    firstName varchar(20),
    lastName varchar(20),
    email varchar(50),
    `password` varchar(20),
    phone varchar(20),
    userStatus int
);
create table SSM_Tag(
	id int primary key auto_increment,
    `name` varchar(20)
);
create table SSM_Pet(
	id int primary key auto_increment,
    category int references Category(id),
    `name` varchar(20),
    photoUrls varchar(100),
    tags int references Tag(id),
    `status` Enum('available','pending','sold') 
);
create table `SSM_Order`(
	id int primary key auto_increment,
    petId int references Pet(id),
    quantity int,
    shipDate datetime,
    status Enum('placed','approved','delivered'),
    complete boolean default false
);
insert into SSM_Category values(0,'狗'),(0,'猫'),(0,'鼠');
insert into SSM_User values	(0,'张东明','东明','张','949861666@qq.com','12345','13977566666',1),
								(0,'东明','东明','张','949861777@qq.com','123456','13977577777',1),
                                (0,'Dz','东明','张','949861888@qq.com','1234567','13977588888',1);
insert into SSM_Tag values(0,'仓鼠'),(0,'布偶猫'),(0,'泰迪');
insert into SSM_Pet values	(0,2,'布偶','img/Ragdoll1.jpg',2,'pending'),
							(0,2,'小仓','img/hamster1.jpg',3,'pending'),
                            (0,2,'日破天','img/hamster1.jpg',1,'sold');
insert into SSM_Order values(0,3,1,now(),'delivered',true);

select `status`,count(*) from SSM_Order order by `status`

