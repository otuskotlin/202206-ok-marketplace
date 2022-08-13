package ru.otus.otuskotlin.markeplace.springapp.common

val notFoundError: (String) -> String  = {
    "Not found ad by id $it"
}