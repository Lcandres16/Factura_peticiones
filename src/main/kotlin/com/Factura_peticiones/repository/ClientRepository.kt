package com.Factura_peticiones.repository




import com.Factura_peticiones.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository: JpaRepository<Client, Long?> {
    fun findById (id: Long?): Client?
    fun findByAddress(address: String?): List<Client>
}