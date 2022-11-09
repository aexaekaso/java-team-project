drop database if exists cafedb;
create database cafedb;
use cafedb;
create table coffee(
	ccode int primary key not null auto_increment,
    cName varchar(30) not null,
    cPrice int not null
);

create table beverage(
	bcode int primary key not null auto_increment,
    bName varchar(30) not null,
    bPrice int not null
);
create table dessert(
	dcode int primary key not null auto_increment,
    dName varchar(30) not null,
    dPrice int not null
);
create table customers(
	customer_id varchar(20) not null primary key,
    customer_name varchar(10) not null,
    customer_pwd varchar(20) not null,
    customer_phone varchar(20) not null,
    customer_coupon int default 0,
    customer_couponcheck int default 0
);
create table orderCart(
	ocode int primary key not null auto_increment,
    customer_id varchar(20) not null,
    ccode int,
    bcode int,
    dcode int,
    oname varchar(30) not null,
    oamount int not null,
    oprice int not null,
    odate datetime
);

insert into customers(customer_id, customer_name, customer_pwd, customer_phone, customer_coupon) value('admin','관리자','bdfbdf', '010-1234-5678', '3000'); -- 기본 쿠폰 차감가 3000원 
insert into customers(customer_id, customer_name, customer_pwd, customer_phone) value('a','아무개','b', '010-1234-5678'); -- 아이디 a 비번 1 사용하시고 주석처리 하세요.
update customers set customer_coupon = 10, customer_couponcheck = 8 where customer_id ='a'; -- 쿠폰 테스트 세팅
-- select * from customers;
-- select * from orderCart;

create table backupUpdateCustomers(
	bcustomer_id varchar(20) not null,
    bcustomer_name varchar(10) not null,
    bcustomer_pwd varchar(20) not null,
    bcustomer_phone varchar(20) not null,
    bcustomer_coupon int,
    bcustomer_couponcheck int,
    bcustomer_date datetime
);
#select * from backupUpdateCustomers;
create table backupDeleteCustomers(
	bdcustomer_id varchar(20) not null,
    bdcustomer_name varchar(10) not null,
    bdcustomer_pwd varchar(20) not null,
    bdcustomer_phone varchar(20) not null,
    bdcustomer_coupon int,
    bdcustomer_couponcheck int,
    bdcustomer_date datetime
);
#select * from backupDeleteCustomers;
#drop trigger if exists backupUpdateCustomers;
delimiter $$
create trigger backupUpdateCustomers
	after update
    on customers
    for each row
begin
	insert into backupUpdateCustomers values(old.customer_id, old.customer_name, old.customer_pwd, old.customer_phone, old.customer_coupon, old.customer_couponcheck, current_timestamp());
end $$
delimiter ;
#select * from backupUpdateCustomers;
#drop trigger if exists backupDeleteCustomers;
delimiter $$
create trigger backupDeleteCustomers
	after delete
    on customers
    for each row
begin
	insert into backupDeleteCustomers values(old.customer_id, old.customer_name, old.customer_pwd, old.customer_phone, old.customer_coupon, old.customer_couponcheck, current_timestamp());
end $$
delimiter ;

INSERT INTO coffee(ccode, cname, cprice) VALUES(101, '아메리카노', 4500);
INSERT INTO coffee(ccode, cname, cprice) VALUES(102, '카푸치노', 5000);
INSERT INTO coffee(ccode, cname, cprice) VALUES(103, '돌체 라떼', 5900);
INSERT INTO coffee(ccode, cname, cprice) VALUES(104, '카라멜 마키아또', 5900);
INSERT INTO coffee(ccode, cname, cprice) VALUES(105, '초콜릿 모카', 5900);
INSERT INTO coffee(ccode, cname, cprice) VALUES(106, '콜드 브루', 4900);
INSERT INTO coffee(ccode, cname, cprice) VALUES(107, '에스프레소', 4000);
INSERT INTO coffee(ccode, cname, cprice) VALUES(108, '에스프레소 콘 파나', 4200);
INSERT INTO coffee(ccode, cname, cprice) VALUES(109, '카페 라떼', 5000);
INSERT INTO coffee(ccode, cname, cprice) VALUES(110, '바닐라 플랫 화이트', 5900);
INSERT INTO coffee(ccode, cname, cprice) VALUES(111, '아이스 커피', 4500);
INSERT INTO coffee(ccode, cname, cprice) VALUES(112, '오늘의 커피', 4200);
INSERT INTO coffee(ccode, cname, cprice) VALUES(113, '디카페인 카페 라떼', 5300);
INSERT INTO coffee(ccode, cname, cprice) VALUES(114, '디카페인 카페 아메리카노', 4800);
INSERT INTO coffee(ccode, cname, cprice) VALUES(115, '디카페인 카라멜 마키아또', 6200);

