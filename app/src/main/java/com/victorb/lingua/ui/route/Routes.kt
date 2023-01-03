package com.victorb.lingua.ui.route

sealed class Routes(val route: String) {
    object Home : Routes("/")

    object CourseLibrary : Routes("/course/list")
    object Course : Routes("/course/view")

    object Practice : Routes("/practice")
}