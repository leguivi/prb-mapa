package com.proyecto

data class History(
    var location : String,
    var date : String
)

data class ListHistory(
    var list : MutableList<History> = mutableListOf()
)