INSERT INTO authors(author_name) VALUES ('testAuthor');


INSERT INTO genres(genre_name) VALUES ('Comedy');
INSERT INTO genres(genre_name) VALUES ('Drama');
INSERT INTO genres(genre_name) VALUES ('Poem');

INSERT INTO books(book_name, genre_id)
  VALUES ('testBook1', (SELECT id FROM genres WHERE genre_name = 'Drama'));
INSERT INTO books(book_name, genre_id)
  VALUES ('testBook2', (SELECT id FROM genres WHERE genre_name = 'Comedy'));
INSERT INTO books(book_name, genre_id)
  VALUES ('testBook3', (SELECT id FROM genres WHERE genre_name = 'Comedy'));

INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE author_name = 'testAuthor'),
  (SELECT id FROM books WHERE book_name = 'testBook1')
);
INSERT INTO authors_books(author_id, book_id) VALUES (
  (SELECT id FROM authors WHERE author_name = 'testAuthor'),
  (SELECT id FROM books WHERE book_name = 'testBook2')
);