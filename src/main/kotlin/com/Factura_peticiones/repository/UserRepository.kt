package com.Factura_peticiones.repository

import com.Factura_peticiones.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
    fun findByUsername(username: String): User?
}