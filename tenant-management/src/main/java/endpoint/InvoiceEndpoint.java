/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */
package endpoint;

import dto.InvoiceCreateDto;
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
import service.InvoiceService;

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

    @Inject
    InvoiceService invoiceService;

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
     * @param housingObjectId            the ID of the housing object
     * @param relevantForAnnualStatement optional query parameter to filter invoices relevant for the annual statement
     * @return a response containing the list of invoices for the specified housing object
     */
    @GET
    @Path("/housing-object/{housingObjectId}")
    public Response getInvoicesByHousingObject(@PathParam("housingObjectId") long housingObjectId, @QueryParam("relevantForAnnualStatement") Boolean relevantForAnnualStatement) {
        HousingObject housingObject = housingObjectRepository.findById(housingObjectId);
        if (housingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Invoice> invoices;
        if (relevantForAnnualStatement != null) {
            invoices = invoiceRepository.find("housingObject = ?1 and relevantForAnnualStatement = ?2", housingObject, relevantForAnnualStatement).list();
        } else {
            invoices = invoiceRepository.find("housingObject", housingObject).list();
        }
        return Response.ok(invoices).build();
    }

    /**
     * Creates a new invoice.
     *
     * @param invoiceCreateDto the data for the new invoice
     * @return a response containing the created invoice
     */
    @POST
    @Transactional
    public Response createInvoice(InvoiceCreateDto invoiceCreateDto) {
        Invoice invoice;
        try {
            invoice = invoiceService.createInvoice(invoiceCreateDto);
            return Response.status(Response.Status.CREATED).entity(invoice).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Updates an existing invoice.
     *
     * @param id      the ID of the invoice to update
     * @param invoiceCreateDto the updated invoice data
     * @return a response containing the updated invoice
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateInvoice(@PathParam("id") long id, InvoiceCreateDto invoiceCreateDto) {
        Invoice existingInvoice = invoiceRepository.findById(id);
        if (existingInvoice == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (invoiceCreateDto.getInvoiceDate() != null) {
            existingInvoice.setInvoiceDate(invoiceCreateDto.getInvoiceDate());
        }
        if (invoiceCreateDto.getInvoiceAmount() != 0.0) {
            existingInvoice.setInvoiceAmount(invoiceCreateDto.getInvoiceAmount());
        }
        if (invoiceCreateDto.getInvoiceCategoryId() != null) {
            InvoiceCategory invoiceCategory = invoiceCategoryRepository.findById(Long.parseLong(invoiceCreateDto.getInvoiceCategoryId()));
            if (invoiceCategory != null) {
                existingInvoice.setInvoiceCategory(invoiceCategory);
            }
        }
        if (invoiceCreateDto.getHousingObjectId() != null) {
            HousingObject housingObject = housingObjectRepository.findById(Long.parseLong(invoiceCreateDto.getHousingObjectId()));
            if (housingObject != null) {
                existingInvoice.setHousingObject(housingObject);
            }
        }
        if (invoiceCreateDto.getDescription() != null) {
            existingInvoice.setDescription(invoiceCreateDto.getDescription());
        }
        if (invoiceCreateDto.getStatus() != null) {
            existingInvoice.setStatus(invoiceCreateDto.getStatus());
        }
        if (invoiceCreateDto.getReceiver() != null) {
            existingInvoice.setReceiver(invoiceCreateDto.getReceiver());
        }
        if (invoiceCreateDto.getReceiverIban() != null) {
            existingInvoice.setReceiverIban(invoiceCreateDto.getReceiverIban());
        }
        if (invoiceCreateDto.getReceiverBic() != null) {
            existingInvoice.setReceiverBic(invoiceCreateDto.getReceiverBic());
        }
        if (invoiceCreateDto.getExternalInvoiceNumber() != null) {
            existingInvoice.setExternalInvoiceNumber(invoiceCreateDto.getExternalInvoiceNumber());
        }
        if (invoiceCreateDto.getCurrency() != null) {
            existingInvoice.setCurrency(invoiceCreateDto.getCurrency());
        }
        if (invoiceCreateDto.getRelevantForAnnualStatement() != null) {
            existingInvoice.setRelevantForAnnualStatement(Boolean.parseBoolean(invoiceCreateDto.getRelevantForAnnualStatement()));
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