package com.partialapp

import com.partialapp.Composition.compose
import com.partialapp.Currying.curry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CompositionShould {

    @Test
    fun `compose two functions producing a new one with the result of chaining them`() {
        fun add(a: Int, b: Int) = a + b
        fun mult(a: Int, b: Int) = a * b
        val plusOne = ::add.curry()(1)
        val double = ::mult.curry()(2)

        val incAndDouble = double compose plusOne

        assertThat(incAndDouble(3)).isEqualTo(8)
    }
}