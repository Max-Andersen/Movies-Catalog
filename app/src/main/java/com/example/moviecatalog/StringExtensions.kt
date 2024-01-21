package com.example.moviecatalog

fun String.normalizeDate(): String {
    val originalString = this
    return buildString {
        append(originalString.slice(6..9) + "-")
        append(originalString.slice(3..4) + "-")
        append(originalString.slice(0..1) + "-")
        append("T18:14:23.985Z")
    }
}

fun String.unNormalizeDate(): String {
    val originalString = this
    return buildString {
        append(originalString.slice(8..9) + ".")
        append(originalString.slice(5..6) + ".")
        append(originalString.slice(0..3))
    }
}