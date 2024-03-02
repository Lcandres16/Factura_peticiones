package com.Factura_peticiones.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "role")
class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    var id: Long? = null
    var role: String? = null
    @Column(name = "user_id")
    var userId: Long? = null
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable=false, updatable=false)
    var user:User? = null
}