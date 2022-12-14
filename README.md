API for working with books.
======
------
+ Using IDE
  + Clean &#8594; Package &#8594; Working through the IDE;
  + Command line &#8594; mvnw clean install &#8594; java -jar test-one-vizion-1.0.jar;
------
+ Endpoint returns a list of all books that are contained in the table book, sorted in reverse alphabetical order of the 
  book.title column value: 

GET: http://localhost:808/api/books/all-with-author-sorting
+ Endpoint to add a new book to the book table: 

POST: http://localhost:808/api/books/save

```javascript
    {"title" : "title", "author" : "author", "description" : "description"}
```
+ Endpoint returning a list of all books grouped by author book(book.author): 

GET: http://localhost:808/api/books/all-grouped-by-author
+ Endpoint that takes a symbol as a parameter and returns a list of 10 authors whose book titles contain that symbol 
  the most times, together with the number of occurrences of this symbol in all book titles of the author. 
  The case of the symbol does not matter. Authors whose book titles do not contain the symbol should not must be present in the returned value:

GET: http://localhost:808/api/books/including-symbol-by-title??symbol=a