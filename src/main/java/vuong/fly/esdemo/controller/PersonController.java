package vuong.fly.esdemo.controller;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuong.fly.esdemo.builder.PersonQueryBuilder;
import vuong.fly.esdemo.document.Person;
import vuong.fly.esdemo.service.PersonService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    PersonQueryBuilder personQueryBuilder;

    Faker faker = new Faker();

    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Person> findAll() {
        return service.findAll();
    }

    @PostMapping
    public void save(@RequestBody final Person person) {
        service.save(person);
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable final String id) {
        return service.findById(id);
    }

    @PostMapping("/generate-people")
    public void generatePeople() {
        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            p.setId(faker.idNumber().valid());
            p.setName(faker.name().name());
            p.setAge(faker.number().numberBetween(18, 81));
            service.save(p);
        }
    }

    @GetMapping("/filter")
    public List<Person> findByAge(@RequestBody Person person) {
        return service.findByAge(person.getAge());
    }

    @GetMapping("/find-by-name")
    public List<Person> findByName(@RequestBody Person person) {
        return service.findByName(person.getName());
    }

    @GetMapping("/find-by-age")
    public Object findByAgeWithQueryBuilder() throws IOException {
        return  service.findByAgeWithQueryBuilder(5, 30);
    }

}
