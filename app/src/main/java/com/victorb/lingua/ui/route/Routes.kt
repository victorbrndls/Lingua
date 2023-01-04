package com.victorb.lingua.ui.route

sealed class Routes(val route: String) {
    object Home : Routes("/")

    object DeckLibrary : Routes("/deck/list")
    object Deck : Routes("/deck/view")

    object Practice : Routes("/practice")
}