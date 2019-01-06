INSERT INTO authors(first_name, last_name) VALUES ('Jack', 'London');
INSERT INTO authors(first_name, last_name) VALUES ('Lev', 'Tolstoy');
INSERT INTO authors(first_name, last_name) VALUES ('Aleksandr', 'Pushkin');
INSERT INTO authors(first_name, last_name) VALUES ('Ilya', 'Ilf');
INSERT INTO authors(first_name, last_name) VALUES ('Evgeniy', 'Petrov');
INSERT INTO genres(name) VALUES ('Comedy');
INSERT INTO genres(name) VALUES ('Drama');
INSERT INTO genres(name) VALUES ('Poem');
INSERT INTO books(name, isbn, genre_id) VALUES ('Sea Wolf', '978-5-699-88580-0', (SELECT id FROM genres WHERE name = 'Drama'));
INSERT INTO books(name, isbn, genre_id) VALUES ('The hearts of three', '978-5-9910-0150-2', (SELECT id FROM genres WHERE name = 'Drama'));
INSERT INTO books(name, isbn, genre_id) VALUES ('War and peace', '978-5-4241-0693-4', (SELECT id FROM genres WHERE name = 'Drama'));
INSERT INTO books(name, isbn, genre_id) VALUES ('Anna Karenina', '978-5-17-087888-8', (SELECT id FROM genres WHERE name = 'Drama'));
INSERT INTO books(name, isbn, genre_id) VALUES ('Evgeniy Onegin', '978-5-699-81352-0', (SELECT id FROM genres WHERE name = 'Poem'));
INSERT INTO books(name, isbn, genre_id) VALUES ('12 chairs', '978-5-17-092624-4', (SELECT id FROM genres WHERE name = 'Comedy'));
INSERT INTO books(name, isbn, genre_id) VALUES ('Golden calf', '978-5-04-098872-3', (SELECT id FROM genres WHERE name = 'Comedy'));
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE last_name = 'London'),
  (SELECT id FROM books WHERE name = 'Sea Wolf')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE last_name = 'London'),
  (SELECT id FROM books WHERE name = 'The hearts of three')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE last_name = 'Tolstoy'),
  (SELECT id FROM books WHERE name = 'War and peace')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE last_name = 'Tolstoy'),
  (SELECT id FROM books WHERE name = 'Anna Karenina')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE last_name = 'Pushkin'),
  (SELECT id FROM books WHERE name = 'Evgeniy Onegin')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE last_name = 'Petrov'),
  (SELECT id FROM books WHERE name = '12 chairs')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE last_name = 'Ilf'),
  (SELECT id FROM books WHERE name = '12 chairs')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE last_name = 'Ilf'),
  (SELECT id FROM books WHERE name = 'Golden calf')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE last_name = 'Petrov'),
  (SELECT id FROM books WHERE name = 'Golden calf')
);