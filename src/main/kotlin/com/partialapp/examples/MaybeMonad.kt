package com.partialapp.examples

@Suppress("UNCHECKED_CAST")
sealed class Maybe<out A> {
    data class Just<A>(val value: A) : Maybe<A>()
    object Nothing : Maybe<Nothing>()

    fun <B> map(fn: (A) -> B): Maybe<B> = when (this) {
        is Just -> Just(fn(this.value))
        Nothing -> Nothing as Maybe<B>
    }

    fun <B> flatMap(fn: (A) -> Maybe<B>): Maybe<B> = when (this) {
        is Just -> fn(this.value)
        Nothing -> Nothing as Maybe<B>
    }

    companion object {
        fun <A> pure(value: A): Maybe<A> = Just(value)
    }
}

// Applicative functor: Sequential application
infix fun <A, B> Maybe<(A) -> B>.ap(value: Maybe<A>): Maybe<B> =
    value.flatMap { v -> this.map { fn -> fn(v) } }