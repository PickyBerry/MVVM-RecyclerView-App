package com.example.recyclerviewexercise

import com.example.recyclerviewexercise.model.User

interface Navigator {
    fun showDetails(user: User)
    fun goBack()
    fun toast(messageRes: Int)
}