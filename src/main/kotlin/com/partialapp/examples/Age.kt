package com.partialapp.examples

import com.partialapp.Composition.compose
import com.partialapp.Currying.curry

object Age {

    data class Person(val age: Int, val name: String)

    fun isGreaterThan(comparator: Int, value: Int) = value > comparator

    val moreThanSeventeen: (value: Int) -> Boolean = ::isGreaterThan.curry()(17)

    val isAgeOfLegalMajority: (Person) -> Boolean = moreThanSeventeen compose Person::age
}
