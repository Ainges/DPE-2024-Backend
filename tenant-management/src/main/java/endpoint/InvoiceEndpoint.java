/**
 * Start
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */

package endpoint;

import entity.Invoice;
import entity.InvoiceCategory;
import entity.HousingObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.HousingObjectRepository;
import repository.InvoiceRepository;
import repository.InvoiceCategoryRepository;
import entity.HousingObject;

import java.util.List;

@ApplicationScoped
@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceEndpoint {

    @Inject
    InvoiceRepository invoiceRepository;

    @Inject
    InvoiceCategoryRepository invoiceCategoryRepository;

    @Inject
    HousingObjectRepository housingObjectRepository;

    /**
     * Retrieves all invoices.
     *
     * @return a list of all invoices
     */
    @GET
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.listAll();
    }

    /**
     * Retrieves a specific invoice by its ID.
     *
     * @param id the ID of the invoice
     * @return the invoice with the specified ID
     */
    @GET
    @Path("/{id}")
    public Invoice getInvoice(@PathParam("id") long id) {
        return invoiceRepository.findById(id);
    }

    /**
     * Retrieves all invoices associated with a specific category.
     *
     * @param categoryId the ID of the category
     * @return a Response containing the list of invoices for the specified category,
     * or a 404 Not Found status if the category does not exist
     */
    @GET
    @Path("/category/{categoryId}")
    public Response getInvoicesByCategory(@PathParam("categoryId") long categoryId) {
        InvoiceCategory category = invoiceCategoryRepository.findById(categoryId);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Invoice> invoices = invoiceRepository.find("invoiceCategory", category).list();
        return Response.ok(invoices).build();
    }

    /**
     * Retrieves all invoices associated with a specific housing object.
     *
     * @param housingObjectId the ID of the housing object
     * @return a Response containing the list of invoices for the specified housing object,
     * or a 404 Not Found status if the housing object does not exist
     */
    @GET
    @Path("/housing-object/{housingObjectId}")
    public Response getInvoicesByHousingObject(@PathParam("housingObjectId") long housingObjectId) {
        HousingObject housingObject = housingObjectRepository.findById(housingObjectId);
        if (housingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Invoice> invoices = invoiceRepository.find("housingObject", housingObject).list();
        return Response.ok(invoices).build();
    }

    /**
     * Creates a new invoice.
     *
     * @param invoice the invoice to create
     * @return a Response containing the created invoice
     */
    @POST
    @Transactional
    public Response createInvoice(Invoice invoice) {
        invoiceRepository.persist(invoice);
        return Response.status(Response.Status.CREATED).entity(invoice).build();
    }

    /**
     * Updates an existing invoice.
     *
     * @param id      the ID of the invoice to update
     * @param invoice the updated invoice data
     * @return a Response containing the updated invoice,
     * or a 404 Not Found status if the invoice does not exist
     */
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

    /**
     * Deletes an existing invoice.
     *
     * @param id the ID of the invoice to delete
     * @return a Response with no content if the deletion was successful,
     * or a 404 Not Found status if the invoice does not exist
     */
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
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */