package com.Factura_peticiones.service




import com.Factura_peticiones.model.Client
import com.Factura_peticiones.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.web.server.ResponseStatusException


@Service
class ClientService {
    @Autowired
    lateinit var clientRepository: ClientRepository

    fun list(): List<Client> {
        return clientRepository.findAll()
    }

    fun save(client: Client): Client {
        try {
            client.full_name?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("Nombres no debe ser vacio")
            return clientRepository.save(client)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun update(client: Client): Client {
        try {
            clientRepository.findById(client.id)
                ?: throw Exception("ID no existe")

            return clientRepository.save(client)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun updateName(client: Client): Client {
        try {
            val response = clientRepository.findById(client.id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ID no existe")

            response.apply {
                full_name = client.full_name
            }

            return clientRepository.save(response)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }


    fun delete(id: Long?): Boolean? {
        try {
            val response = clientRepository.findById(id)

                ?: throw Exception("ID no existe")
            clientRepository.deleteById(id!!)
            return true
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun listById(id: Long?): Client? {
        return clientRepository.findById(id)
    }


    fun getClientsWithAddress(address: String?): List<Client> {
        val clients = if (address != null) {
            clientRepository.findByAddress(address)
        } else {
            emptyList()
        }

        return clients
    }



   /* fun list (pageable: Pageable,client: Client):Page<Client>{
        val matcher = ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withMatcher(("full_name"), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        return clientRepository.findAll(Example.of(client, matcher), pageable)
    }
*/

}