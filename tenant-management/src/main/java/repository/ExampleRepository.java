package repository;

import entity.Example;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ExampleRepository implements PanacheRepository<Example> {
    public Example findByVar1(String var1) {
        return find("var1", var1).firstResult();
    }

    @Transactional
    public void persistExample(Example example) {
        persist(example);
    }

    public List<Example> listAllExamples() {
        return listAll();
    }


}
