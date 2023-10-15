package kz.t4jgat.library.DAO;

import kz.t4jgat.library.models.Book;
import kz.t4jgat.library.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void update(Book book, int id) {
        jdbcTemplate.update("UPDATE book SET name=?, author_name=?, year=? WHERE id=?",
                book.getName(), book.getAuthor_name(), book.getYear(), id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(person_id, name, author_name, year)  values (?, ?, ?, ?)", null,
                book.getName(), book.getAuthor_name(), book.getYear());
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = person.id " +
                        "WHERE book.id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE id=?", id);
    }

    public void assign(int id, Person person) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", person.getId(), id);
    }
}
