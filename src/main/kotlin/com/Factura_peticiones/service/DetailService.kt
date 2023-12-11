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
        try {
            // Save the detail
            val savedDetail = detailRepository.save(detail)

            // Logic to decrease stock
            val productId = detail.product_Id
            val product = productId?.let { productRepository.findById(it).orElse(null) }

            if (product != null) {
                product.apply {
                    stok = stok - detail.quantity!!

                }

                // Save the updated product
                productRepository.save(product)
            }

            // Check if the associated invoice exists
            detail.invoice_Id?.let { invoice_Id ->
                if (!invoiceRepository.existsById(invoice_Id)) {
                    throw ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found for id: $invoice_Id")
                }
            }

            // Check if the associated product exists
            detail.product_Id?.let { product_Id ->
                if (!productRepository.existsById(product_Id)) {
                    throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: $product_Id")
                }
            }

            // Return the saved detail
            return savedDetail
        } catch (ex: Exception) {
            // Handle exceptions by wrapping them in a ResponseStatusException
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing the request", ex)
        }

}
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