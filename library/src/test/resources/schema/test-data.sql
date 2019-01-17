-- INSERT INTO authors(author_name) VALUES ('Jack London');
-- INSERT INTO authors(author_name) VALUES ('Lev Tolstoy');
-- INSERT INTO authors(author_name) VALUES ('Aleksandr Pushkin');
-- INSERT INTO authors(author_name) VALUES ('Ilya Ilf');
-- INSERT INTO authors(author_name) VALUES ('Evgeniy Petrov');
INSERT INTO authors(author_name) VALUES ('testAuthor');


INSERT INTO genres(genre_name) VALUES ('Comedy');
INSERT INTO genres(genre_name) VALUES ('Drama');
INSERT INTO genres(genre_name) VALUES ('Poem');

-- INSERT INTO books(book_name, genre_id)
--   VALUES ('Sea Wolf', (SELECT id FROM genres WHERE genre_name = 'Drama'));
-- INSERT INTO books(book_name, genre_id)
--   VALUES ('The hearts of three', (SELECT id FROM genres WHERE genre_name = 'Drama'));
-- INSERT INTO books(book_name, genre_id)
--   VALUES ('War and peace', (SELECT id FROM genres WHERE genre_name = 'Drama'));
-- INSERT INTO books(book_name, genre_id)
--   VALUES ('Anna Karenina', (SELECT id FROM genres WHERE genre_name = 'Drama'));
-- INSERT INTO books(book_name, genre_id)
--   VALUES ('Evgeniy Onegin', (SELECT id FROM genres WHERE genre_name = 'Poem'));
-- INSERT INTO books(book_name, genre_id)
--   VALUES ('12 chairs', (SELECT id FROM genres WHERE genre_name = 'Comedy'));
-- INSERT INTO books(book_name, genre_id)
--   VALUES ('Golden calf', (SELECT id FROM genres WHERE genre_name = 'Comedy'));
INSERT INTO books(book_name, genre_id)
  VALUES ('testBook1', (SELECT id FROM genres WHERE genre_name = 'Drama'));
INSERT INTO books(book_name, genre_id)
  VALUES ('testBook2', (SELECT id FROM genres WHERE genre_name = 'Comedy'));
INSERT INTO books(book_name, genre_id)
  VALUES ('testBook3', (SELECT id FROM genres WHERE genre_name = 'Comedy'));

-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT id FROM authors WHERE author_name = 'Jack London'),
--   (SELECT id FROM books WHERE book_name = 'Sea Wolf')
-- );
-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT id FROM authors WHERE author_name = 'Jack London'),
--   (SELECT id FROM books WHERE book_name = 'The hearts of three')
-- );
-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT id FROM authors WHERE author_name = 'Lev Tolstoy'),
--   (SELECT id FROM books WHERE book_name = 'War and peace')
-- );
-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT id FROM authors WHERE author_name = 'Lev Tolstoy'),
--   (SELECT id FROM books WHERE book_name = 'Anna Karenina')
-- );
-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT id FROM authors WHERE author_name = 'Aleksandr Pushkin'),
--   (SELECT id FROM books WHERE book_name = 'Evgeniy Onegin')
-- );
-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT id FROM authors WHERE author_name = 'Evgeniy Petrov'),
--   (SELECT id FROM books WHERE book_name = '12 chairs')
-- );
-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT id FROM authors WHERE author_name = 'Ilya Ilf'),
--   (SELECT id FROM books WHERE book_name = '12 chairs')
-- );
-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT id FROM authors WHERE author_name = 'Ilya Ilf'),
--   (SELECT id FROM books WHERE book_name = 'Golden calf')
-- );
-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT id FROM authors WHERE author_name = 'Evgeniy Petrov'),
--   (SELECT id FROM books WHERE book_name = 'Golden calf')
-- );
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE author_name = 'testAuthor'),
  (SELECT id FROM books WHERE book_name = 'testBook1')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE author_name = 'testAuthor'),
  (SELECT id FROM books WHERE book_name = 'testBook2')
);