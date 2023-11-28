package kz.t4jgat.library.repositories;

import kz.t4jgat.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findBookByTitleStartingWith(String title);
}
