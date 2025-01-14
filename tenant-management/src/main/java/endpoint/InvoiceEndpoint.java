/**
 * Start
 *
 * @author 1 GitHub Copilot
 * @author 2 Moritz Baur
 */
package endpoint;

import dto.InvoiceDTO;
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

import java.util.Calendar;
import java.util.Date;
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
     * Retrieves invoices by housing object ID and year.
     *
     * @param housingObjectId            the ID of the housing object
     * @param year                       the year to filter invoices
     * @param relevantForAnnualStatement optional query parameter to filter invoices relevant for the annual statement
     * @return a response containing the list of invoices for the specified housing object and year
     */
    @GET
    @Path("/housing-object/{housingObjectId}/year/{year}")
    public Response getInvoicesByHousingObjectAndYear(@PathParam("housingObjectId") long housingObjectId, @PathParam("year") int year, @QueryParam("relevantForAnnualStatement") Boolean relevantForAnnualStatement) {
        HousingObject housingObject = housingObjectRepository.findById(housingObjectId);
        if (housingObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        Date startDate = calendar.getTime();
        calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        Date endDate = calendar.getTime();

        List<Invoice> invoices;
        if (relevantForAnnualStatement != null) {
            invoices = invoiceRepository.find(
                    "housingObject = ?1 and invoiceDate >= ?2 and invoiceDate <= ?3 and relevantForAnnualStatement = ?4",
                    housingObject, startDate, endDate, relevantForAnnualStatement).list();
        } else {
            invoices = invoiceRepository.find(
                    "housingObject = ?1 and invoiceDate >= ?2 and invoiceDate <= ?3",
                    housingObject, startDate, endDate).list();
        }

        return Response.ok(invoices).build();
    }

    /**
     * Creates a new invoice.
     *
     * @param invoiceDTO the data for the new invoice
     * @return a response containing the created invoice
     */
    @POST
    @Transactional
    public Response createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice;
        try {
            invoice = invoiceService.createInvoice(invoiceDTO);
            return Response.status(Response.Status.CREATED).entity(invoice).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Updates an existing invoice.
     *
     * @param id         the ID of the invoice to update
     * @param invoiceDTO the updated invoice data
     * @return a response containing the updated invoice
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateInvoice(@PathParam("id") long id, InvoiceDTO invoiceDTO) {
        Invoice existingInvoice = invoiceRepository.findById(id);
        if (existingInvoice == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (invoiceDTO.getInvoiceDate() != null) {
            existingInvoice.setInvoiceDate(invoiceDTO.getInvoiceDate());
        }
        if (invoiceDTO.getInvoiceAmount() != 0.0) {
            existingInvoice.setInvoiceAmount(invoiceDTO.getInvoiceAmount());
        }
        if (invoiceDTO.getInvoiceCategoryId() != null) {
            InvoiceCategory invoiceCategory = invoiceCategoryRepository.findById(Long.parseLong(invoiceDTO.getInvoiceCategoryId()));
            if (invoiceCategory != null) {
                existingInvoice.setInvoiceCategory(invoiceCategory);
            }
        }
        if (invoiceDTO.getHousingObjectId() != null) {
            HousingObject housingObject = housingObjectRepository.findById(Long.parseLong(invoiceDTO.getHousingObjectId()));
            if (housingObject != null) {
                existingInvoice.setHousingObject(housingObject);
            }
        }
        if (invoiceDTO.getDescription() != null) {
            existingInvoice.setDescription(invoiceDTO.getDescription());
        }
        if (invoiceDTO.getStatus() != null) {
            existingInvoice.setStatus(invoiceDTO.getStatus());
        }
        if (invoiceDTO.getReceiver() != null) {
            existingInvoice.setReceiver(invoiceDTO.getReceiver());
        }
        if (invoiceDTO.getReceiverIban() != null) {
            existingInvoice.setReceiverIban(invoiceDTO.getReceiverIban());
        }
        if (invoiceDTO.getReceiverBic() != null) {
            existingInvoice.setReceiverBic(invoiceDTO.getReceiverBic());
        }
        if (invoiceDTO.getExternalInvoiceNumber() != null) {
            existingInvoice.setExternalInvoiceNumber(invoiceDTO.getExternalInvoiceNumber());
        }
        if (invoiceDTO.getCurrency() != null) {
            existingInvoice.setCurrency(invoiceDTO.getCurrency());
        }
        if (invoiceDTO.getRelevantForAnnualStatement() != null) {
            existingInvoice.setRelevantForAnnualStatement(Boolean.parseBoolean(invoiceDTO.getRelevantForAnnualStatement()));
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