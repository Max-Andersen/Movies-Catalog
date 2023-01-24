package com.example.moviecatalog

import com.example.moviecatalog.network.Network
import com.example.moviecatalog.repository.UserRepository

suspend fun checkUserAlive(): Boolean {
    var result = false
    UserRepository().getData().collect {
        result = it.isSuccess
    }
    return result
}

fun clearUserData() {
    Network.userId = ""
    Network.updateToken("")
}