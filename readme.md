# Partial application in Kotlin

Kotlin does not provide [partial application](https://en.wikipedia.org/wiki/Partial_application) built-in capabilities, but, can
we use it somehow?

## Currying

We can not talk about partial application without talking about [currying](https://en.wikipedia.org/wiki/Currying), a
related technique but different concept:

- **Partial Application**: process to take a function with several args, apply some of them and produce another function
  with less [arity](https://en.wikipedia.org/wiki/Arity).
- **Currying**: technique of converting a function with several args into a sequence of functions that each takes a
  single argument.

Currying allows one form of partial application, since we can apply arguments one by one to a curried version of a
function.

Languages that support [first-class functions](https://en.wikipedia.org/wiki/First-class_function), such as kotlin, are able 
to implement in one way or another currying and partial application.

## Partial application & Currying in Kotlin

In languages such as ML, Haskell or F#, functions are defined in curried form by default, but Kotlin as many languages
like java, scala or Javascript, functions are multi-argument and not curried.

Even though Kotlin does not support currying, we can easily transform our functions to a curried version:

```kotlin
fun <A, B, C> ((A, B) -> C).curry(): (A) -> (B) -> C = { a: A -> { b: B -> this(a, b) } }
```

We are going to have a [lot of extension functions](src/main/kotlin/com/partialapp/Currying.kt), one for each arity, but
is doable.

We can also implement partial application:

```kotlin
object NotApplicable

typealias NA = NotApplicable

fun <A, B, C> ((A, B) -> C).papply(a: A, na: NA): (B) -> C = { b: B -> this(a, b) }

fun <A, B, C> ((A, B) -> C).papply(na: NA, b: B): (A) -> C = { a: A -> this(a, b) }
```

This time we will have even more [extension functions](src/main/kotlin/com/partialapp/PartialApplication.kt), one for each combination of params.

## But, why would we need partial application?

Partial application can be used to solve software problems in a different way.

### Function specialization

The main benefit of partial application and currying is the ability to define more general functions, which can be easily 
used to create specific ones without repeating code, therefore empowering [DRY](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself) principle.

Here a simple example about how to create new functions with partial app using currying:
```kotlin
fun add(a: Int, b: Int) : Int = a + b
 
val plusOne = ::add.curry()(1)

plusOne(3)
// 4
```

And here how to do it using partial application directly without currying:
```kotlin
enum class Protocol { HTTP, HTTPS }

fun buildUrl(protocol: Protocol, hostName: String, port: Int?, path: String?): String =
    "${protocol.name.lowercase()}//$hostName${port?.let { ":$it" }}${path?.let { "/$path" }}"

val buildProductionOrdersURL: (path: String?) -> String =
    ::buildUrl.papply(HTTPS, "service.orders.consul", 443, NA)

val buildStagingOrdersURL: (path: String?) -> String =
    ::buildUrl.papply(HTTP, "service.orders.consul", 80, NA)
```

### Facilitates Function Composition and the use of high order functions

Curried functions are taking one argument at time, it facilitates [function composition](https://en.wikipedia.org/wiki/Function_composition)
 since this technique requires one arg functions to combine and chain them.
```kotlin
object Composition {
    infix fun <A, B, C> ((B) -> C).compose(fn: (A) -> B): (A) -> C = { a: A -> this(fn(a)) }
}
```

Also, Currying and partial application are returning functions, so using them we are already promoting one of the most important 
concepts of functional programming, [High order functions](https://en.wikipedia.org/wiki/Higher-order_function).

Let's see it in action:
```kotlin
data class Person(val age: Int, val name: String)

fun isGreaterThan(comparator: Int, value: Int) = value > comparator

val moreThanSeventeen: (value: Int) -> Boolean = ::isGreaterThan.curry()(17)

val isAgeOfLegalMajority: (Person) -> Boolean = moreThanSeventeen compose Person::age

listOf(Person(18, "Jane"), Person(4, "Michael"), Person(22, "John")).filter(isAgeOfLegalMajority)
```

### Dependency injection

Another really useful use-case of partial application is dependency-injection. In OOP is a common practice to provide any 
dependency/collaborator to our objects at construction time, but there are other ways to do it, one could be in the return 
type of our functions (see [reader monad](https://hackage.haskell.org/package/mtl-2.2.2/docs/Control-Monad-Reader.html)) and 
another could be to provide the dependencies as any other function argument.

Let's see it in action:
```kotlin
typealias GetPerson = (UUID) -> Marriage.Person?

data class Person(val id: UUID, val name: String)

val persons = listOf(Person(randomUUID(), "Jane"), Person(randomUUID(), "Mary"))

val inMemoryGetPerson: GetPerson = { id:UUID -> persons.find { it.id == id } }

data class Marriage(val first: Person, val second: Person)

fun marry(getPerson: GetPerson, first: UUID, second: UUID): Marriage? =
  getPerson(first)?.let { one -> getPerson(second)?.let { Pair(one, it) }?.let { Marriage(it.first, it.second) } }

val marryCurried: (getPerson: GetPerson) -> (first: UUID) -> (second: UUID) -> Marriage? = ::marry.curry()

val finalMarry: (first: UUID) -> (second: UUID) -> Marriage? = marryCurried(inMemoryGetPerson)
```
With this approach, we can provide our dependencies beforehand, and pass the resulting function around across our program, 
allowing `finalMarry` function to be dependency agnostic.

### Applicative Functors

[Applicative functors](https://en.wikipedia.org/wiki/Applicative_functor), together with [functors](https://en.wikipedia.org/wiki/Functor_(functional_programming)) and [monads](https://en.wikipedia.org/wiki/Monad_(functional_programming)), are one of
most useful patterns in functional programming.

In a nutshell, applicative functors allow us to:
- Apply normal functions (taking non-monadic args) on two monadic values
- Allow traversing monadic values (sequence and traverse functions)

First let's write the maybe monad:
```kotlin
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
```

Now, let's apply a normal function to a couple of optional values:
```kotlin
val add: (a: Int, b: Int) -> Int = { a, b -> a + b }
val curriedAdd = add.curry()
val one = Maybe.pure(1)
val two = Maybe.pure(2)
val fn = Maybe.pure(curriedAdd)

val result: Maybe<Int> = fn ap one ap two
// result = Maybe(3)
```

## Conclusion

Partial application is a powerful tool and can be applied in kotlin, but does it worth it?

Well, I guess that in functional-first languages like haskell, clojure, F# or scala it is something more natural, a normal
way to code because the languages themselves push you to use it. 

In Kotlin this is a bit forced, you have to create your own functions or use fn libraries to achieve it, and even with them 
it is still a bit complex and weird.

