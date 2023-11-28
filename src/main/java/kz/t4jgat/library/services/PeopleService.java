package kz.t4jgat.library.services;

import kz.t4jgat.library.models.Book;
import kz.t4jgat.library.models.Person;
import kz.t4jgat.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }


    public Person findOne(int id) {
        Person person = peopleRepository.findById(id).orElse(null);
        assert person != null;
        List<Book> books = person.getBooks();

        if (books!=null) {
            for (Book book : books)
                book.setOverdue(checkOverdue(book));
        }
        return person;
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Person updatedPerson, int id) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    boolean checkOverdue(Book book) {
        Date takenAt = book.getTakenAt();
        Date currentTimestamp = new Date();
        long timeDiffInMilliseconds = Math.abs(currentTimestamp.getTime() - takenAt.getTime());
        long timeDiffInDays = timeDiffInMilliseconds / (1000 * 60 * 60 * 24);
        return timeDiffInDays >= 10;
    }
}
