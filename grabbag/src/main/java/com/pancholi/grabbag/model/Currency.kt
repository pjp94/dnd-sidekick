package com.pancholi.grabbag.model

enum class Currency {

    CP { override fun toString() = this.name.lowercase() },
    SP { override fun toString() = this.name.lowercase() },
    EP { override fun toString() = this.name.lowercase() },
    GP { override fun toString() = this.name.lowercase() },
    PP { override fun toString() = this.name.lowercase() }
}