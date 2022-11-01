drop database if exists cafedb;
create database cafedb;

use cafedb;

create table coffee(
	ccode int(5) primary key not null auto_increment,
    cName varchar(10) not null,
    cPrice int(10) not null
);
create table beverage(
	bcode int(5) primary key not null auto_increment,
    bName varchar(10) not null,
    bPrice int(10) not null
);
create table dessert(
	dcode int(5) primary key not null auto_increment,
    dName varchar(10) not null,
    dPrice int(10) not null
);
-- select * from orderCart;
create table orderCart(
	ocode int(5) primary key not null auto_increment,
    oamount int(5) not null,
    oprice int(10) not null,
    odate datetime
);
/* 컬럼 추가가 안되네용 ㅠ
    customer_id varchar(20) not null,
    ccode int(5),
    bcode int(5),
    dcode int(5),
    foreign key (customer_id) references customers(customer_id),
    foreign key (ccode) references coffee(ccode),
    foreign key (bcode) references beverage(bcode),
    foreign key (dcode) references dessert(dcode)
*/
create table customers(
	customer_id varchar(20) not null primary key,
    customer_name varchar(10) not null,
    customer_pwd varchar(20) not null,
    customer_phone varchar(20) not null,
    customer_coupon int(10) default 0,
    customer_couponcheck int(10) default 0
);
create table admin(
	admin_id varchar(20) not null primary key,
    admin_pwd varchar(20) not null,
    admin_coupon int(10) default 3000
);
insert into customers(customer_id, customer_name, customer_pwd, customer_phone) value('asd','아무개','pw', 010-1234-5678); -- 테스트용 주석 처리 하세요
-- insert into admin(admin_id, admin_pwd, customer_pwd, customer_phone) value('asd','이동건','asd', 010-1234-5678);
-- update customers set customer_coupon = 0, customer_couponcheck = 9 where customer_id ='asd';
-- delete from customers where customer_id = 'asd';
-- drop table if exists customers;
-- select * from backupUpdateCustomers;
create table backupUpdateCustomers(
	bcustomer_id varchar(20) not null primary key,
    bcustomer_name varchar(10) not null,
    bcustomer_pwd varchar(20) not null,
    bcustomer_phone varchar(20) not null,
    bcustomer_coupon int(10) default 0,
    bcustomer_couponcheck int(10) default 0
);
/*drop table if exists backupDeleteCustomers;*/
-- select * from backupDeleteCustomers;
create table backupDeleteCustomers(
	bdcustomer_id varchar(20) not null primary key,
    bdcustomer_name varchar(10) not null,
    bdcustomer_pwd varchar(20) not null,
    bdcustomer_phone varchar(20) not null,
    bdcustomer_coupon int(10) default 0,
    bdcustomer_couponcheck int(10) default 0,
    bdcustomer_date date
);
drop trigger if exists backupUpdateCustomers;
delimiter $$
create trigger backupUpdateCustomers
	after update
    on customers
    for each row
begin
	insert into backupUpdateCustomers values(old.customer_id, old.customer_name, old.customer_pwd, old.customer_phone, old.customer_coupon, old.customer_couponcheck);
end $$
delimiter ;
-- select * from backupUpdateCustomers;
drop trigger if exists backupDeleteCustomers;
delimiter $$
create trigger backupDeleteCustomers
	after delete
    on customers
    for each row
begin
	insert into backupDeleteCustomers values(old.customer_id, old.customer_name, old.customer_pwd, old.customer_phone, old.customer_coupon, old.customer_couponcheck, curdate());
end $$
delimiter ;