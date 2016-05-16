INSERT INTO CATEGORY (TITLE, DESCRIPTION) VALUES
('Digital', 'Digital records (aka video, audio, books etc)'),
('Movie', 'Some video content made by Hollywood people or their guys with camera'),
('Table', 'One of the most useful type of the furniture'),
('Аксессуары', 'Гарнитуры, зарядные устройства, наушники и т.д.');

INSERT INTO ITEM (TITLE, DESCRIPTION, WEIGHT) VALUES
  ('Sennheiser OCX 686i Sports', 'Good sport headphones with garniture for that who know the price of good sound', 0.125);
INSERT INTO ITEM (TITLE, DESCRIPTION, WEIGHT, WIDtH, HEIGHT) VALUES
  ('Dinning table', 'Простой, но комфортабельный обеденный стол для семьи из 6-8 человек', 5.6, 1.46, 0.75);
INSERT INTO ITEM (TITLE, DESCRIPTION) VALUES
  ('Gladiator, digital copy', 'Digital copy of Riddley Scott movie');

INSERT INTO AUCTION_CLIENT (LOGIN, NAME, LAST_NAME, DELIVERY_ADDRESS, CONTACT_PHONE) VALUES
('Odin', 'John', 'Smith', 'USA, California, Los Angeles 16/45', '1234567890'),
('Loki', 'Ivan', 'Ivanov', 'Ukraine, Kyiv, Hreshatik 56/2a', '380951234567'),
('Spiderman', 'Николай', null, 'Украина, Крым, мыс Айя', '380971208899');

INSERT INTO LOT (ITEM_ID, OWNER_ID, START_PRICE, CURRENT_PRICE) VALUES
(1, 1, 9.99, 9.99);

INSERT INTO ITEM_CATEGORY (ITEM_ID, CATEGORY_ID) VALUES
(3, 1),
(3, 2),
(1, 4);
