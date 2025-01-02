package endpoint;

import entity.Example;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import service.ExampleService;

import java.util.List;


@ApplicationScoped
@Path("/QrCodePayment")
public class QrCodePaymentEndpoingt {


    @Inject
    ExampleService exampleService;

    @GET
    public List<Example> getAllExamples() {
        return exampleService.getAllExamples();
    }

    @POST
    public Example postExample(Example example) {

        example = exampleService.createExample(example.getVar1(), example.getVar2());

        return example;
    }
}
