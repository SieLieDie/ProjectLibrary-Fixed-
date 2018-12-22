CREATE SCHEMA `library` DEFAULT CHARACTER SET utf8 ;
use `library`;
CREATE TABLE `book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(50) NOT NULL,
  `in_stoke` int(3) unsigned NOT NULL,
  `description` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
);
CREATE TABLE `interface_lang` (
  `lang_id` int(11) NOT NULL AUTO_INCREMENT,
  `lang_name` varchar(10) NOT NULL,
  `lang_local` varchar(8) NOT NULL,
  PRIMARY KEY (`lang_id`)
);
CREATE TABLE `user_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(15) NOT NULL,
  PRIMARY KEY (`role_id`)
);
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(10) NOT NULL,
  `password` varchar(70) NOT NULL,
  `role_id` int(1) NOT NULL,
  `first_name` varchar(15) NOT NULL,
  `second_name` varchar(15) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name_UNIQUE` (`login`)
);
CREATE TABLE `book_author` (
  `author_id` int(11) NOT NULL AUTO_INCREMENT,
  `author_first_name` varchar(20) NOT NULL,
  `author_second_name` varchar(20) NOT NULL,
  `author_lang_id` int(11) NOT NULL,
  `author_core_id` int(11) NOT NULL,
  PRIMARY KEY (`author_id`,`author_lang_id`,`author_core_id`)
);
CREATE TABLE `book_author_core` (
  `core_id` int(11) NOT NULL AUTO_INCREMENT,
  `core_first_name` varchar(20) NOT NULL,
  `core_second_name` varchar(20) NOT NULL,
  PRIMARY KEY (`core_id`)
);
CREATE TABLE `book_bounds_author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`book_id`,`author_id`),
  KEY `bound_book_idx` (`book_id`),
  KEY `bound_author_idx` (`author_id`)
); 
CREATE TABLE `book_genre` (
  `genre_id` int(11) NOT NULL AUTO_INCREMENT,
  `genre_name` varchar(15) NOT NULL,
  `genre_lang_id` int(11) NOT NULL,
  `genre_core_id` int(11) NOT NULL,
  PRIMARY KEY (`genre_id`,`genre_lang_id`,`genre_core_id`)
);
CREATE TABLE `book_bounds_genre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`book_id`,`genre_id`)
); 
CREATE TABLE `book_genre_core` (
  `core_id` int(11) NOT NULL AUTO_INCREMENT,
  `core_name` varchar(15) NOT NULL,
  PRIMARY KEY (`core_id`)
);
CREATE TABLE `monitored` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`user_id`,`book_id`)
);

ALTER TABLE `library`.`book_author` ADD CONSTRAINT `book_author_fk0` FOREIGN KEY (`author_core_id`) REFERENCES `book_author_core`(`core_id`);
ALTER TABLE `library`.`book_author` ADD CONSTRAINT `book_author_fk1` FOREIGN KEY (`author_lang_id`) REFERENCES `interface_lang`(`lang_id`);
ALTER TABLE `library`.`book_bounds_author` ADD CONSTRAINT `book_bounds_author_fk0` FOREIGN KEY (`author_id`) REFERENCES `book_author`(`author_id`);
ALTER TABLE `library`.`book_bounds_author` ADD CONSTRAINT `book_bounds_author_fk1` FOREIGN KEY (`book_id`) REFERENCES `book`(`book_id`);
ALTER TABLE `library`.`book_bounds_genre` ADD CONSTRAINT `book_bounds_genre_fk0` FOREIGN KEY (`book_id`) REFERENCES `book`(`book_id`);
ALTER TABLE `library`.`book_bounds_genre` ADD CONSTRAINT `book_bounds_genre_fk1` FOREIGN KEY (`genre_id`) REFERENCES `book_genre`(`genre_id`);
ALTER TABLE `library`.`book_genre` ADD CONSTRAINT `book_genre_fk0` FOREIGN KEY (`genre_lang_id`) REFERENCES `interface_lang`(`lang_id`);
ALTER TABLE `library`.`book_genre` ADD CONSTRAINT `book_genre_fk1` FOREIGN KEY (`genre_core_id`) REFERENCES `book_genre_core`(`core_id`);
ALTER TABLE `library`.`monitored` ADD CONSTRAINT `monitored_fk0` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);
ALTER TABLE `library`.`monitored` ADD CONSTRAINT `monitored_fk1` FOREIGN KEY (`book_id`) REFERENCES `book`(`book_id`);
ALTER TABLE `library`.`user` ADD CONSTRAINT `user_fk0` FOREIGN KEY (`role_id`) REFERENCES `user_role`(`role_id`);
ALTER TABLE 'library'.'book' CHANGE COLUMN 'in_stoke' 'in_stock' INT(3) UNSIGNED NOT NULL;

INSERT INTO `library`.`user_role` (`role_name`) VALUES ('admin');
INSERT INTO `library`.`user_role` (`role_name`) VALUES ('user');

INSERT INTO `library`.`user` (`login`, `password`, `role_id`, `first_name`, `second_name`) VALUES ('ivan18', '$2a$12$Ir7.p7HyTTZYAAfWS5vEE.ZLfB9BjQawC7J/MeoFQ/l68vn4Bc7ym', '1', 'Ivan', 'Sergeev');
INSERT INTO `library`.`user` (`login`, `password`, `role_id`, `first_name`, `second_name`) VALUES ('vasia21', '$2a$12$s.6qqQjs6VIpwhwQIcdvGuHDLCkk18x5Zt/pg0/FZmofGS/B0vwgi', '2', 'Vasia', 'Ydin');

INSERT INTO `library`.`book` (`book_name`, `in_stoke`, `description`) VALUES ('Alice’s Adventures in Wonderland', '1', 'Alice\'s Adventures in Wonderland is a popular book by Lewis Carroll. Read Alice\'s Adventures in Wonderland, free online version of the book by Lewis Carroll, on ReadCentral.com. ');
INSERT INTO `library`.`book` (`book_name`, `in_stoke`, `description`) VALUES ('The Little Prince', '0', 'The Little Prince, adapted for children and illustrated with the magnificent original images from the film. A book full of tenderness, designed to help youngsters discover the magic of the universal masterpiece by Saint-Exupery.');
INSERT INTO `library`.`book` (`book_name`, `in_stoke`, `description`) VALUES ('1984', '4', 'The spread of the military dictatorship in the 20th century could not escape the attentive glance of writers who sensitively recorded the slightest fluctuations in public opinion. Many writers occupied some or other sides of the barricades, without moving away from the political realities of their time.');
INSERT INTO `library`.`book` (`book_name`, `in_stoke`, `description`) VALUES ('Gardener\'s Song', '2', 'We offer an annotation, description, summary or foreword to the reading (depends on what the author of the book \"The Song of the Gardener\" himself wrote). If you did not find the necessary information about the book - write in the comments, we will try to find it.');
INSERT INTO `library`.`book` (`book_name`, `in_stoke`, `description`) VALUES ('Barnyard', '3', 'I understand that I appeal to readers quite unknown to me ... And first of all I decided to say a little about myself and about how my political convictions formed.');

INSERT INTO `library`.`interface_lang` (`lang_name`, `lang_local`) VALUES ('EN', 'en');
INSERT INTO `library`.`interface_lang` (`lang_name`, `lang_local`) VALUES ('RU', 'ru');

INSERT INTO `library`.`book_author_core` (`core_first_name`, `core_second_name`) VALUES ('Lewis', 'Carroll');
INSERT INTO `library`.`book_author_core` (`core_first_name`, `core_second_name`) VALUES ('Antoine', 'de Saint-Exupery');
INSERT INTO `library`.`book_author_core` (`core_first_name`, `core_second_name`) VALUES ('George', 'Orwell');

INSERT INTO `library`.`book_author` (`author_first_name`, `author_second_name`, `author_lang_id`, `author_core_id`) VALUES ('Lewis', 'Carroll', '1', '1');
INSERT INTO `library`.`book_author` (`author_first_name`, `author_second_name`, `author_lang_id`, `author_core_id`) VALUES ('Antoine', 'de Saint-Exupery', '1', '2');
INSERT INTO `library`.`book_author` (`author_first_name`, `author_second_name`, `author_lang_id`, `author_core_id`) VALUES ('George', 'Orwell', '1', '3');
INSERT INTO `library`.`book_author` (`author_first_name`, `author_second_name`, `author_lang_id`, `author_core_id`) VALUES ('Льюис', 'Кэрролл', '2', '1');
INSERT INTO `library`.`book_author` (`author_first_name`, `author_second_name`, `author_lang_id`, `author_core_id`) VALUES ('Антуан', 'де Сент-Экзюпери', '2', '2');
INSERT INTO `library`.`book_author` (`author_first_name`, `author_second_name`, `author_lang_id`, `author_core_id`) VALUES ('Джордж', 'Оруэлл', '2', '3');

INSERT INTO `library`.`book_genre_core` (`core_name`) VALUES ('Fable');
INSERT INTO `library`.`book_genre_core` (`core_name`) VALUES ('Dystopia');

INSERT INTO `library`.`book_genre` (`genre_name`, `genre_lang_id`, `genre_core_id`) VALUES ('Fable', '1', '1');
INSERT INTO `library`.`book_genre` (`genre_name`, `genre_lang_id`, `genre_core_id`) VALUES ('Dystopia', '1', '2');
INSERT INTO `library`.`book_genre` (`genre_name`, `genre_lang_id`, `genre_core_id`) VALUES ('Басня', '2', '1');
INSERT INTO `library`.`book_genre` (`genre_name`, `genre_lang_id`, `genre_core_id`) VALUES ('Антиутопия', '2', '2');

INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('1', '1');
INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('1', '4');
INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('2', '2');
INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('2', '5');
INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('3', '3');
INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('3', '6');
INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('4', '1');
INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('4', '4');
INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('5', '3');
INSERT INTO `library`.`book_bounds_author` (`book_id`, `author_id`) VALUES ('5', '6');

INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('1', '1');
INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('1', '3');
INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('2', '1');
INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('2', '3');
INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('3', '2');
INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('3', '4');
INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('4', '2');
INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('4', '4');
INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('5', '1');
INSERT INTO `library`.`book_bounds_genre` (`book_id`, `genre_id`) VALUES ('5', '3');
