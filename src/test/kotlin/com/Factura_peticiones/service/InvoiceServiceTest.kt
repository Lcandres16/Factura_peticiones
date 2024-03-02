package com.Factura_peticiones.service


import com.Factura_peticiones.model.Client
import com.Factura_peticiones.model.Invoice
import com.Factura_peticiones.repository.ClientRepository
import com.Factura_peticiones.repository.InvoiceRepository
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.util.*

@SpringBootTest
class InvoiceServiceTest {

    @InjectMocks
    lateinit var invoiceService: InvoiceService

    @Mock
    lateinit var invoiceRepository: InvoiceRepository

    @Mock
    lateinit var clientRepository: ClientRepository



    val clientMock = Client().apply {
        id = 1
        nui = "0301707030"
        full_name = "Juan"
        address = "Ceunca"
    }
    @Test
    fun listInvoices() {

        val jsonStringList = File("./src/test/resources/invoice_test_list.json").readText(Charsets.UTF_8)
        val invoices = Gson().fromJson(jsonStringList, Array<Invoice>::class.java).toList()
        Mockito.`when`(invoiceRepository.findAll()).thenReturn(invoices)
        val resultList = invoiceService.list()

        Assertions.assertEquals(invoices.size, resultList.size)
        Assertions.assertTrue(resultList.containsAll(invoices))
    }
    @Test
    fun saveInvoiceWhenIsCorrect() {
        val jsonString = File("./src/test/resources/invoice.json").readText(Charsets.UTF_8)
        val invoiceMock = Gson().fromJson(jsonString, Invoice::class.java)
        Mockito.`when`(clientRepository.findById(invoiceMock.clientId)).thenReturn(clientMock)
        Mockito.`when`(invoiceRepository.save(Mockito.any(Invoice::class.java))).thenReturn(invoiceMock)
        val response = invoiceService.save(invoiceMock)
        Assertions.assertEquals(response.id, invoiceMock.id)
    }


}
