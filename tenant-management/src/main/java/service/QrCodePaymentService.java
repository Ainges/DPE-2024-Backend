package service;

import entity.Example;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import repository.ExampleRepository;

import java.util.List;

@ApplicationScoped
public class QrCodePaymentService {

    @Inject
    ExampleRepository exampleRepository;

    public Example createExample(String var1, String var2) {
        Example example = new Example(var1, var2);
        exampleRepository.persistExample(example);
        return example;


    }

    public List<Example> getAllExamples(){return exampleRepository.listAllExamples();}

}
