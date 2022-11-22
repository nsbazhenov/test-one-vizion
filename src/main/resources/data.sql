DROP TABLE IF EXISTS BOOK;

CREATE TABLE BOOK
(
    ID                      LONG AUTO_INCREMENT,
    TITLE                   VARCHAR(155) NOT NULL,
    AUTHOR                  VARCHAR(155) NOT NULL,
    DESCRIPTION             VARCHAR(155),
    CONSTRAINT BOOK_PK PRIMARY KEY (ID)
);

INSERT INTO BOOK (title, author, description)
VALUES ('Crime and Punishment', 'F. Dostoevsky', null);

INSERT INTO BOOK (title, author, description)
VALUES ('Anna Karenina', 'L. Tolstoy', null);

INSERT INTO BOOK (title, author, description)
VALUES ('The Brothers Karamazov', 'F. Dostoevsky', null);

INSERT INTO BOOK (title, author, description)
VALUES ('War and Peace', 'L. Tolstoy', null);

INSERT INTO BOOK (title, author, description)
VALUES ('Dead Souls', 'N. Gogol', null);