package com.Factura_peticiones.mapper

import com.Factura_peticiones.dto.ProductDto
import com.Factura_peticiones.model.Product
import com.Factura_peticiones.repository.ProductRepository


class ProductMapper(private val productRepository: ProductRepository) {

    companion object {
        fun mapToDto(product: Product): ProductDto {
            return ProductDto(
                product.id,
                "${product.description} ${product.brand}"
            )
        }
    }

    fun listDto(): List<ProductDto> {
        val productList = productRepository.findAll()
        val productDtoList = mutableListOf<ProductDto>()

        productList.map { product ->
            val productDto = mapToDto(product)
            productDtoList.add(productDto)
        }

        return productDtoList
    }
}