package ru.otus.otuskotlin.marketplace.mappers.v2.exceptions

import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

class UnknownMkplCommand(command: MkplCommand) : Throwable("Wrong command $command at mapping toTransport stage")
