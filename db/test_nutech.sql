CREATE DATABASE test_nutech;

USE test_nutech;


CREATE TABLE tb_user (
  user_id int NOT NULL AUTO_INCREMENT,
  balance decimal(19,2) ,
  email varchar(255) ,
  first_name varchar(255) ,
  last_name varchar(255) ,
  password varchar(255) ,
  username varchar(255) ,
  profile_image varchar(255) ,
  PRIMARY KEY (user_id)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE tb_banner (
  banner_id int NOT NULL AUTO_INCREMENT,
  banner_image varchar(255) ,
  banner_name varchar(255) ,
  description varchar(255) ,
  PRIMARY KEY (banner_id)
) 

CREATE TABLE tb_service (
  service_id int NOT NULL AUTO_INCREMENT,
  service_code varchar(255) ,
  service_icon varchar(255) ,
  service_name varchar(255) ,
  service_tarif decimal(19,2) ,
  PRIMARY KEY (service_id)
) 

CREATE TABLE tb_transaction (
  id int NOT NULL AUTO_INCREMENT,
  created_on datetime ,
  description varchar(255) ,
  invoice_number varchar(255) ,
  service_code varchar(255) ,
  service_name varchar(255) ,
  total_amount decimal(19,2) ,
  transaction_type varchar(255) ,
  user_id int ,
  PRIMARY KEY (id)
) 


INSERT INTO tb_banner (banner_name, banner_image, description) VALUES
('Banner 1', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
('Banner 2', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
('Banner 3', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
('Banner 4', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
('Banner 5', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet'),
('Banner 6', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet');

INSERT INTO tb_service (service_code, service_name, service_icon, service_tariff) VALUES
('PAJAK', 'Pajak PBB', 'https://nutech-integrasi.app/dummy.jpg', 40000),
('PLN', 'Listrik', 'https://nutech-integrasi.app/dummy.jpg', 10000),
('PDAM', 'PDAM Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 40000),
('PULSA', 'Pulsa', 'https://nutech-integrasi.app/dummy.jpg', 40000),
('PGN', 'PGN Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000),
('MUSIK', 'Musik Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000),
('TV', 'TV Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000),
('PAKET_DATA', 'Paket data', 'https://nutech-integrasi.app/dummy.jpg', 50000),
('VOUCHER_GAME', 'Voucher Game', 'https://nutech-integrasi.app/dummy.jpg', 100000),
('VOUCHER_MAKANAN', 'Voucher Makanan', 'https://nutech-integrasi.app/dummy.jpg', 100000),
('QURBAN', 'Qurban', 'https://nutech-integrasi.app/dummy.jpg', 200000),
('ZAKAT', 'Zakat', 'https://nutech-integrasi.app/dummy.jpg', 300000);



