INSERT INTO authors(author_name) VALUES ('testAuthor');

INSERT INTO genres(genre_name) VALUES ('Comedy');
INSERT INTO genres(genre_name) VALUES ('Drama');
INSERT INTO genres(genre_name) VALUES ('Poem');

INSERT INTO books(book_name, genre_id)
  VALUES ('testBook1', (SELECT genre_id FROM genres WHERE genre_name = 'Drama'));
INSERT INTO books(book_name, genre_id)
  VALUES ('testBook2', (SELECT genre_id FROM genres WHERE genre_name = 'Comedy'));
INSERT INTO books(book_name, genre_id)
  VALUES ('testBook3', (SELECT genre_id FROM genres WHERE genre_name = 'Comedy'));

INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'testAuthor'),
  (SELECT book_id FROM books WHERE book_name = 'testBook1')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT author_id FROM authors WHERE author_name = 'testAuthor'),
  (SELECT book_id FROM books WHERE book_name = 'testBook2')
);
-- INSERT INTO authors_books(author_id, book_id) VALUES (
--   (SELECT author_id FROM authors WHERE author_name = 'testAuthor'),
--   (SELECT book_id FROM books WHERE book_name = 'testBook3')
-- );