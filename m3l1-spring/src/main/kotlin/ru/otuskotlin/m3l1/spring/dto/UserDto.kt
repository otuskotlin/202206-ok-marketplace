package ru.otuskotlin.m3l1.spring.dto

import ru.otuskotlin.m3l1.spring.model.UserEntity

data class UserDto(
    val id: Int?,
    val name: String
)

fun UserDto.toEntity(newId: Int?) = UserEntity(id = newId ?: id ?: 0, name = name)

fun UserEntity.toDto() = UserDto(id = id, name = name)
