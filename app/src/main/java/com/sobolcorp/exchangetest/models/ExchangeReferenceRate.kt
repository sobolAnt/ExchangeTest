package com.sobolcorp.exchangetest.models

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "Envelop")
data class Envelop @JvmOverloads constructor(
    @field:Element(name = "subject") var subject: String = "",
    @field:Element(name = "Sender") var sender: Sender? = null,
    @field:Element(name = "Cube") var cube: Cube? = null
)

data class Sender @JvmOverloads constructor(
    @field:Element(name = "name") var name: String = ""
)

@Root(strict = false, name = "Cube")
data class Cube @JvmOverloads constructor(
    @field:Element(name = "Cube") var cube: CubeInner? = null
)
data class CubeInner @JvmOverloads constructor(
    @field:ElementList(inline = true) var cube: List<Currency>? = null
)
@Root(strict = false, name = "Cube")
data class Currency @JvmOverloads constructor(
    @field:Attribute(name = "currency", required = false) var currency: String? = "",
    @field:Attribute(name = "rate", required = false) var rate: Float? = null
)