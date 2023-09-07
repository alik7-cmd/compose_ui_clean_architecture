package com.example.check24.common

sealed class Screen(val route : String){

    object Overview : Screen("screen/overview")
    object Details : Screen("screen/details")
}
