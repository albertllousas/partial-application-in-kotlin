package com.partialapp.examples

import com.partialapp.Currying.curry
import com.partialapp.NA
import com.partialapp.PartialApplication.papply

fun add(a: Int, b:Int): Int = a + b

val plusOne: (b: Int) -> Int = ::add.curry()(1)

val plusTwo: (a: Int) -> Int = ::add.papply(NA, 2)
