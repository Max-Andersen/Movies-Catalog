package com.example.moviecatalog.repository

class AccountRepository {
    val url = ""
    fun Regiser(login: String, name: String, password: String, email: String, birthdate: String, gender: String): String{
        //make request on url with login, name, etc.
        return "Success"
    }

    fun Login(username: String, password: String): String{
        //make request on url with login, name, etc.
        return "$username login successful"
    }

    fun Logout(){

    }
}