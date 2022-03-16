package com.partialapp.examples

import com.partialapp.NA
import com.partialapp.PartialApplication.papply
import com.partialapp.examples.Protocol.HTTP
import com.partialapp.examples.Protocol.HTTPS

enum class Protocol { HTTP, HTTPS }

fun buildUrl(protocol: Protocol, hostName: String, port: Int?, path: String?): String =
    "${protocol.name.lowercase()}//$hostName${port?.let { ":$it" }}${path?.let { "/$path" }}"

val buildProductionOrdersURL: (path: String?) -> String =
    ::buildUrl.papply(HTTPS, "service.orders.consul", 443, NA)

val buildStagingOrdersURL: (path: String?) -> String =
    ::buildUrl.papply(HTTP, "service.orders.consul", 80, NA)



