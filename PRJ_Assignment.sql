CREATE DATABASE StoreManagement

GO

USE StoreManagement

GO

CREATE TABLE tblRoles(
	roleID   nvarchar(50) PRIMARY KEY,
	roleName nvarchar(50),
	[status] bit
)

GO

CREATE TABLE tblUsers(
	userID     char(100) PRIMARY KEY,
	fullName   nvarchar(100),
	[password] varchar(100),
	roleID     nvarchar(50) REFERENCES tblRoles(roleID),
	[address]  nvarchar(150),
	birthday   date,
	phone      varchar(20),
	[status]   bit
)

GO

CREATE TABLE tblOrder(
	orderID   int PRIMARY KEY,
	orderDate date,
	total     int,
	userID    char(100) REFERENCES tblUsers(userID)
)

GO

CREATE TABLE tblCategory(
	categoryID   int PRIMARY KEY,
	categoryName nvarchar(50),
	[status]	 bit
)

GO

CREATE TABLE tblProduct(
	productID       nvarchar(20) PRIMARY KEY,
	productName		nvarchar(40),
	[image]			varchar(200),
	price			int,
	quantity		int,
	categoryID		int REFERENCES tblCategory(categoryID),
	importDate      date,
	usingDate       date,
	[status]		bit
)

GO

CREATE TABLE tblOrderDetail(
	detailID  int identity(1,1) PRIMARY KEY,
	price     int,
	quantity  int,
	orderID   int REFERENCES tblOrder(orderID),
	productID nvarchar(20) REFERENCES tblProduct(productID)
)

GO

INSERT INTO tblRoles VALUES
('AD', 'Admin', 1),
('US', 'User', 1)

GO

INSERT INTO tblUsers VALUES
('khanhtran@gmail.com', 'Tran Thi Van Khanh', '12345', 'AD', '30 Trần Phú, Đông Hà, Quảng Trị', '2002/12/16', '0945167243', 1),
('matthews121@gmail.com', 'Matthews Samuel', 'matt123', 'US', '232 Diane St., Washington D.C, NY', '1992/01/09', '122-334-222', 1),
('kobi@gmail.com', 'Lee Jung Jae', 'ljj123', 'US', '23 Pyongchang City, Korea', '1991/02/04', '09022-222', 1),
('martin1221@gmail.com', 'Martin Thompson', 'martin69', 'US', '100 Harriet Blv., Wisconsin', '1982/01/09', '233-332-575', 1),
('harrypotter12@gmail.com', 'Harry Potter', '123321', 'US', '12 King Palace, Kingston, UK', '1992/11/09', '3232-222-229', 1),
('monicaluv@gmail.com', 'Monica Risa', 'monica1609', 'US', '989 Luisa Apartment, Santa Monica, CA', '2009/10/09', '687-343-787', 1),
('godrick888@gmail.com', 'Godrick Min', 'thelord', 'US', '991 Bond St., Salt Lake, NJ', '1988/12/10', '344-5656-222', 1)

GO

INSERT INTO tblOrder VALUES
(190, '2022/02/02', 100000, 'khanhtran@gmail.com'),
(191, '2022/02/03', 1230000, 'matthews121@gmail.com')

GO

INSERT INTO tblCategory VALUES
(1, 'Rau củ', 1),
(2, 'Nấm', 1),
(3, 'Hoa quả', 1),
(4, 'Rau gia vị', 1)

GO

INSERT INTO tblProduct VALUES
('123', 'Rau bi na', 'https://cdn.tgdd.vn/Files/2017/08/23/1015484/rau-bina-la-rau-gi-1_800x450.jpg', 15000, 650, 1, '2022-01-15', '2022-02-15', 1),
('124', 'Buoi nam roi', 'https://cdn.tgdd.vn/Files/2019/09/21/1200406/vi-sao-goi-la-buoi-nam-roi-va-cach-chon-buoi-nam-roi-ngon-201909211736395895.jpg', 50000, 650, 3, '2022-01-15', '2022-02-15', 1),
('125', 'Nam rom', 'https://thucpham.com/wp-content/uploads/2017/01/nam-rom.jpg', 5000, 30, 2, '2022-01-15', '2022-03-30', 1),
('126', 'Rau den', 'https://vinmec-prod.s3.amazonaws.com/images/20201226_005345_144787_rau_den.max-800x800.jpg', 5000, 20, 1, '2022-01-15', '2022-02-18', 1),
('127', 'Nam meo', 'https://vcdn-suckhoe.vnecdn.net/2016/07/12/mn-18-PITR-4752-1468301042.jpg', 15000, 150, 2, '2022-01-15', '2022-06-15', 1),
('128', 'Rau cai', 'https://vinmec-prod.s3.amazonaws.com/images/20200513_164911_307202_raucai.max-800x800.png', 15000, 6500, 1, '2022-01-15', '2022-07-27', 1),
('129', 'Thi la', 'https://cdn.caythuocdangian.com/2019/03/cay-thi-la.jpg', 3000, 350, 4, '2022-01-15', '2022-02-15', 1),
('130', 'Rau khoai', 'https://benh.vn/wp-content/uploads/2018/04/tac-dung-cua-rau-khoai-lang-benhvn.jpg', 4000, 220, 1, '2022-01-15', '2022-08-15',1),
('131', 'Xoai Da Lat', 'https://suna.vn/wp-content/uploads/2018/11/xoai-say-deo-da-lat-ngon.jpg', 30000, 5, 3, '2022-01-15', '2022-10-25', 1),
('132', 'Hanh la', 'https://vinmec-prod.s3.amazonaws.com/images/20200711_003716_460645_download_4.max-1800x1800.jpg', 2000, 322, 4, '2022-01-15', '2023-02-15', 1)

GO

INSERT INTO tblOrderDetail VALUES
(60000, 4, 190, '123')

GO
