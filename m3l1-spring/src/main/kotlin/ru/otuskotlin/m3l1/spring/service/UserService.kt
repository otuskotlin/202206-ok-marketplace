package ru.otuskotlin.m3l1.spring.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.otuskotlin.m3l1.spring.dto.UserDto
import ru.otuskotlin.m3l1.spring.dto.toDto
import ru.otuskotlin.m3l1.spring.dto.toEntity
import ru.otuskotlin.m3l1.spring.repository.UserRepository

@Service
class UserService(
    private val repository: UserRepository,
)
{
    @Transactional
    fun createUser(user: UserDto): UserDto {
        if (repository.findByName(user.name) != null) throw IllegalStateException("User ${user.name} already exists")
        return repository.save(user.toEntity(0)).toDto()
    }

    fun deleteUser(id: Int) {
        repository.deleteById(id)
    }

    fun fetchUser(id: Int): UserDto =
        repository.findByIdOrNull(id)?.toDto() ?: throw IllegalArgumentException("User $id does not exists")

    fun fetchAllUsers(): List<UserDto> =
        repository.findAll().map { it.toDto() }

}