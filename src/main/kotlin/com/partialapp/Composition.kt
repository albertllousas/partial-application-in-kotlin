package com.partialapp

object Composition {
    infix fun <A, B, C> ((B) -> C).compose(fn: (A) -> B): (A) -> C = { a: A -> this(fn(a)) }
}