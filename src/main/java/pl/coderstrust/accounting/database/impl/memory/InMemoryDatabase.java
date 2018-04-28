package pl.coderstrust.accounting.database.impl.memory;

import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adam on 2018-04-16.
 */
public class InMemoryDatabase implements Database {

  private final Map<Long, Invoice> invoices = new HashMap<>();
  private Long id = 0L;

  @Override
  public void saveInvoice(Invoice invoice) {
    invoice.setId(id);
    invoices.put(id++, invoice);
  }
  @Override
  public void saveListOfInvoices(List<Invoice> invoicesListName){
    for (Invoice invoice: invoicesListName){
      saveInvoice(invoice);
    }
  }

  @Override
  public Collection<Invoice> getInvoices() {
    return invoices.values();
  }


  @Override
  public Invoice getInvoiceById(Long id) {
    return invoices.get(id);
  }

  @Override
  public void updateInvoice(Invoice invoice) {
    invoices.put(invoice.getId(), invoice);
  }

  @Override
  public void removeInvoiceById(Long id) {
    invoices.remove(id);
  }
}
