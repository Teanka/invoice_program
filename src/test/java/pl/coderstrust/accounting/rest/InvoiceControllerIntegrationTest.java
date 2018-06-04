package pl.coderstrust.accounting.rest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.coderstrust.accounting.model.TestInvoiceProvider.invoiceOne;
import static pl.coderstrust.accounting.model.TestInvoiceProvider.invoiceOneModified;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderstrust.accounting.SpringConfiguration;
import pl.coderstrust.accounting.model.Invoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerIntegrationTest {

  Invoice invoiceOne = invoiceOne();
  Invoice invoiceModified = invoiceOneModified();
  List<Invoice> invoicesList = new ArrayList<>(Arrays.asList(invoiceOne, invoiceModified));

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldCheckThatInvoiceControllerFunctionsProperly() throws Exception {
    mockMvc.perform(post("/invoices/add_invoice").content(invoiceToJson(invoiceOne))
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());

    mockMvc.perform(get("/invoices"))
        .andDo(print())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("FV 1/2017")));

    mockMvc.perform(put("/invoices").content(invoiceToJson(invoiceModified))
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());

    mockMvc.perform(get("/invoices"))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("FV 6/2018")));

    //    mockMvc.perform(post("/invoices/add_invoices").content(invoicesToJson(invoicesList))
    //        .contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8))
    //        .andExpect(status().isOk());

    mockMvc.perform(delete("/invoices/1"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/invoices"))
        .andDo(print())
        .andExpect(jsonPath("$", hasSize(0)))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[]")));
  }

  private String invoiceToJson(Invoice invoice) throws JsonProcessingException {
    SpringConfiguration springConfiguration = new SpringConfiguration();
    return springConfiguration.objectMapper().writeValueAsString(invoice);
  }

  private List<String> invoicesToJson(List<Invoice> invoicesList) throws JsonProcessingException {
    List<String> invoicesInJson = new ArrayList<>();
    for (Invoice invoice : invoicesList) {
      invoicesInJson.add(invoiceToJson(invoice));
    }
    return invoicesInJson;
  }
}