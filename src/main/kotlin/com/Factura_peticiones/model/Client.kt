package com.Factura_peticiones.model


import jakarta.validation.constraints.NotBlank
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "client")
class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    var id: Long? = null
    @NotBlank(message="Campo obligatorio")
    var nui: String? = null
    @NotBlank(message="Campo obligatorio")
    var full_name: String? = null
    @NotBlank(message="Campo obligatorio")
    var address: String? = null
    var email: String? = null
}