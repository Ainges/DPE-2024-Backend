/**
 * Start
 *
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
     * Retrieves an invoice by its ID.
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
     * Retrieves invoices by their category ID.
     *
     * @param categoryId the ID of the invoice category
     * @return a response containing the list of invoices in the specified category
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
     * Retrieves invoices by their housing object ID.
     *
     * @param housingObjectId the ID of the housing object
     * @return a response containing the list of invoices for the specified housing object
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
     * @return a response containing the created invoice
     */
    @POST
    @Transactional
    public Response createInvoice(Invoice invoice) {
        // Check if an invoice with the same externalInvoiceNumber and receiver already exists
        List<Invoice> existingInvoices = invoiceRepository.find("externalInvoiceNumber = ?1 and receiver = ?2", invoice.getExternalInvoiceNumber(), invoice.getReceiver()).list();
        if (!existingInvoices.isEmpty()) {
            return Response.status(Response.Status.CONFLICT).entity("Invoice with the same externalInvoiceNumber and receiver already exists").build();
        }
        invoiceRepository.persist(invoice);
        return Response.status(Response.Status.CREATED).entity(invoice).build();
    }

    /**
     * Updates an existing invoice.
     *
     * @param id      the ID of the invoice to update
     * @param invoice the updated invoice data
     * @return a response containing the updated invoice
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateInvoice(@PathParam("id") long id, Invoice invoice) {
        Invoice existingInvoice = invoiceRepository.findById(id);
        if (existingInvoice == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (invoice.getInvoiceDate() != null) {
            existingInvoice.setInvoiceDate(invoice.getInvoiceDate());
        }
        if (invoice.getInvoiceAmount() != 0.0) {
            existingInvoice.setInvoiceAmount(invoice.getInvoiceAmount());
        }
        if (invoice.getInvoiceCategory() != null) {
            existingInvoice.setInvoiceCategory(invoice.getInvoiceCategory());
        }
        if (invoice.getHousingObject() != null) {
            existingInvoice.setHousingObject(invoice.getHousingObject());
        }
        if (invoice.getDescription() != null) {
            existingInvoice.setDescription(invoice.getDescription());
        }
        if (invoice.getStatus() != null) {
            existingInvoice.setStatus(invoice.getStatus());
        }
        if (invoice.getReceiver() != null) {
            existingInvoice.setReceiver(invoice.getReceiver());
        }
        if (invoice.getReceiverIban() != null) {
            existingInvoice.setReceiverIban(invoice.getReceiverIban());
        }
        if (invoice.getReceiverBic() != null) {
            existingInvoice.setReceiverBic(invoice.getReceiverBic());
        }
        if (invoice.getExternalInvoiceNumber() != null) {
            existingInvoice.setExternalInvoiceNumber(invoice.getExternalInvoiceNumber());
        }
        invoiceRepository.persist(existingInvoice);
        return Response.ok(existingInvoice).build();
    }

    /**
     * Deletes an invoice by its ID.
     *
     * @param id the ID of the invoice to delete
     * @return a response indicating the result of the deletion
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
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */