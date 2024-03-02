package com.Factura_peticiones.service

import com.Factura_peticiones.model.Client
import com.Factura_peticiones.repository.ClientRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class ClientServiceTest {

    @InjectMocks
    lateinit var clientService: ClientService //clae que se va a probar

    @Mock
    lateinit var clientRepository: ClientRepository

    val clientMock = Client().apply {
        id=1
        nui="0301707030"
        full_name="Juan"
        address= "Ceunca"
    }

    @Test
    fun listClients() {
        val jsonString = File("./src/test/resources/client.json").readText()
        val clients = parseJsonToClientList(jsonString)

        Mockito.`when`(clientRepository.findAll()).thenReturn(clients)

        val resultList = clientService.list()

        Assertions.assertEquals(clients.size, resultList.size)
        Assertions.assertTrue(resultList.containsAll(clients))
    }


    fun parseJsonToClientList(jsonString: String): List<Client> {
        val gson = Gson()
        val clientType = object : TypeToken<List<Client>>() {}.type
        return gson.fromJson(jsonString, clientType)


    }
    @Test
    fun saveClientCorrect(){
        Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
        val response = clientService.save(clientMock)
        Assertions.assertEquals(response.id, clientMock.id)
    }


    @Test
    fun saveClientWhenFull_nameIsBlank(){

        Assertions.assertThrows(Exception::class.java) {
            clientMock.apply { full_name=" "}
            Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(clientMock)
            clientService.save(clientMock)
        }

    }
}