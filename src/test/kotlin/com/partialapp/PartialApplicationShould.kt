package com.partialapp

import com.partialapp.PartialApplication.papply
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PartialApplicationShould {

    @Test
    fun `apply partially a function of two arguments returning a new function with the non-applied params`() {
        val add: (a: Int, b: Int) -> Int = { a, b -> a + b }

        assertThat(add.papply(1, NA)(2)).isEqualTo(add(1, 2))
        assertThat(add.papply(NA, 2)(1)).isEqualTo(add(1, 2))
    }

    @Test
    fun `apply partially a function of three arguments returning a new function with the non-applied params`() {
        val add: (a: Int, b: Int, c: Int) -> Int = { a, b, c -> a + b + c }

        assertThat(add.papply(1, NA, NA)(2, 3)).isEqualTo(add(1, 2, 3))
        assertThat(add.papply(NA, 2, NA)(1, 3)).isEqualTo(add(1, 2, 3))
        assertThat(add.papply(NA, NA, 3)(1, 2)).isEqualTo(add(1, 2, 3))
        assertThat(add.papply(NA, 2, 3)(1)).isEqualTo(add(1, 2, 3))
        assertThat(add.papply(1, NA, 3)(2)).isEqualTo(add(1, 2, 3))
        assertThat(add.papply(1, 2, NA)(3)).isEqualTo(add(1, 2, 3))
    }

    @Test
    fun `apply partially a function of four arguments returning a new function with the non-applied params`() {
        val add: (a: Int, b: Int, c: Int, d: Int) -> Int = { a, b, c, d -> a + b + c + d }

        assertThat(add.papply(1, NA, NA, NA)(2, 3, 4)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(NA, 2, NA, NA)(1, 3, 4)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(NA, NA, 3, NA)(1, 2, 4)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(NA, NA, NA, 4)(1, 2, 3)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(1, 2, NA, NA)(3, 4)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(1, NA, 3, NA)(2, 4)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(1, NA, NA, 4)(2, 3)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(NA, 2, NA, 4)(1, 3)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(NA, 2, 3, NA)(1, 4)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(NA, NA, 3, 4)(1, 2)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(NA, 2, 3, 4)(1)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(1, NA, 3, 4)(2)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(1, 2, NA, 4)(3)).isEqualTo(add(1, 2, 3, 4))
        assertThat(add.papply(1, 2, 3, NA)(4)).isEqualTo(add(1, 2, 3, 4))
    }
}