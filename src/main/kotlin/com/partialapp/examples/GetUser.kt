package com.partialapp.examples

import com.partialapp.Currying.curry
import java.util.UUID
import java.util.UUID.*

typealias GetPerson = (UUID) -> Marriage.Person?

object Marriage {

    data class Person(val id: UUID, val name: String)

    val persons = listOf(Person(randomUUID(), "Jane"), Person(randomUUID(), "Mary"))

    val inMemoryGetPerson: GetPerson = { id:UUID -> persons.find { it.id == id } }

    data class Marriage(val first: Person, val second: Person)

    fun marry(getPerson: GetPerson, first: UUID, second: UUID): Marriage? =
        getPerson(first)?.let { one -> getPerson(second)?.let { Pair(one, it) }?.let { Marriage(it.first, it.second) } }

    val marryCurried: (getPerson: GetPerson) -> (first: UUID) -> (second: UUID) -> Marriage? = ::marry.curry()

    val finalMarry: (first: UUID) -> (second: UUID) -> Marriage? = marryCurried(inMemoryGetPerson)

}