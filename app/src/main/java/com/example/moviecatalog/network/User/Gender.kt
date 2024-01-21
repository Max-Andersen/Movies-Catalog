package com.example.moviecatalog.network.User

enum class Gender(val serverId: Int) {
    MALE(0),
    FEMALE(1);

    companion object {
        fun fromServerIdToEnumValue(serverId: Int): Gender =
            when (serverId) {
                0 -> MALE
                1 -> FEMALE
                else -> throw Exception("Unknown gender!")
            }
    }
}