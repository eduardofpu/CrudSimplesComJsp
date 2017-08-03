CREATE SCHEMA `projeto_jsp` DEFAULT CHARACTER SET latin1 ;


CREATE TABLE `projeto_jsp`.`produtos` (
  `id` INT NOT NULL auto_increment,
  `produto` VARCHAR(500) NULL,  
  PRIMARY KEY (`id`))
ENGINE = InnoDB;  