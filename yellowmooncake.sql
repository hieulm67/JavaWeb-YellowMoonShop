USE [master]
GO
/****** Object:  Database [YellowMoonCake]    Script Date: 10/18/2020 1:50:28 PM ******/
CREATE DATABASE [YellowMoonCake]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'YellowMoonCake', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\YellowMoonCake.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'YellowMoonCake_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\YellowMoonCake_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [YellowMoonCake] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [YellowMoonCake].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [YellowMoonCake] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [YellowMoonCake] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [YellowMoonCake] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [YellowMoonCake] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [YellowMoonCake] SET ARITHABORT OFF 
GO
ALTER DATABASE [YellowMoonCake] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [YellowMoonCake] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [YellowMoonCake] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [YellowMoonCake] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [YellowMoonCake] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [YellowMoonCake] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [YellowMoonCake] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [YellowMoonCake] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [YellowMoonCake] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [YellowMoonCake] SET  DISABLE_BROKER 
GO
ALTER DATABASE [YellowMoonCake] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [YellowMoonCake] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [YellowMoonCake] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [YellowMoonCake] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [YellowMoonCake] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [YellowMoonCake] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [YellowMoonCake] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [YellowMoonCake] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [YellowMoonCake] SET  MULTI_USER 
GO
ALTER DATABASE [YellowMoonCake] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [YellowMoonCake] SET DB_CHAINING OFF 
GO
ALTER DATABASE [YellowMoonCake] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [YellowMoonCake] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [YellowMoonCake] SET DELAYED_DURABILITY = DISABLED 
GO
USE [YellowMoonCake]
GO
/****** Object:  Table [dbo].[tblCategory]    Script Date: 10/18/2020 1:50:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblCategory](
	[categoryId] [varchar](10) NOT NULL,
	[categoryName] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[categoryId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblLog]    Script Date: 10/18/2020 1:50:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblLog](
	[logId] [int] IDENTITY(1,1) NOT NULL,
	[userEmail] [varchar](100) NOT NULL,
	[productId] [int] NOT NULL,
	[logDate] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[logId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblOrder]    Script Date: 10/18/2020 1:50:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblOrder](
	[orderId] [uniqueidentifier] NOT NULL DEFAULT (newid()),
	[totalPrice] [float] NOT NULL,
	[orderDate] [datetime] NOT NULL DEFAULT (getdate()),
	[userEmail] [varchar](100) NOT NULL,
	[userName] [nvarchar](50) NOT NULL,
	[userPhone] [varchar](20) NOT NULL,
	[userAddress] [nvarchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[orderId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblOrderDetail]    Script Date: 10/18/2020 1:50:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblOrderDetail](
	[detailId] [int] IDENTITY(1,1) NOT NULL,
	[orderId] [uniqueidentifier] NOT NULL,
	[productId] [int] NOT NULL,
	[productPrice] [float] NOT NULL,
	[productQuantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[detailId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[tblProduct]    Script Date: 10/18/2020 1:50:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblProduct](
	[productId] [int] IDENTITY(1,1) NOT NULL,
	[productName] [nvarchar](100) NOT NULL,
	[productImage] [nvarchar](200) NOT NULL,
	[productCreateDate] [datetime] NOT NULL DEFAULT (getdate()),
	[productExpirationDate] [datetime] NOT NULL DEFAULT (dateadd(month,(5),getdate())),
	[productPrice] [float] NOT NULL,
	[productQuantity] [int] NOT NULL,
	[productCategory] [varchar](10) NOT NULL,
	[productStatus] [varchar](10) NULL DEFAULT ('A'),
PRIMARY KEY CLUSTERED 
(
	[productId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblRole]    Script Date: 10/18/2020 1:50:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblRole](
	[roleId] [varchar](10) NOT NULL,
	[roleName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tblRole] PRIMARY KEY CLUSTERED 
(
	[roleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblStatus]    Script Date: 10/18/2020 1:50:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblStatus](
	[statusId] [varchar](10) NOT NULL,
	[statusName] [nvarchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[statusId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblUser]    Script Date: 10/18/2020 1:50:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblUser](
	[userEmail] [varchar](100) NOT NULL,
	[userPassword] [varchar](30) NOT NULL,
	[userName] [nvarchar](50) NOT NULL,
	[userPhone] [varchar](20) NOT NULL,
	[userAddress] [nvarchar](200) NOT NULL,
	[userRole] [varchar](10) NOT NULL,
	[userStatus] [varchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[userEmail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[tblCategory] ([categoryId], [categoryName]) VALUES (N'C', N'Combo')
INSERT [dbo].[tblCategory] ([categoryId], [categoryName]) VALUES (N'P', N'Cake')
INSERT [dbo].[tblCategory] ([categoryId], [categoryName]) VALUES (N'U', N'Unknown')
SET IDENTITY_INSERT [dbo].[tblLog] ON 

INSERT [dbo].[tblLog] ([logId], [userEmail], [productId], [logDate]) VALUES (1, N'hieulm@gmail.com', 14, CAST(N'2020-10-15 19:10:34.750' AS DateTime))
INSERT [dbo].[tblLog] ([logId], [userEmail], [productId], [logDate]) VALUES (2, N'hieulm@gmail.com', 15, CAST(N'2020-10-18 13:16:06.623' AS DateTime))
INSERT [dbo].[tblLog] ([logId], [userEmail], [productId], [logDate]) VALUES (3, N'hieulm@gmail.com', 13, CAST(N'2020-10-18 13:12:58.097' AS DateTime))
INSERT [dbo].[tblLog] ([logId], [userEmail], [productId], [logDate]) VALUES (4, N'hieulm@gmail.com', 16, CAST(N'2020-10-18 13:15:36.717' AS DateTime))
SET IDENTITY_INSERT [dbo].[tblLog] OFF
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'2223c384-c6f7-4dcf-854b-18809fd17b23', 59, CAST(N'2020-10-18 13:31:30.980' AS DateTime), N'vt@gmail.com', N'Tran san', N'0123456789', N'Nhà ở Caauf Giaays')
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'45ea73dd-d1a1-4da6-92d8-5660073784a6', 118, CAST(N'2020-10-18 13:33:17.107' AS DateTime), N'hieulmse140108@fpt.edu.vn', N'Hieu Ne', N'0123445678', N'TPHCM')
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'98fa48e2-c291-4904-82e1-96499008969c', 600, CAST(N'2020-10-17 17:51:32.887' AS DateTime), N'longlb@gmail.com', N'Long Le', N'0987654321', N'Ai biết')
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'6cfc77c8-8208-4aca-9aac-a00a22bc358f', 590, CAST(N'2020-10-18 13:22:13.003' AS DateTime), N'longlb@gmail.com', N'Long Le', N'0987654321', N'Ai biết')
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'80114aa3-06aa-4f50-9a05-a8ff30e85cd9', 114, CAST(N'2020-10-18 13:47:40.523' AS DateTime), N'hieulmse140108@fpt.edu.vn', N'Hieu Ne', N'0123445678', N'TPHCM')
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'2d67b277-6cc9-439a-8071-c31d3d1eb94c', 520, CAST(N'2020-10-18 13:21:20.400' AS DateTime), N'tulong@gmail.com', N'Too Long', N'0987321456', N'Nhà ở Ba Đình')
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'bcaec1c8-eba6-4060-9b2c-c4d1526a7129', 360, CAST(N'2020-10-17 18:50:07.047' AS DateTime), N'minhtn@gmail.com', N'Minh Tust', N'0896745321', N'Vũng Tàu')
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'c7f72027-a47f-4820-8ded-dc0090c11d65', 195, CAST(N'2020-10-17 18:22:09.587' AS DateTime), N'longlb@gmail.com', N'Long Le', N'0987654321', N'Ai biết')
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'a55874b3-af29-4bbd-b15e-f0943d9e7356', 790, CAST(N'2020-10-17 12:35:12.300' AS DateTime), N'tustlong@gmail.com', N'Tuts Long', N'0123456789', N'Nhà ở Ba Đình')
INSERT [dbo].[tblOrder] ([orderId], [totalPrice], [orderDate], [userEmail], [userName], [userPhone], [userAddress]) VALUES (N'6a40dddd-6210-428d-8260-fee3e51644a3', 59, CAST(N'2020-10-17 11:47:45.533' AS DateTime), N'longtust@gmail.com', N'Long Tust', N'0987654321', N'Nhà ở Cầu Giấy')
SET IDENTITY_INSERT [dbo].[tblOrderDetail] ON 

INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (1, N'6a40dddd-6210-428d-8260-fee3e51644a3', 15, 19, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (2, N'a55874b3-af29-4bbd-b15e-f0943d9e7356', 15, 19, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (3, N'a55874b3-af29-4bbd-b15e-f0943d9e7356', 11, 19, 20)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (4, N'a55874b3-af29-4bbd-b15e-f0943d9e7356', 13, 22, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (5, N'98fa48e2-c291-4904-82e1-96499008969c', 13, 22, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (6, N'98fa48e2-c291-4904-82e1-96499008969c', 15, 19, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (7, N'98fa48e2-c291-4904-82e1-96499008969c', 11, 19, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (8, N'c7f72027-a47f-4820-8ded-dc0090c11d65', 3, 2.5, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (9, N'c7f72027-a47f-4820-8ded-dc0090c11d65', 5, 15, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (10, N'c7f72027-a47f-4820-8ded-dc0090c11d65', 1, 2, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (11, N'bcaec1c8-eba6-4060-9b2c-c4d1526a7129', 1, 2, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (12, N'bcaec1c8-eba6-4060-9b2c-c4d1526a7129', 10, 18, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (13, N'bcaec1c8-eba6-4060-9b2c-c4d1526a7129', 9, 16, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (14, N'2d67b277-6cc9-439a-8071-c31d3d1eb94c', 16, 19, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (15, N'2d67b277-6cc9-439a-8071-c31d3d1eb94c', 5, 15, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (16, N'2d67b277-6cc9-439a-8071-c31d3d1eb94c', 10, 18, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (17, N'6cfc77c8-8208-4aca-9aac-a00a22bc358f', 13, 22, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (18, N'6cfc77c8-8208-4aca-9aac-a00a22bc358f', 16, 19, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (19, N'6cfc77c8-8208-4aca-9aac-a00a22bc358f', 10, 18, 10)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (20, N'2223c384-c6f7-4dcf-854b-18809fd17b23', 13, 22, 1)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (21, N'2223c384-c6f7-4dcf-854b-18809fd17b23', 11, 19, 1)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (22, N'2223c384-c6f7-4dcf-854b-18809fd17b23', 10, 18, 1)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (23, N'45ea73dd-d1a1-4da6-92d8-5660073784a6', 10, 18, 2)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (24, N'45ea73dd-d1a1-4da6-92d8-5660073784a6', 13, 22, 2)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (25, N'45ea73dd-d1a1-4da6-92d8-5660073784a6', 11, 19, 2)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (26, N'80114aa3-06aa-4f50-9a05-a8ff30e85cd9', 9, 16, 2)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (27, N'80114aa3-06aa-4f50-9a05-a8ff30e85cd9', 13, 22, 2)
INSERT [dbo].[tblOrderDetail] ([detailId], [orderId], [productId], [productPrice], [productQuantity]) VALUES (28, N'80114aa3-06aa-4f50-9a05-a8ff30e85cd9', 11, 19, 2)
SET IDENTITY_INSERT [dbo].[tblOrderDetail] OFF
SET IDENTITY_INSERT [dbo].[tblProduct] ON 

INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (1, N'Bánh mèo cry1', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/pic1.jpg', CAST(N'2020-10-11 17:07:38.237' AS DateTime), CAST(N'2021-03-11 17:07:38.237' AS DateTime), 2, 80, N'P', N'A')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (3, N'Bánh mèo meme2', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/pic2.jpg', CAST(N'2020-10-11 17:07:38.237' AS DateTime), CAST(N'2021-03-11 17:07:38.237' AS DateTime), 2.5, 90, N'C', N'A')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (4, N'Bánh mèo 3', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/pic3.jpg', CAST(N'2020-10-11 17:07:38.237' AS DateTime), CAST(N'2021-03-11 17:07:38.237' AS DateTime), 7, 100, N'C', N'D')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (5, N'Bánh mèo cry', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/cry.jpeg', CAST(N'2020-10-11 17:07:38.240' AS DateTime), CAST(N'2021-03-11 17:07:38.240' AS DateTime), 15, 80, N'P', N'A')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (6, N'Bánh meme', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/meme.jpg', CAST(N'2020-10-11 17:07:38.240' AS DateTime), CAST(N'2021-03-11 17:07:38.240' AS DateTime), 17, 100, N'C', N'D')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (9, N'Moon Cake RedBean', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/redbean.jpg', CAST(N'2020-10-11 22:54:55.187' AS DateTime), CAST(N'2021-03-11 22:54:55.187' AS DateTime), 16, 88, N'P', N'A')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (10, N'Moon Cake Taro', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/taro.jpg', CAST(N'2020-10-11 22:55:28.353' AS DateTime), CAST(N'2021-03-11 22:55:28.353' AS DateTime), 18, 67, N'P', N'A')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (11, N'Bánh yolk moon', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/Bánh yolk moon.jpeg', CAST(N'2020-10-12 23:14:59.257' AS DateTime), CAST(N'2021-03-12 23:14:59.257' AS DateTime), 19, 65, N'P', N'A')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (13, N'Bánh vànggg', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/Bánh vànggg.jpeg', CAST(N'2020-10-13 00:00:00.000' AS DateTime), CAST(N'2021-03-13 00:00:00.000' AS DateTime), 22, 65, N'P', N'A')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (14, N'Bánh unLikeeeee', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/Bánh Likeeeee.jpeg', CAST(N'2020-05-13 00:00:00.000' AS DateTime), CAST(N'2020-10-13 00:00:00.000' AS DateTime), 8, 100, N'P', N'E')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (15, N'Bánh Cá', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/Bánh Cá.jpeg', CAST(N'2020-10-15 00:00:00.000' AS DateTime), CAST(N'2021-03-15 00:00:00.000' AS DateTime), 19, 9980, N'P', N'A')
INSERT [dbo].[tblProduct] ([productId], [productName], [productImage], [productCreateDate], [productExpirationDate], [productPrice], [productQuantity], [productCategory], [productStatus]) VALUES (16, N'Bánh nhân khoai môn', N'D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/Bánh nhân khoai môn.jpeg', CAST(N'2020-10-18 13:15:36.707' AS DateTime), CAST(N'2021-03-18 13:15:36.707' AS DateTime), 19, 980, N'C', N'A')
SET IDENTITY_INSERT [dbo].[tblProduct] OFF
INSERT [dbo].[tblRole] ([roleId], [roleName]) VALUES (N'A', N'Admin')
INSERT [dbo].[tblRole] ([roleId], [roleName]) VALUES (N'G', N'Guest')
INSERT [dbo].[tblRole] ([roleId], [roleName]) VALUES (N'M', N'Member')
INSERT [dbo].[tblStatus] ([statusId], [statusName]) VALUES (N'A', N'Active')
INSERT [dbo].[tblStatus] ([statusId], [statusName]) VALUES (N'D', N'Deactive')
INSERT [dbo].[tblStatus] ([statusId], [statusName]) VALUES (N'E', N'Expire')
INSERT [dbo].[tblUser] ([userEmail], [userPassword], [userName], [userPhone], [userAddress], [userRole], [userStatus]) VALUES (N'emiulethanhnam@gmail.com', N'123456', N'Ko phai Hieu', N'1234555321', N'TPHN', N'A', N'A')
INSERT [dbo].[tblUser] ([userEmail], [userPassword], [userName], [userPhone], [userAddress], [userRole], [userStatus]) VALUES (N'hieulm@gmail.com', N'123456', N'Hieu Le', N'0123456789', N'Đ biết', N'A', N'A')
INSERT [dbo].[tblUser] ([userEmail], [userPassword], [userName], [userPhone], [userAddress], [userRole], [userStatus]) VALUES (N'hieulmse140108@fpt.edu.vn', N'123456', N'Hieu Ne', N'0123445678', N'TPHCM', N'M', N'A')
INSERT [dbo].[tblUser] ([userEmail], [userPassword], [userName], [userPhone], [userAddress], [userRole], [userStatus]) VALUES (N'longlb@gmail.com', N'123456', N'Long Le', N'0987654321', N'Ai biết', N'M', N'A')
INSERT [dbo].[tblUser] ([userEmail], [userPassword], [userName], [userPhone], [userAddress], [userRole], [userStatus]) VALUES (N'minhtn@gmail.com', N'123456', N'Minh Tust', N'0896745321', N'Vũng Tàu', N'M', N'A')
ALTER TABLE [dbo].[tblLog]  WITH CHECK ADD  CONSTRAINT [FK_tblLog_tblProduct] FOREIGN KEY([productId])
REFERENCES [dbo].[tblProduct] ([productId])
GO
ALTER TABLE [dbo].[tblLog] CHECK CONSTRAINT [FK_tblLog_tblProduct]
GO
ALTER TABLE [dbo].[tblLog]  WITH CHECK ADD  CONSTRAINT [FK_tblLog_tblUser] FOREIGN KEY([userEmail])
REFERENCES [dbo].[tblUser] ([userEmail])
GO
ALTER TABLE [dbo].[tblLog] CHECK CONSTRAINT [FK_tblLog_tblUser]
GO
ALTER TABLE [dbo].[tblOrderDetail]  WITH CHECK ADD FOREIGN KEY([orderId])
REFERENCES [dbo].[tblOrder] ([orderId])
GO
ALTER TABLE [dbo].[tblOrderDetail]  WITH CHECK ADD FOREIGN KEY([productId])
REFERENCES [dbo].[tblProduct] ([productId])
GO
ALTER TABLE [dbo].[tblProduct]  WITH CHECK ADD FOREIGN KEY([productCategory])
REFERENCES [dbo].[tblCategory] ([categoryId])
GO
ALTER TABLE [dbo].[tblProduct]  WITH CHECK ADD FOREIGN KEY([productStatus])
REFERENCES [dbo].[tblStatus] ([statusId])
GO
ALTER TABLE [dbo].[tblUser]  WITH CHECK ADD FOREIGN KEY([userRole])
REFERENCES [dbo].[tblRole] ([roleId])
GO
ALTER TABLE [dbo].[tblUser]  WITH CHECK ADD FOREIGN KEY([userStatus])
REFERENCES [dbo].[tblStatus] ([statusId])
GO
USE [master]
GO
ALTER DATABASE [YellowMoonCake] SET  READ_WRITE 
GO