INSERT INTO beverage(bcode, bname, bprice) VALUES(201,'딸기 아사이 레모네이드', 5900);
INSERT INTO beverage(bcode, bname, bprice) VALUES(202,'말차 라떼', 6100);
INSERT INTO beverage(bcode, bname, bprice) VALUES(203,'블랙 밀크 티', 5900);
INSERT INTO beverage(bcode, bname, bprice) VALUES(204,'자몽 블랙 티', 5700);
INSERT INTO beverage(bcode, bname, bprice) VALUES(205,'차이 티 라떼', 5500);
INSERT INTO beverage(bcode, bname, bprice) VALUES(206,'제주 유기 녹차', 5300);
INSERT INTO beverage(bcode, bname, bprice) VALUES(207,'얼 그레이 티', 4500);
INSERT INTO beverage(bcode, bname, bprice) VALUES(208,'캐모마일', 4500);
INSERT INTO beverage(bcode, bname, bprice) VALUES(209,'쿨 라임', 5900);
INSERT INTO beverage(bcode, bname, bprice) VALUES(210,'블랙 티 레모네이드', 5400);
INSERT INTO beverage(bcode, bname, bprice) VALUES(211,'탱고 티 레모네이드', 5400);
INSERT INTO beverage(bcode, bname, bprice) VALUES(212,'코코아', 4500);
INSERT INTO beverage(bcode, bname, bprice) VALUES(213,'딸기 딜라이트 요거트', 6300);
INSERT INTO beverage(bcode, bname, bprice) VALUES(214,'망고 바나나', 6300);
INSERT INTO beverage(bcode, bname, bprice) VALUES(215,'망고 패션 프루트', 5400);

INSERT INTO dessert(dcode, dname, dprice) VALUES(301, '치킨 지즈 치아바타', 5800);
INSERT INTO dessert(dcode, dname, dprice) VALUES(302, '베이컨 치즈 토스트', 4900);
INSERT INTO dessert(dcode, dname, dprice) VALUES(303, '토마토 크림치즈 베이글', 5300);
INSERT INTO dessert(dcode, dname, dprice) VALUES(304, '샌드위치', 4900);
INSERT INTO dessert(dcode, dname, dprice) VALUES(305, '햄 올리브 샌드위치', 5900);
INSERT INTO dessert(dcode, dname, dprice) VALUES(306, '단호박 에그 샌드위치', 4900);
INSERT INTO dessert(dcode, dname, dprice) VALUES(307, '에그에그 샌드위치', 4400);
INSERT INTO dessert(dcode, dname, dprice) VALUES(308, '잉글리쉬 머핀', 4200);
INSERT INTO dessert(dcode, dname, dprice) VALUES(309, '트리플 치즈 크로크무슈', 5200);
INSERT INTO dessert(dcode, dname, dprice) VALUES(310, '토마토 로제 수프', 4200);
INSERT INTO dessert(dcode, dname, dprice) VALUES(311, '단호박 크림 수프', 4200);
INSERT INTO dessert(dcode, dname, dprice) VALUES(312, '트러플 머쉬룸 수프', 4200);
INSERT INTO dessert(dcode, dname, dprice) VALUES(313, '한입 고구마', 3800);
INSERT INTO dessert(dcode, dname, dprice) VALUES(314, '리얼 두부칩', 3400);
INSERT INTO dessert(dcode, dname, dprice) VALUES(315, '유산균 옐로푸드 쉐이크', 2700);
INSERT INTO dessert(dcode, dname, dprice) VALUES(316, '콜라겐 레드푸드 쉐이크', 2700);
INSERT INTO dessert(dcode, dname, dprice) VALUES(317, '프로틴 블랙푸드 쉐이크', 2700);
INSERT INTO dessert(dcode, dname, dprice) VALUES(318, '카스텔라', 9500);
INSERT INTO dessert(dcode, dname, dprice) VALUES(319, '치즈 베이글 칩', 2700);
INSERT INTO dessert(dcode, dname, dprice) VALUES(320, '라이스 칩', 2700);

select * from customers;
select * from backupdeletecustomers;