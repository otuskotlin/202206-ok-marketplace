package ru.otuskotlin.m3l1.spring.repository

import org.springframework.data.repository.CrudRepository
import ru.otuskotlin.m3l1.spring.model.UserEntity

interface UserRepository: CrudRepository<UserEntity, Int> {
    fun findByName(name: String): UserEntity?

}