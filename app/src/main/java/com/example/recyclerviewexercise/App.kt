package com.example.recyclerviewexercise

import android.app.Application
import com.example.recyclerviewexercise.model.UsersService

class App: Application() {
    val usersService = UsersService()
}