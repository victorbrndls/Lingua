package com.victorb.lingua.data.deck.repository

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.deck.entity.Deck
import java.util.*

private val fakeCards = listOf(
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "Car",
        outputs = listOf("Auto"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "Tomato",
        outputs = listOf("Tomate"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "Sugar",
        outputs = listOf("Zucker"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "Oi",
        outputs = listOf("Hello"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "Bom dia",
        outputs = listOf("Good morning"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "Tchau",
        outputs = listOf("Bye"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "And",
        outputs = listOf("E"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "Finger",
        outputs = listOf("Dedo"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "Lorem",
        outputs = listOf("Ipsum"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "Keyboard and Mouse",
        outputs = listOf("Teclado e Mouse"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "How are you?",
        outputs = listOf("Wie geht's?"),
    ),
    DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = "I'm doing good",
        outputs = listOf("Mir geht es gut"),
    ),
)

val fakeDecks = listOf(
    Deck(
        id = "deck1",
        title = "10 Italian Words",
        cards = fakeCards.shuffled().take(14).map { it.copy(deckId = "deck1") },
    ),
    Deck(
        id = "deck2",
        title = "Beginner Words for Portuguese Learners",
        cards = fakeCards.shuffled().take(14).map { it.copy(deckId = "deck2") },
    ),
    Deck(
        id = "deck3",
        title = "German for Kids",
        cards = fakeCards.shuffled().take(14).map { it.copy(deckId = "deck3") },
    ),
    Deck(
        id = "deck4",
        title = "18th English Words",
        cards = fakeCards.shuffled().take(14).map { it.copy(deckId = "deck4") },
    ),
)