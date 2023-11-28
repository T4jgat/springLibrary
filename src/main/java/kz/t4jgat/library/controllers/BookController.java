package kz.t4jgat.library.controllers;

import kz.t4jgat.library.DAO.BookDAO;
import kz.t4jgat.library.DAO.PersonDAO;
import kz.t4jgat.library.models.Book;
import kz.t4jgat.library.models.Person;
import kz.t4jgat.library.repositories.BooksRepository;
import kz.t4jgat.library.repositories.PeopleRepository;
import kz.t4jgat.library.services.BookService;
import kz.t4jgat.library.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    // ======== Fields and constructor ==============
    private BookService bookService;
    private PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    // ======== Controllers =============
    @GetMapping
    public String index(Model model, @RequestParam("page") int page,
                        @RequestParam("books_per_page") int booksPerPage) {
        List<Book> books = bookService.findAll(page, booksPerPage);
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);

        assert book != null;
        Person bookOwner = book.getOwner();


        if (bookOwner!=null) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("book") Book updateBook) {
        bookService.update(updateBook, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int bookId, @ModelAttribute("person") Person selectedPerson) {
        bookService.updateOwner(selectedPerson, bookId);
        return "redirect:/books/" + bookId;
    }
}
