package pl.coderstrust.accounting.database.impl.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConditionalOnProperty(value = "inMemoryDatabase.enabled", havingValue = "true")
@Repository
public class InMemoryDatabase implements Database {

  private final Map<Long, Invoice> invoices = new HashMap<>();
  private Long id = 1L;

  @Override
  public Long saveInvoice(Invoice invoice) {
    invoice.setId(id);
    invoices.put(id, invoice);
    return id++;
  }

  @Override
  public List<Long> saveInvoices(List<Invoice> invoicesListName) {
    List<Long> ids = new ArrayList<>();
    for (Invoice invoice : invoicesListName) {
      ids.add(saveInvoice(invoice));
    }
    return ids;
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
  public void updateInvoiceById(Invoice invoice, Long id) {
    invoice.setId(id);
    if (invoices.get(id) != null) {
      invoices.put(id, invoice);
    }
  }

  @Override
  public void removeInvoiceById(Long id) {
    invoices.remove(id);
  }
}