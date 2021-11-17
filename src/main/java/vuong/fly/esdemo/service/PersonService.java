package vuong.fly.esdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuong.fly.esdemo.document.Person;
import vuong.fly.esdemo.repository.PersonRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {

        this.repository = repository;
    }

    public void save(final Person person) {

        repository.save(person);
    }

    public Person findById(final String id) {

        return repository.findById(id).orElse(null);
    }

    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        repository.findAll().forEach(persons::add);
        return persons;
    }

    public List<Person> findByAge(int age) {

        return repository.findByAge(age);
    }

    public List<Person> findByName(String name) {

        return repository.findByName(name);
    }

    public Object findByAgeWithQueryBuilder(int from, int to) throws IOException {

        return repository.findByAgeWithQueryBuilder(from, to);
    }

}
