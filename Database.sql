-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3307
-- Thời gian đã tạo: Th5 20, 2025 lúc 05:16 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

drop database if exists cnpm;
create database cnpm;
use cnpm;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `cnpm`
--

DELIMITER $$
--
-- Thủ tục
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `next_id` (OUT `newId` VARCHAR(10))   BEGIN
    DECLARE currentNumeric INT;
    DECLARE newNumeric INT;
    DECLARE currentId VARCHAR(10);

    SELECT id INTO currentId FROM id_seq FOR UPDATE;

    SET currentNumeric = CAST(SUBSTRING(currentId, 3) AS UNSIGNED);
    SET newNumeric = currentNumeric + 1;

    SET newId = CONCAT('ID', LPAD(newNumeric, 8, '0'));

    UPDATE id_seq SET id = newId;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `category_id` varchar(10) NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `usage_count` bigint(20) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`category_id`, `category_name`, `usage_count`) VALUES
('ID00000006', 'Vietnamese', 12),
('ID00000007', 'Dessert', 8),
('ID00000008', 'Salad', 5),
('ID00000009', 'Soup', 7),
('ID00000010', 'Beverage', 9);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comments`
--

CREATE TABLE `comments` (
  `comment_id` varchar(10) NOT NULL,
  `recipe_id` varchar(10) NOT NULL,
  `user_id` varchar(10) DEFAULT NULL,
  `comment_content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `comments`
--

INSERT INTO `comments` (`comment_id`, `recipe_id`, `user_id`, `comment_content`, `created_at`) VALUES
('ID00000021', 'ID00000011', 'ID00000002', 'Nước dùng rất ngon, thịt mềm!', '2025-05-19 09:21:27'),
('ID00000022', 'ID00000012', 'ID00000003', 'Bánh flan hơi ngọt, cần giảm đường một chút.', '2025-05-19 09:21:27'),
('ID00000023', 'ID00000013', 'ID00000004', 'Tươi mát, rất hợp mùa hè.', '2025-05-19 09:21:27'),
('ID00000024', 'ID00000014', 'ID00000005', 'Cá tươi, nước chua vừa phải.', '2025-05-19 09:21:27'),
('ID00000025', 'ID00000015', 'ID00000001', 'Trân châu dai, trà thơm.', '2025-05-19 09:21:27');

--
-- Bẫy `comments`
--
DELIMITER $$
CREATE TRIGGER `trg_after_comment_delete` AFTER DELETE ON `comments` FOR EACH ROW BEGIN
  UPDATE recipe_stats
  SET comment_count = comment_count - 1
  WHERE recipe_id = OLD.recipe_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_comment_insert` AFTER INSERT ON `comments` FOR EACH ROW BEGIN
  UPDATE recipe_stats
  SET comment_count = comment_count + 1
  WHERE recipe_id = NEW.recipe_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_comment_update` AFTER UPDATE ON `comments` FOR EACH ROW BEGIN
  IF OLD.recipe_id <> NEW.recipe_id THEN
    UPDATE recipe_stats
    SET comment_count = comment_count - 1
    WHERE recipe_id = OLD.recipe_id;
    UPDATE recipe_stats
    SET comment_count = comment_count + 1
    WHERE recipe_id = NEW.recipe_id;
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `follows`
--

