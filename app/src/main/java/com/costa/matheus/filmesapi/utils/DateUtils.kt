package com.costa.matheus.filmesapi.utils

fun String.convertDate(): String{
    val splits = this.split("-")
    return "${splits[2]}/${splits[1]}/${splits[0]}"
}