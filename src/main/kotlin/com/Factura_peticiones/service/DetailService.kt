package com.Factura_peticiones.service



import com.Factura_peticiones.model.Detail
import com.Factura_peticiones.repository.DetailRepository
import com.Factura_peticiones.repository.InvoiceRepository
import com.Factura_peticiones.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class DetailService {
    @Autowired
    lateinit var invoiceRepository: InvoiceRepository
    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var detailRepository: DetailRepository

    fun list ():List<Detail>{
        return detailRepository.findAll()
    }

    fun save(detail: Detail): Detail {
        val response = detailRepository.save(detail)
        val product = productRepository.findById(detail.productId)
        product?.let { it ->
            val currentStock = it.stok ?: 0
            it.stok = currentStock - (detail.quantity ?: 0)
            productRepository.save(it)
        }
        return response
    }


    /*
fun save(detail: Detail): Detail {
    val response = detailRepository.save(detail)

    // Update product stock
    val product = productRepository.findById(detail.productId)
    product?.let {
        val currentStock = it.stok ?: 0
        it.stok = currentStock - (detail.quantity ?: 0)
        productRepository.save(it)
    }

    // Update invoice total
    val totalCalculated = detailRepository.sumTotal(detail.invoiceId)?.toInt()
    val invoiceResponse = invoiceRepository.findById(detail.invoiceId)
    invoiceResponse?.let {
        it.total = totalCalculated?.toBigDecimal() ?: BigDecimal.ZERO
        invoiceRepository.save(it)
    }

    return response
}
*/


    fun update(detail: Detail): Detail {
        try {
            detailRepository.findById(detail.id)
                    ?: throw Exception("ID no existe")

            return detailRepository.save(detail)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

    fun updateName(detail: Detail): Detail{
        try{
            val response = detailRepository.findById(detail.id)
                    ?: throw Exception("ID no existe")
            response.apply {
                quantity=detail.quantity //un atributo del modelo
            }
            return detailRepository.save(response)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

    fun delete (id: Long?):Boolean?{
        try{
            val response = detailRepository.findById(id)
                    ?: throw Exception("ID no existe")
            detailRepository.deleteById(id!!)
            return true
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }
    fun listById (id:Long?): Detail?{
        return detailRepository.findById(id)
    }


}