CREATE TABLE `follows` (
  `follow_id` varchar(10) NOT NULL,
  `follower_id` varchar(10) NOT NULL,
  `following_id` varchar(10) NOT NULL,
  `follow_time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `follows`
--

INSERT INTO `follows` (`follow_id`, `follower_id`, `following_id`, `follow_time`) VALUES
('ID00000041', 'ID00000001', 'ID00000002', '2025-05-19 09:21:27'),
('ID00000042', 'ID00000002', 'ID00000003', '2025-05-19 09:21:27'),
('ID00000043', 'ID00000003', 'ID00000004', '2025-05-19 09:21:27'),
('ID00000044', 'ID00000004', 'ID00000005', '2025-05-19 09:21:27'),
('ID00000045', 'ID00000005', 'ID00000001', '2025-05-19 09:21:27');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `id_seq`
--

CREATE TABLE `id_seq` (
  `id` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `id_seq`
--

INSERT INTO `id_seq` (`id`) VALUES
('ID00000045'),
('ID00000046'),
('ID00000047'),
('ID00000048'),
('ID00000049'),
('ID00000050'),
('ID00000051'),
('ID00000052'),
('ID00000053'),
('ID00000054'),
('ID00000055'),
('ID00000056'),
('ID00000057'),
('ID00000058'),
('ID00000059'),
('ID00000060'),
('ID00000061'),
('ID00000062'),
('ID00000063'),
('ID00000064'),
('ID00000065'),
('ID00000066'),
('ID00000067'),
('ID00000068'),
('ID00000069'),
('ID00000070'),
('ID00000071'),
('ID00000072'),
('ID00000073'),
('ID00000074'),
('ID00000075'),
('ID00000076'),
('ID00000077'),
('ID00000078'),
('ID00000079'),
('ID00000080'),
('ID00000081'),
('ID00000082'),
('ID00000083');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `likes`
--

CREATE TABLE `likes` (
  `like_id` varchar(10) NOT NULL,
  `recipe_id` varchar(10) NOT NULL,
  `user_id` varchar(10) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `likes`
--

INSERT INTO `likes` (`like_id`, `recipe_id`, `user_id`, `created_at`) VALUES
('ID00000019', 'ID00000013', 'ID00000004', '2025-05-19 09:21:27'),
('ID00000020', 'ID00000011', 'ID00000005', '2025-05-19 09:21:27'),
('ID00000062', 'ID00000011', 'ID00000001', '2025-05-19 21:19:11'),
('ID00000063', 'ID00000012', 'ID00000001', '2025-05-19 21:29:02');

--
-- Bẫy `likes`
--
DELIMITER $$
CREATE TRIGGER `trg_after_like_delete` AFTER DELETE ON `likes` FOR EACH ROW BEGIN
  UPDATE recipe_stats
  SET like_count = like_count - 1
  WHERE recipe_id = OLD.recipe_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_like_insert` AFTER INSERT ON `likes` FOR EACH ROW BEGIN
  UPDATE recipe_stats
  SET like_count = like_count + 1
  WHERE recipe_id = NEW.recipe_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_like_update` AFTER UPDATE ON `likes` FOR EACH ROW BEGIN
  IF OLD.recipe_id <> NEW.recipe_id THEN
    UPDATE recipe_stats
    SET like_count = like_count - 1
    WHERE recipe_id = OLD.recipe_id;
    UPDATE recipe_stats
    SET like_count = like_count + 1
    WHERE recipe_id = NEW.recipe_id;
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `media`
--

CREATE TABLE `media` (
  `media_id` varchar(10) NOT NULL,
  `recipe_id` varchar(10) NOT NULL,
  `file_url` varchar(255) NOT NULL,
  `media_type` varchar(20) DEFAULT NULL CHECK (`media_type` in ('image','video')),
  `upload_time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `media`
--

INSERT INTO `media` (`media_id`, `recipe_id`, `file_url`, `media_type`, `upload_time`) VALUES
('ID00000036', 'ID00000011', '/media/pho_ga_1.jpg', 'image', '2025-05-19 09:21:27'),
('ID00000037', 'ID00000012', '/media/banh_flan.mp4', 'video', '2025-05-19 09:21:27'),
('ID00000038', 'ID00000013', '/media/goi_cuon_1.jpg', 'image', '2025-05-19 09:21:27'),
('ID00000039', 'ID00000014', '/media/canh_chua_ca_1.jpg', 'image', '2025-05-19 09:21:27'),
('ID00000040', 'ID00000015', '/media/tra_sua_1.jpg', 'image', '2025-05-19 09:21:27');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rates`
--

CREATE TABLE `rates` (
  `rate_id` varchar(10) NOT NULL,
  `recipe_id` varchar(10) NOT NULL,
  `user_id` varchar(10) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL CHECK (`rating` >= 1 and `rating` <= 5),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `rates`
--

INSERT INTO `rates` (`rate_id`, `recipe_id`, `user_id`, `rating`, `created_at`) VALUES
('ID00000026', 'ID00000011', 'ID00000001', 3, '2025-05-19 09:21:27'),
('ID00000027', 'ID00000011', 'ID00000002', 4, '2025-05-19 09:21:27'),
('ID00000028', 'ID00000013', 'ID00000003', 5, '2025-05-19 09:21:27'),
('ID00000029', 'ID00000014', 'ID00000004', 4, '2025-05-19 09:21:27'),
('ID00000030', 'ID00000015', 'ID00000005', 5, '2025-05-19 09:21:27');

--
-- Bẫy `rates`
--
DELIMITER $$
CREATE TRIGGER `trg_after_rate_delete` AFTER DELETE ON `rates` FOR EACH ROW BEGIN
  UPDATE recipe_stats
  SET
    rate_count     = rate_count - 1,
    average_rating = CASE
      WHEN (rate_count - 1) > 0
        THEN ((average_rating * rate_count) - OLD.rating) / (rate_count - 1)
      ELSE 0
    END
  WHERE recipe_id = OLD.recipe_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_rate_insert` AFTER INSERT ON `rates` FOR EACH ROW BEGIN
  UPDATE recipe_stats
  SET
    rate_count = rate_count + 1,
    average_rating =
      (average_rating * (rate_count) + NEW.rating)
      / (rate_count)
  WHERE recipe_id = NEW.recipe_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_rate_move` AFTER UPDATE ON `rates` FOR EACH ROW BEGIN
  IF OLD.recipe_id <> NEW.recipe_id THEN
    -- Giảm ở recipe cũ
    UPDATE recipe_stats
    SET
      rate_count     = rate_count - 1,
      average_rating = CASE
        WHEN (rate_count - 1) > 0
          THEN ((average_rating * rate_count) - OLD.rating) / (rate_count - 1)
        ELSE 0
      END
    WHERE recipe_id = OLD.recipe_id;
    -- Tăng ở recipe mới
    UPDATE recipe_stats
    SET
      rate_count     = rate_count + 1,
      average_rating = CASE
        WHEN rate_count IS NULL OR rate_count = 0
          THEN NEW.rating
        ELSE ((average_rating * rate_count) + NEW.rating) / (rate_count + 1)
      END
    WHERE recipe_id = NEW.recipe_id;
  END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_rate_update` AFTER UPDATE ON `rates` FOR EACH ROW BEGIN
  IF NEW.rating <> OLD.rating THEN
    UPDATE recipe_stats
    SET average_rating = ((average_rating * rate_count) - OLD.rating + NEW.rating) / rate_count
    WHERE recipe_id = NEW.recipe_id;
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `recipes`
--

CREATE TABLE `recipes` (
  `recipe_id` varchar(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `instruction` varchar(1000) NOT NULL,
  `description` text DEFAULT NULL,
  `category_name` varchar(100) DEFAULT NULL,
  `category_id` varchar(10) DEFAULT NULL,
  `ingredients` text NOT NULL,
  `author_name` varchar(100) DEFAULT NULL,
  `author_id` varchar(10) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `avatar_url` varchar(255) DEFAULT NULL,
  `author_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `recipes`
--

INSERT INTO `recipes` (`recipe_id`, `name`, `instruction`, `description`, `category_name`, `category_id`, `ingredients`, `author_name`, `author_id`, `created_at`, `avatar_url`, `author_url`) VALUES
('ID00000011', 'Phở Gà', '1. Hầm xương gà; 2. Chần bánh phở & thịt gà; 3. Chan nước dùng', 'Phở gà thanh ngọt, thơm mùi gừng', 'Vietnamese', 'ID00000006', 'xương gà, bánh phở, thịt gà, gừng, hành, nước mắm', 'Alice Nguyễn', 'ID00000001', '2025-05-19 09:44:59', '/image/pho.png', '/avatars/alice.jpg'),
('ID00000012', 'Bánh Flan', '1. Trộn trứng, sữa, đường; 2. Hấp cách thủy 25p', 'Bánh flan mềm mịn, béo ngậy', 'Dessert', 'ID00000007', 'trứng, sữa tươi, đường, caramel', 'Alice Nguyen', 'ID00000001', '2025-05-19 09:45:49', '/image/banh_flan.png', '/avatars/alice.jpg'),
('ID00000013', 'Gỏi Cuốn', '1. Chuẩn bị tôm, thịt, rau sống; 2. Cuộn bánh tráng; 3. Pha nước chấm', 'Gỏi cuốn tươi mát, chấm ngọt cay', 'Salad', 'ID00000008', 'tôm, thịt luộc, bún, rau sống, bánh tráng', 'Alice Nguyen', 'ID00000001', '2025-05-19 09:46:40', '/image/goi_cuon.png', '/avatars/alice.jpg'),
('ID00000014', 'Canh Chua Cá', '1. Nấu me, sả, cà; 2. Thả cá, đậu bắp; 3. Nêm mắm, thêm rau ngổ', 'Canh chua đậm đà miền Nam', 'Soup', 'ID00000009', 'cá, me, sả, cà chua, đậu bắp, rau ngổ', 'Diana Phạm', 'ID00000004', '2025-05-19 09:47:12', '/image/canh_chua_ca.png', '/avatars/diana.jpg'),
('ID00000015', 'Trà Sữa Trân Châu', '1. Pha trà; 2. Đun sữa; 3. Kết hợp, thêm trân châu', 'Trà sữa ngọt dịu, trân châu dai giòn', 'Beverage', 'ID00000010', 'trà đen, sữa, trân châu, đường, đá', 'Edward Võ', 'ID00000005', '2025-05-19 09:47:39', '/image/tra_sua.png', '/avatars/edward.jpg');

--
-- Bẫy `recipes`
--
DELIMITER $$
CREATE TRIGGER `trg_after_recipe_insert` AFTER INSERT ON `recipes` FOR EACH ROW BEGIN
  INSERT INTO recipe_stats (recipe_id)
  VALUES (NEW.recipe_id);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `recipe_stats`
--

CREATE TABLE `recipe_stats` (
  `recipe_id` varchar(10) NOT NULL,
  `like_count` int(11) NOT NULL DEFAULT 0,
  `save_count` int(11) NOT NULL DEFAULT 0,
  `comment_count` int(11) NOT NULL DEFAULT 0,
  `rate_count` int(11) NOT NULL DEFAULT 0,
  `average_rating` decimal(3,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `recipe_stats`
--

INSERT INTO `recipe_stats` (`recipe_id`, `like_count`, `save_count`, `comment_count`, `rate_count`, `average_rating`) VALUES
('ID00000011', 2, 2, 1, 2, 3.33),
('ID00000012', 1, 2, 1, 0, 0.00),
('ID00000013', 1, 0, 1, 1, 5.00),
('ID00000014', 0, 1, 1, 1, 4.00),
('ID00000015', 0, 1, 1, 1, 5.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `saved`
--

CREATE TABLE `saved` (
  `save_id` varchar(10) NOT NULL,
  `recipe_id` varchar(10) NOT NULL,
  `user_id` varchar(10) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `saved`
--

INSERT INTO `saved` (`save_id`, `recipe_id`, `user_id`, `created_at`) VALUES
('ID00000032', 'ID00000014', 'ID00000002', '2025-05-19 09:21:27'),
('ID00000033', 'ID00000015', 'ID00000003', '2025-05-19 09:21:27'),
('ID00000034', 'ID00000011', 'ID00000004', '2025-05-19 09:21:27'),
('ID00000035', 'ID00000012', 'ID00000005', '2025-05-19 09:21:27'),
('ID00000076', 'ID00000011', 'ID00000001', '2025-05-19 21:46:31'),
('ID00000083', 'ID00000012', 'ID00000001', '2025-05-20 15:06:11');

--
-- Bẫy `saved`
--
DELIMITER $$
CREATE TRIGGER `trg_after_saved_delete` AFTER DELETE ON `saved` FOR EACH ROW BEGIN
  UPDATE recipe_stats
  SET save_count = save_count - 1
  WHERE recipe_id = OLD.recipe_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_saved_insert` AFTER INSERT ON `saved` FOR EACH ROW BEGIN
  UPDATE recipe_stats
  SET save_count = save_count + 1
  WHERE recipe_id = NEW.recipe_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_saved_update` AFTER UPDATE ON `saved` FOR EACH ROW BEGIN
  IF OLD.recipe_id <> NEW.recipe_id THEN
    UPDATE recipe_stats
    SET save_count = save_count - 1
    WHERE recipe_id = OLD.recipe_id;
    UPDATE recipe_stats
    SET save_count = save_count + 1
    WHERE recipe_id = NEW.recipe_id;
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `user_id` varchar(10) NOT NULL,
  `username` varchar(25) NOT NULL,
  `firstname` varchar(25) DEFAULT NULL,
  `lastname` varchar(25) DEFAULT NULL,
  `email` varchar(25) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(5) NOT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `date_of_birth` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`user_id`, `username`, `firstname`, `lastname`, `email`, `password`, `role`, `avatar_url`, `date_of_birth`) VALUES
('ID00000001', 'alice', 'Hai', 'Yen', 'alice@example.com', '$2a$10$oRHjJ0B7K9bngqUB455IcOKM903.VVZggyFB0kMwU6gKg66xyYp2W', 'USER', '/avatars/alice.jpg', '2006-10-17 17:00:00'),
('ID00000002', 'bob', 'Bob', 'Trần', 'bob@example.com', 'password2', 'USER', '/avatars/bob.jpg', '1985-05-19 17:00:00'),
('ID00000003', 'charlie', 'Charlie', 'Lê', 'charlie@example.com', 'password3', 'ADMIN', '/avatars/charlie.jpg', '1992-07-29 17:00:00'),
('ID00000004', 'diana', 'Diana', 'Phạm', 'diana@example.com', 'password4', 'USER', '/avatars/diana.jpg', '1993-11-04 17:00:00'),
('ID00000005', 'edward', 'Edward', 'Võ', 'edward@example.com', 'password5', 'USER', '/avatars/edward.jpg', '1988-03-11 17:00:00');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`),
  ADD UNIQUE KEY `category_name` (`category_name`);

--
-- Chỉ mục cho bảng `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `fk_recipe_comment` (`recipe_id`),
  ADD KEY `fk_user_comment` (`user_id`);

--
-- Chỉ mục cho bảng `follows`
--
ALTER TABLE `follows`
  ADD PRIMARY KEY (`follow_id`),
  ADD UNIQUE KEY `unique_follow` (`follower_id`,`following_id`),
  ADD KEY `fk_following` (`following_id`);

--
-- Chỉ mục cho bảng `id_seq`
--
ALTER TABLE `id_seq`
  ADD UNIQUE KEY `id` (`id`);

--
-- Chỉ mục cho bảng `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`like_id`),
  ADD KEY `fk_recipe` (`recipe_id`),
  ADD KEY `fk_user` (`user_id`);

--
-- Chỉ mục cho bảng `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`media_id`),
  ADD KEY `fk_media_recipe` (`recipe_id`);

--
-- Chỉ mục cho bảng `rates`
--
ALTER TABLE `rates`
  ADD PRIMARY KEY (`rate_id`),
  ADD KEY `fk_rate_recipe` (`recipe_id`),
  ADD KEY `fk_rate_user` (`user_id`);

--
-- Chỉ mục cho bảng `recipes`
--
ALTER TABLE `recipes`
  ADD PRIMARY KEY (`recipe_id`),
  ADD KEY `fk_author` (`author_id`),
  ADD KEY `fk_recipe_category` (`category_id`);

--
-- Chỉ mục cho bảng `recipe_stats`
--
ALTER TABLE `recipe_stats`
  ADD PRIMARY KEY (`recipe_id`);

--
-- Chỉ mục cho bảng `saved`
--
ALTER TABLE `saved`
  ADD PRIMARY KEY (`save_id`),
  ADD KEY `fk_save_recipe` (`recipe_id`),
  ADD KEY `fk_save_user` (`user_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `fk_recipe_comment` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_comment` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `follows`
--
ALTER TABLE `follows`
  ADD CONSTRAINT `fk_follower` FOREIGN KEY (`follower_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_following` FOREIGN KEY (`following_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `likes`
--
ALTER TABLE `likes`
  ADD CONSTRAINT `fk_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `media`
--
ALTER TABLE `media`
  ADD CONSTRAINT `fk_media_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `rates`
--
ALTER TABLE `rates`
  ADD CONSTRAINT `fk_rate_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_rate_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `recipes`
--
ALTER TABLE `recipes`
  ADD CONSTRAINT `fk_author` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_recipe_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `saved`
--
ALTER TABLE `saved`
  ADD CONSTRAINT `fk_save_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_save_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;