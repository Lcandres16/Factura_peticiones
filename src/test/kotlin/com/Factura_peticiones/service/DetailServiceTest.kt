package com.Factura_peticiones.service

import com.Factura_peticiones.model.Detail
import com.Factura_peticiones.repository.DetailRepository
import com.Factura_peticiones.repository.ProductRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class DetailServiceTest {

    @InjectMocks
    lateinit var detailService: DetailService

    @Mock
    lateinit var detailRepository: DetailRepository
    @Mock
    lateinit var productRepository: ProductRepository


    val detailMock = Detail().apply {
        id = 1
        quantity = 5
        price = 100
        invoiceId = 1
        productId = 2
    }

    @Test
    fun saveDetailCorrect() {
        Mockito.`when`(detailRepository.save(Mockito.any(Detail::class.java))).thenReturn(detailMock)
        val response = detailService.save(detailMock)
        Assertions.assertEquals(response.id, detailMock.id)
    }

    @Test
    fun listDetails() {
        val jsonString = File("./src/test/resources/detail.json").readText()
        val details = parseJsonToDetailList(jsonString)
        Mockito.`when`(detailRepository.findAll()).thenReturn(details)

        val resultList = detailService.list()
        Assertions.assertEquals(details.size, resultList.size)
        Assertions.assertTrue(resultList.containsAll(details))
    }

    fun parseJsonToDetailList(jsonString: String): List<Detail> {
        val gson = Gson()
        val detailType = object : TypeToken<List<Detail>>() {}.type
        return gson.fromJson(jsonString, detailType)
    }
}
