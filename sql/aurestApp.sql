/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `aurestApp`
--
CREATE DATABASE IF NOT EXISTS `aurestApp` DEFAULT CHARACTER SET latin1 COLLATE latin1_general_ci;
USE `aurestApp`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Altname`
--

CREATE TABLE IF NOT EXISTS `Altname` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `MitarbeiterID` int(11) NOT NULL,
  `Name` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Einstellungen`
--

CREATE TABLE IF NOT EXISTS `Einstellungen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config` varchar(255) CHARACTER SET latin1 NOT NULL,
  `wert` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Kunden`
--

CREATE TABLE IF NOT EXISTS `Kunden` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Kunde` varchar(255) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Logins`
--

CREATE TABLE IF NOT EXISTS `Logins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(100) CHARACTER SET latin1 NOT NULL,
  `Passwort` varchar(100) CHARACTER SET latin1 NOT NULL,
  `MitarbeiterID` int(11) NOT NULL,
  `Aktiv` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Mitarbeiter`
--

CREATE TABLE IF NOT EXISTS `Mitarbeiter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `Kurz` varchar(50) COLLATE latin1_general_ci NOT NULL,
  `eMail` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `TeleAktiv` tinyint(1) NOT NULL,
  `eMailAktiv` tinyint(1) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
