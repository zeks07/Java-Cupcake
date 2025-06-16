package com.zeks.javacupcake.lalr.productions

abstract class ProductionDelegate {
    private var production: Production? = null

    fun bindTo(production: Production) {
        this.production = production
    }
}