package com.partialapp.examples

import com.partialapp.Currying.curry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MaybeMonadShould {

    @Test
    fun `apply a normal function (taking non-monadic args) to a couple of optional values using applicative function`() {
        val add: (a: Int, b: Int) -> Int = { a, b -> a + b }
        val curriedAdd = add.curry()
        val one = Maybe.pure(1)
        val two = Maybe.pure(2)
        val fn = Maybe.pure(curriedAdd)

        val result: Maybe<Int> = fn ap one ap two

        assertThat(result).isEqualTo(Maybe.pure(3))
    }
}
