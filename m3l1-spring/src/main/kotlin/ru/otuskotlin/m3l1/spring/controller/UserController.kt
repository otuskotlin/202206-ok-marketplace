package ru.otuskotlin.m3l1.spring.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otuskotlin.m3l1.spring.dto.UserDto
import ru.otuskotlin.m3l1.spring.service.UserService


@RestController
@RequestMapping(path = ["/users"])
class UserController(
    private val service: UserService,
) {
    init {
    }

    @GetMapping
    fun fetchAll() =
        service.fetchAllUsers()

    @GetMapping(path = ["/{id}"])
    fun fetchById(@PathVariable id: Int) = service.fetchUser(id)

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: Int) = service.deleteUser(id)

    @PostMapping
    fun create(@RequestBody user: UserDto) = service.createUser(user)
}