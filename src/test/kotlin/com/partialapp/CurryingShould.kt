package com.partialapp

import com.partialapp.Currying.curry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CurryingShould {

    @Test
    fun `convert a function that takes two arguments into a sequence of functions that each takes a single argument`() {
        val add: (a: Int, b: Int) -> Int = { a, b -> a + b }

        val curriedAdd = add.curry()

        assertThat(curriedAdd(1)(2)).isEqualTo(add(1, 2))
    }

    @Test
    fun `convert a function that takes three arguments into a sequence of functions that each takes a single argument`() {
        val add: (a: Int, b: Int, c: Int) -> Int = { a, b, c -> a + b + c }

        val curriedAdd = add.curry()

        assertThat(curriedAdd(1)(2)(3)).isEqualTo(add(1, 2, 3))
    }

    @Test
    fun `convert a function that takes four arguments into a sequence of functions that each takes a single argument`() {
        val add: (a: Int, b: Int, c: Int, d: Int) -> Int = { a, b, c, d -> a + b + c + d }

        val curriedAdd = add.curry()

        assertThat(curriedAdd(1)(2)(3)(4)).isEqualTo(add(1, 2, 3, 4))
    }

    @Test
    fun `convert a function that takes five arguments into a sequence of functions that each takes a single argument`() {
        val add: (a: Int, b: Int, c: Int, d: Int, e: Int) -> Int = { a, b, c, d, e -> a + b + c + d + e }

        val curriedAdd = add.curry()

        assertThat(curriedAdd(1)(2)(3)(4)(5)).isEqualTo(add(1, 2, 3, 4, 5))
    }
}