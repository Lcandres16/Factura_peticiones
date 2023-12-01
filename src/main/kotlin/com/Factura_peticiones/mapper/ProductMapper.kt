package com.Factura_peticiones.mapper


import com.Factura_peticiones.dto.ProductDto
import com.Factura_peticiones.model.Product


object ProductMapper {
    fun mapToDto(product: Product): ProductDto {
        return ProductDto(
            product.id,
            "${product.description} ${product.brand}"
        )
    }
}
fun listDto(products: List<Product>): List<ProductDto> {
    val productDtoList: MutableList<ProductDto> = mutableListOf()
    for (product in products) {
        val productDto = ProductMapper.mapToDto(product)
        productDtoList.add(productDto)
    }

    return productDtoList
}