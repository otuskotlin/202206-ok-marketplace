package ru.otuskotlin.m3l1.spring.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserEntity(
    @Id
    @GeneratedValue
    val id: Int,
    val name: String
)
