package com.Factura_peticiones.repository




import com.Factura_peticiones.model.Detail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DetailRepository: JpaRepository<Detail, Long?> {
    fun findById (id: Long?): Detail?


}
