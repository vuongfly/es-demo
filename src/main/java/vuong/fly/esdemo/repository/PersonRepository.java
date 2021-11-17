package vuong.fly.esdemo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import vuong.fly.esdemo.builder.PersonQueryBuilder;
import vuong.fly.esdemo.document.Person;
import vuong.fly.esdemo.helper.Indices;

import java.io.IOException;
import java.util.List;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {

    List<Person> findByName(String name);

    List<Person> findByAge(Integer age);

    List<Person> findByAgeBetween(Integer ageMax, Integer ageMin);

    default Object findByAgeWithQueryBuilder(int from, int to) throws IOException {
        PersonQueryBuilder queryBuilder = new PersonQueryBuilder();
        return queryBuilder.executeQueryBuilder(Indices.PERSON_INDEX, queryBuilder.findByAge(from, to));
    }
}
