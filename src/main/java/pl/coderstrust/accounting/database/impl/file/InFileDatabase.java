package pl.coderstrust.accounting.database.impl.file;

import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.Collection;

/**
 * Created by Adam on 2018-04-16.
 */
public class InFileDatabase implements Database {

  @Override
  public void saveInvoice(Invoice invoice) {

  }

  @Override
  public Collection<Invoice> getInvoices() {
    return null;
  }

  @Override
  public Invoice getInvoiceById(Long id) {
    return null;
  }

  @Override
  public void updateInvoice(Invoice invoice) {

  }

  @Override
  public void removeInvoiceById(Long id) {
  }
}
