package ru.otus.otuskotlin.marketplace.mappers.v1.exceptions

import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

class UnknownMkplCommand(command: MkplCommand) : Throwable("Wrong command $command at mapping toTransport stage")
