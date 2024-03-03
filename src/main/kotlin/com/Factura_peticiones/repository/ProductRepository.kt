package com.Factura_peticiones.repository




import com.Factura_peticiones.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long?> {
    fun findById (id: Long?): Product?

    @Query(nativeQuery = true)
    fun getMostTenSoldProducts(): List<Product>
}