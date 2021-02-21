-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 21 Feb 2021 pada 16.52
-- Versi server: 10.4.17-MariaDB
-- Versi PHP: 7.3.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `helpdesk`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_penanganan`
--

CREATE TABLE `detail_penanganan` (
  `idDetailPen` varchar(100) NOT NULL,
  `idPenanganan` varchar(100) NOT NULL,
  `idTiket` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_penanganan`
--

INSERT INTO `detail_penanganan` (`idDetailPen`, `idPenanganan`, `idTiket`) VALUES
('832fa054-5984-40f4-b20b-200d7a129bc5', 'd9d98316-ef30-4380-8ebb-697a6afb967b', 'TKT0001');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penanganan`
--

CREATE TABLE `penanganan` (
  `idPenanganan` varchar(100) NOT NULL,
  `idTeknisi` varchar(100) NOT NULL,
  `createdDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penanganan`
--

INSERT INTO `penanganan` (`idPenanganan`, `idTeknisi`, `createdDate`) VALUES
('508bc118-bb02-4bd5-865f-6a5e501214fb', '8a6ba089-3566-43e6-911d-b0aa9f80d52a', '2021-02-21'),
('d9d98316-ef30-4380-8ebb-697a6afb967b', 'f978bbfe-914e-4c70-96c6-fe9b760f3896', '2021-02-20'),
('e323378c-11ca-423c-b062-de568f3fe894', '66fd4a1d-787d-47d7-83b2-aa264a129d96', '2021-02-21');

-- --------------------------------------------------------

--
-- Struktur dari tabel `teknisi`
--

CREATE TABLE `teknisi` (
  `idTeknisi` varchar(100) NOT NULL,
  `idUser` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `teknisi`
--

INSERT INTO `teknisi` (`idTeknisi`, `idUser`) VALUES
('f978bbfe-914e-4c70-96c6-fe9b760f3896', 'USR0005'),
('8a6ba089-3566-43e6-911d-b0aa9f80d52a', 'USR0008'),
('66fd4a1d-787d-47d7-83b2-aa264a129d96', 'USR0009');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tiket_helpdesk`
--

CREATE TABLE `tiket_helpdesk` (
  `idTiket` varchar(100) NOT NULL,
  `problemDesc` varchar(250) NOT NULL,
  `status` enum('Pending','In Action','Solved','Not Solved') NOT NULL,
  `idUser` varchar(100) NOT NULL,
  `createdDate` date NOT NULL,
  `updatedDate` date DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tiket_helpdesk`
--

INSERT INTO `tiket_helpdesk` (`idTiket`, `problemDesc`, `status`, `idUser`, `createdDate`, `updatedDate`) VALUES
('TKT0001', 'Tidak dapat membuka tutup botol', 'In Action', 'USR0004', '2021-02-20', '2021-02-21'),
('TKT0002', 'Tidak dapat membuka pintu', 'Pending', 'USR0004', '2021-02-20', NULL),
('TKT0004', 'Tidak dapat mengikat tali sepatu', 'Pending', 'USR0002', '2021-02-20', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `idUser` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(250) NOT NULL,
  `role` enum('Admin','User','Teknisi') NOT NULL,
  `nama` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`idUser`, `username`, `password`, `role`, `nama`) VALUES
('USR0002', 'useramal', '$2a$10$Ft8hEtLd1ZrhQiyA5.7rO.wwVx8GgJIk/FjVOM5VtwhXN1mKJ0tUG', 'User', 'Amal'),
('USR0003', 'user2', '$2a$10$/W2BmGSOFVzhPtBsw1D/w.2dWEJNCTtuVBA4JD4sI6GlWbarmbBLK', 'User', 'user2'),
('USR0004', 'user3', '$2a$10$WI1g5Ur8RBAlaxIOkoxLoOwG5sjVx1gmBAlQFNLqJuXbiYtI10v4K', 'User', 'user3'),
('USR0005', 'teknisi14', '$2a$10$72Mg8SHW/n2KoEFZL7urL.gzOucxTnxfJkJqwuzxwfjwceKdwZmtC', 'Teknisi', 'Agung'),
('USR0006', 'admins1', '$2a$10$z9.9/Ohnh4esGAq0yssbKerdzF0TD3xpjxEvE.XSEcTnCkSC/jrG.', 'Admin', 'Silo Mardadi'),
('USR0007', 'teknisi16', '$2a$10$p0FRvYrFU2PMSlSZuXXo1ecTBtD83xbsZdIq2anUudFyyV6zOiBHq', 'Teknisi', 'Silo Mardadi1'),
('USR0008', 'teknisi', '$2a$10$BnE1iwzmwkIW.JEUjXm4UOtNMlzjmAQax3PNR6/02Lcx/fYKtvXXK', 'Teknisi', 'Mang Udin'),
('USR0009', 'alif', '$2a$10$2NIivNTiy4q3CVEuIbDep.RBAdR8ao4Ba7BgOOonISxARbzMLWrhu', 'Teknisi', 'Alif');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `detail_penanganan`
--
ALTER TABLE `detail_penanganan`
  ADD PRIMARY KEY (`idDetailPen`),
  ADD KEY `IdTiket` (`idTiket`),
  ADD KEY `idPenanganan` (`idPenanganan`);

--
-- Indeks untuk tabel `penanganan`
--
ALTER TABLE `penanganan`
  ADD PRIMARY KEY (`idPenanganan`),
  ADD KEY `idTeknisi` (`idTeknisi`);

--
-- Indeks untuk tabel `teknisi`
--
ALTER TABLE `teknisi`
  ADD PRIMARY KEY (`idTeknisi`),
  ADD KEY `idUser` (`idUser`);

--
-- Indeks untuk tabel `tiket_helpdesk`
--
ALTER TABLE `tiket_helpdesk`
  ADD PRIMARY KEY (`idTiket`),
  ADD KEY `idUser` (`idUser`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_penanganan`
--
ALTER TABLE `detail_penanganan`
  ADD CONSTRAINT `detail_penanganan_ibfk_1` FOREIGN KEY (`IdTiket`) REFERENCES `tiket_helpdesk` (`idTiket`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_penanganan_ibfk_2` FOREIGN KEY (`idPenanganan`) REFERENCES `penanganan` (`idPenanganan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_penanganan_ibfk_3` FOREIGN KEY (`idTiket`) REFERENCES `tiket_helpdesk` (`idTiket`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `penanganan`
--
ALTER TABLE `penanganan`
  ADD CONSTRAINT `penanganan_ibfk_1` FOREIGN KEY (`idTeknisi`) REFERENCES `teknisi` (`idTeknisi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `teknisi`
--
ALTER TABLE `teknisi`
  ADD CONSTRAINT `teknisi_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tiket_helpdesk`
--
ALTER TABLE `tiket_helpdesk`
  ADD CONSTRAINT `tiket_helpdesk_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
