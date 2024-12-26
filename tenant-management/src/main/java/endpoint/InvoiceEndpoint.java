/**
 * Start
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */

package endpoint;

import entity.Invoice;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.InvoiceRepository;

import java.util.List;

@ApplicationScoped
@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceEndpoint {

    @Inject
    InvoiceRepository invoiceRepository;

    @GET
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Invoice getInvoice(@PathParam("id") long id) {
        return invoiceRepository.findById(id);
    }

    @POST
    @Transactional
    public Response createInvoice(Invoice invoice) {
        invoiceRepository.persist(invoice);
        return Response.status(Response.Status.CREATED).entity(invoice).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateInvoice(@PathParam("id") long id, Invoice invoice) {
        Invoice existingInvoice = invoiceRepository.findById(id);
        if (existingInvoice == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingInvoice.setInvoiceDate(invoice.getInvoiceDate());
        existingInvoice.setInvoiceAmount(invoice.getInvoiceAmount());
        existingInvoice.setInvoiceCategory(invoice.getInvoiceCategory());
        existingInvoice.setHousingObject(invoice.getHousingObject());
        invoiceRepository.persist(existingInvoice);
        return Response.ok(existingInvoice).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteInvoice(@PathParam("id") long id) {
        Invoice invoice = invoiceRepository.findById(id);
        if (invoice == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        invoiceRepository.delete(invoice);
        return Response.noContent().build();
    }
}

/**
 * End
 * Primary author GitHub Copilot
 * Secondary author Moritz Baur
 */