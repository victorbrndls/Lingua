package com.victorb.lingua.data.deck.repository

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.deck.entity.Deck
import java.util.*

private val fakeCards = listOf(
    DeckCard(
        id = "",
        deckId = "",
        input = "Car",
        outputs = listOf("Auto"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Tomato",
        outputs = listOf("Tomate"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Sugar",
        outputs = listOf("Zucker"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Oi",
        outputs = listOf("Hello"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Bom dia",
        outputs = listOf("Good morning"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Tchau",
        outputs = listOf("Bye"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "And",
        outputs = listOf("E"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Finger",
        outputs = listOf("Dedo"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Lorem",
        outputs = listOf("Ipsum"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Keyboard and Mouse",
        outputs = listOf("Teclado e Mouse"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "How are you?",
        outputs = listOf("Wie geht's?"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "I'm doing well",
        outputs = listOf("Mir geht es gut"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Sausage",
        outputs = listOf("Wurst"),
    ),
    DeckCard(
        id = "",
        deckId = "",
        input = "Juice",
        outputs = listOf("Suco"),
    ),
)

val fakeDecks = listOf(
    Deck(
        id = "deck1",
        title = "10 Italian Words",
        cards = fakeCards.shuffled().take(14)
            .map { it.copy(id = UUID.randomUUID().toString(), deckId = "deck1") },
    ),
    Deck(
        id = "deck2",
        title = "Beginner Words for Portuguese Learners",
        cards = fakeCards.shuffled().take(14)
            .map { it.copy(id = UUID.randomUUID().toString(), deckId = "deck2") },
    ),
    Deck(
        id = "deck3",
        title = "German for Kids",
        cards = fakeCards.shuffled().take(14)
            .map { it.copy(id = UUID.randomUUID().toString(), deckId = "deck3") },
    ),
    Deck(
        id = "deck4",
        title = "18th English Words",
        cards = fakeCards.shuffled().take(14)
            .map { it.copy(id = UUID.randomUUID().toString(), deckId = "deck4") },
    ),
)