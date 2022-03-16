package com.partialapp

object NotApplicable

typealias NA = NotApplicable

object PartialApplication {

    fun <A, B, C> ((A, B) -> C).papply(a: A, na: NA): (B) -> C = { b: B -> this(a, b) }

    fun <A, B, C> ((A, B) -> C).papply(na: NA, b: B): (A) -> C = { a: A -> this(a, b) }

    fun <A, B, C, D> ((A, B, C) -> D).papply(a: A, b: B, na3: NA): (C) -> D = { c: C -> this(a, b, c) }

    fun <A, B, C, D> ((A, B, C) -> D).papply(a: A, na2: NA, c: C): (B) -> D = { b: B -> this(a, b, c) }

    fun <A, B, C, D> ((A, B, C) -> D).papply(na1: NA, b: B, c: C): (A) -> D = { a: A -> this(a, b, c) }

    fun <A, B, C, D> ((A, B, C) -> D).papply(a: A, na2: NA, na3: NA): (B, C) -> D = { b: B, c: C -> this(a, b, c) }

    fun <A, B, C, D> ((A, B, C) -> D).papply(na1: NA, b: B, na3: NA): (A, C) -> D = { a: A, c: C -> this(a, b, c) }

    fun <A, B, C, D> ((A, B, C) -> D).papply(na1: NA, na2: NA, c: C): (A, B) -> D = { a: A, b: B -> this(a, b, c) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(a: A, n2: NA, na3: NA, na4: NA): (B, C, D) -> E =
        { b: B, c: C, d: D -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(n1: NA, b: B, na3: NA, na4: NA): (A, C, D) -> E =
        { a: A, c: C, d: D -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(n1: NA, n2: NA, c: C, na4: NA): (A, B, D) -> E =
        { a: A, b: B, d: D -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(n1: NA, n2: NA, na3: NA, d: D): (A, B, C) -> E =
        { a: A, b: B, c: C -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(a: A, b: B, na3: NA, na4: NA): (C, D) -> E =
        { c: C, d: D -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(n1: NA, b: B, c: C, na4: NA): (A, D) -> E =
        { a: A, d: D -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(na1: NA, na2: NA, c: C, d: D): (A, B) -> E =
        { a: A, b: B -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(na1: NA, b: B, na3: NA, d: D): (A, C) -> E =
        { a: A, c: C -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(a: A, na2: NA, c: C, na4: NA): (B, D) -> E =
        { b: B, d: D -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(a: A, na2: NA, na3: NA, d: D): (B, C) -> E =
        { b: B, c: C -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(a: A, b: B, c: C, na4: NA): (D) -> E =
        { d: D -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(a: A, b: B, na3: NA, d: D): (C) -> E =
        { c: C -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(a: A, na2: NA, c: C, d: D): (B) -> E =
        { b: B -> this(a, b, c, d) }

    fun <A, B, C, D, E> ((A, B, C, D) -> E).papply(na1: NA, b: B, c: C, d: D): (A) -> E =
        { a: A -> this(a, b, c, d) }

}
