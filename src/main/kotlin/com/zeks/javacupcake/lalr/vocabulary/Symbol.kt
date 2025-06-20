package com.zeks.javacupcake.lalr.vocabulary

sealed class Symbol(
    val name: String,
    val presentation: String = name,
) {
    var isUsed = false
        private set

    var isAlive = false
        private set

    var isReachable = false
        private set

    fun markAsAlive() {
        isAlive = true
    }

    fun markAsUsed() {
        isUsed = true
    }

    fun markAsReachable() {
        isReachable = true
    }

    override fun toString() = presentation
}