package com.victorb.lingua.ui.route

import android.os.Bundle

sealed class Routes(val route: String) {
    object Home : Routes("/")

    object DeckLibrary : Routes("/deck/list")
    object ViewDeck : Routes("/deck/{id}/view")
    object EditDeck : Routes("/deck/{id}/edit") {
        fun createRoute(deckId: String?) = "/deck/$deckId/edit"
        fun parse(bundle: Bundle?): String? = bundle?.getString("id")?.takeIf { it != "null" }
    }

    object EditCard : Routes("/card/{id}/edit") {
        fun createRoute(cardId: String?) = "/card/$cardId/edit"
        fun parse(bundle: Bundle?): String? = bundle?.getString("id")?.takeIf { it != "null" }
    }

    object AddCard : Routes("/deck/{id}/card/new") {
        fun createRoute(deckId: String?) = "/deck/$deckId/card/new"
        fun parse(bundle: Bundle?): String? = bundle?.getString("id")?.takeIf { it != "null" }
    }

    object Practice : Routes("/practice/deck/{id}") {
        fun createRoute(deckId: String?) = "/practice/deck/$deckId"
        fun parse(bundle: Bundle?): String? = bundle?.getString("id")?.takeIf { it != "null" }
    }
}