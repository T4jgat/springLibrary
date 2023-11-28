package kz.t4jgat.library.services;


import kz.t4jgat.library.models.Book;
import kz.t4jgat.library.models.Person;
import kz.t4jgat.library.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void updateOwner(Person person, int id) {
        Book book = booksRepository.findById(id).orElse(null);
        assert book != null;
        book.setTakenAt(new Date());
        book.setId(id);
        book.setOwner(person);
        booksRepository.save(book);
    }

    @Transactional
    public void releaseBook(int id) {
        Book book = booksRepository.findById(id).orElse(null);
        assert book != null;
        book.setOwner(null);
        book.setTakenAt(null);
        booksRepository.save(book);
    }

}
