-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 10, 2022 at 09:19 PM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 8.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smartlib_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(60) NOT NULL,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `photo` varchar(200) NOT NULL,
  `created_on` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`, `firstname`, `lastname`, `photo`, `created_on`) VALUES
(1, 'admin', '$2y$10$Yq8ZXYSZtBnnLEgT5VXo9O/Ub8yQdXEfsaNX73QXuWclEY.yL0Iwi', 'admin', 'Librarian', 'images.jpg', '2018-05-03');

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `category_id` int(11) NOT NULL,
  `title` text NOT NULL,
  `author` varchar(150) NOT NULL,
  `publisher` varchar(150) NOT NULL,
  `publish_date` date NOT NULL,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `isbn`, `category_id`, `title`, `author`, `publisher`, `publish_date`, `status`) VALUES
(4, '07897519845', 5, 'C PROGRAMMING ABSOLUTE BEGINNE', 'Greg Perry', 'Que Publishing', '2013-07-24', 0),
(5, '9385676342', 1, 'Database Management Systems (DBMS)', 'Rajiv Chopra', 'S.Chand Publishing', '0000-00-00', 0),
(6, '0070411832', 5, 'Head First C: A Brain-Friendly Guide Paperback – Illustrated', 'Dawn Griffiths', 'O?Reilly', '2012-11-21', 1),
(8, '0321399528', 3, 'Computer Graphics: Principles and Practice', 'John Hughes ', 'Addison-Wesley Professional', '2013-07-18', 0),
(11, '9351761754', 2, 'PLANE TRIGONOMETRY Part-1', 'S L Loney', 'Arihant Publications', '2015-07-25', 0),
(13, '0321399522', 6, 'JavaScript & PHP', 'Basanta Chapagain', 'Addison-Wesley Professional', '2019-07-27', 0),
(14, '91856763425', 5, 'Mobile Programming I', 'Monika puyal', 'S.Chand Publishing', '1955-12-27', 1),
(15, '938567435', 7, 'Nepali Ed-5', 'Basanta Chapagain', 'Arihant Publications', '1994-12-27', 0);

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `date_borrow` date NOT NULL,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`id`, `student_id`, `book_id`, `date_borrow`, `status`) VALUES
(32, 20, 4, '2022-08-11', 1),
(33, 20, 6, '2022-08-11', 0),
(34, 20, 14, '2022-08-11', 0);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Database Management System'),
(2, 'Mathematics-I'),
(3, 'Computer Graphics'),
(5, 'C Programming'),
(6, 'Scripting Language'),
(7, 'Bussiness'),
(10, 'Computer Network');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `title` text NOT NULL,
  `code` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`id`, `title`, `code`) VALUES
(3, 'Bachelors of Information Technology', 'BIT'),
(4, 'Bachelors in Computer Application', 'BCA'),
(5, 'Computer Science and Information Technology ', 'CSIT'),
(6, 'Bachelor of Business Studies', 'BBS'),
(7, 'Bachelor in Business Application', 'BBA'),
(8, 'Bachelor in Information Technology', 'BIT'),
(10, 'Computer Science', 'CS');

-- --------------------------------------------------------

--
-- Table structure for table `returns`
--

CREATE TABLE `returns` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `date_return` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `returns`
--

INSERT INTO `returns` (`id`, `student_id`, `book_id`, `date_return`) VALUES
(12, 20, 4, '2022-08-11');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `student_id` varchar(15) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `photo` varchar(200) NOT NULL,
  `course_id` int(11) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `encrypted_password` varchar(80) DEFAULT NULL,
  `salt` varchar(10) DEFAULT NULL,
  `updated_at` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `student_id`, `firstname`, `lastname`, `photo`, `course_id`, `email`, `status`, `created_at`, `encrypted_password`, `salt`, `updated_at`) VALUES
(15, 'FMZ917236450', 'Monika', 'Phuyal', '', 4, 'monika@gmail.com', 1, '2022-08-10', '0Xwm7GMypJn33/vTxdaXZWIf//UxMWRmZmQzNzhj', '11dffd378c', NULL),
(16, 'APS081376495', 'Baban', 'Hello', '', 4, 'hello@gmail.com', 1, '2022-08-10', 'Kl9CYtAj7uR3odWW0XhdPUtddPc3YzkxNzc2N2Ey', '7c917767a2', NULL),
(17, 'VGE936401278', 'Kamana', 'Chhetri', '117-1176598_female-student-png-image-indian-college-student-png.png', 3, 'kamana@gmail.com', 1, '2022-08-10', 'VAu2ANMlxGD1resHuVyUtKhbcIcxYTE3YTQyZDYx', '1a17a42d61', NULL),
(20, 'VAX157649208', 'Tsherpa', 'Bahadur', '360_F_267632714_lVq7kUVqtGvdjFBDSseEDNCiTO6ACHSn.jpg', 6, 'rana@gmail.com', 0, '2022-08-11', 'IBXlICuGDrenCtBWEquD2pEd6cNlYThjNjk3OTNi', 'ea8c69793b', NULL),
(22, 'ZPR496728013', 'Sovit', 'Rai', '', 8, 'sovitR12@gmail.com', 1, '2022-08-11', 'SSJK3F8qpdqTbRDqS6fD1r6vjFg0ZjM2NmFjMDc2', '4f366ac076', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `returns`
--
ALTER TABLE `returns`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `borrow`
--
ALTER TABLE `borrow`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `returns`
--
ALTER TABLE `returns`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
