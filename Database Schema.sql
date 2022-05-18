-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 18, 2022 at 09:29 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mtdl`
--

-- --------------------------------------------------------

--
-- Table structure for table `covid_cases`
--

CREATE TABLE `covid_cases` (
  `Date_Updated` varchar(10) NOT NULL,
  `Country_Name` varchar(50) NOT NULL,
  `Total_Confirmed_Cases` varchar(20) DEFAULT NULL,
  `Active_Cases` varchar(20) DEFAULT NULL,
  `Cured_Cases` varchar(20) DEFAULT NULL,
  `Total_Deaths` varchar(20) DEFAULT NULL,
  `PCR_Test_Required` varchar(1000) DEFAULT NULL,
  `Mobile_Tracing_App` varchar(1000) DEFAULT NULL,
  `quarantine` varchar(1000) DEFAULT NULL,
  `mask` varchar(1000) DEFAULT NULL,
  `vaccine` varchar(1000) DEFAULT NULL,
  `curfew` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `covid_cases`
--

INSERT INTO `covid_cases` (`Date_Updated`, `Country_Name`, `Total_Confirmed_Cases`, `Active_Cases`, `Cured_Cases`, `Total_Deaths`, `PCR_Test_Required`, `Mobile_Tracing_App`, `quarantine`, `mask`, `vaccine`, `curfew`) VALUES
('2022-05-17', 'Albania', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown'),
('2022-05-17', 'Andorra', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Austria', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Belarus', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Belgium', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Bosnia & Herzegovina', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Bulgaria', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Croatia', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Czechia', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Denmark', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Estonia', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Finland', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'France', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Germany', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Greece', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Hungary', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Iceland', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Ireland', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Italy', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Latvia', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Liechtenstein', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Lithuania', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Luxembourg', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Malta', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Moldova', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Monaco', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Montenegro', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Netherlands', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'North Macedonia', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Norway', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Poland', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Portugal', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Romania', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Russia', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'San Marino', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Serbia', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Slovakia', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Slovenia', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Spain', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Sweden', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-18', 'Switzerland', '2751644', '123/56', 'nush boss', 'rip', 'false', 'no', 'no', 'true', 'yes', 'unknown'),
('2022-05-17', 'Ukraine', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'United Kingdom', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown'),
('2022-05-17', 'Vatican City', '275177', 'unknown', 'unknown', '3496', 'sssssssssssssssssssss', '', 'Some travellers', '', '', 'unknown');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `Email` varchar(100) NOT NULL,
  `Password` varchar(500) NOT NULL,
  `UserType` varchar(15) NOT NULL,
  `First_Name` varchar(150) NOT NULL,
  `Last_Name` varchar(150) NOT NULL,
  `Favorites` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`Email`, `Password`, `UserType`, `First_Name`, `Last_Name`, `Favorites`) VALUES
('admin@alin.com', 'admin', 'Administrator', 'Alin (Admin)', '', ''),
('alin@alin.com', 'alin', 'User', 'Alin', 'Stanescu', ''),
('mod@alin.com', 'moderator', 'Moderator', 'Alin (Mod)', 'Stanescu', ''),
('testConnection', 'connectionSucceeded', 'internal', 'Test', 'Connection', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `covid_cases`
--
ALTER TABLE `covid_cases`
  ADD UNIQUE KEY `Country_Name` (`Country_Name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD UNIQUE KEY `E-mail` (`Email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
