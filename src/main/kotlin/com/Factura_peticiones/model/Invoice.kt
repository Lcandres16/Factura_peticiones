package com.Factura_peticiones.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "invoice")
class Invoice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    var id: Long? = null
    var code: String? = null
    @Column(name="create_At")
    var createAt: Date? = null
    var total: Int? = null
    @Column(name="client_id")
    var clientId: Long? = null

}