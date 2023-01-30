package com.victorb.lingua.data.deck.repository

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.deck.entity.Deck
import java.util.*

private val cards1 = listOf(
    card(input = "maestro", output = "teacher"),
    card(input = "cavallo", output = "horse"),
    card(input = "autista", output = "driver"),
    card(input = "mare", output = "see"),
    card(input = "luna", output = "moon"),
    card(input = "caricabatterie", output = "charger"),
    card(input = "tastiera", output = "keyboard"),
    card(input = "schermo", output = "screen"),
    card(input = "madre", output = "mother"),
    card(input = "moglie", output = "wife"),
    card(input = "marito", output = "husband"),
    card(input = "padre", output = "father"),
    card(input = "figlia", output = "daughter"),
    card(input = "figlio", output = "son"),
    card(input = "bambino", output = "child"),
    card(input = "amico", output = "friend"),
    card(input = "aereo", output = "plane"),
    card(input = "autobus", output = "bus"),
    card(input = "tavalo", output = "table"),
    card(input = "forchetta", output = "fork"),
)

private val cards2 = listOf(
    card(input = "Buongiorno", output = "Good morning"),
    card(input = "Buon pomeriggio", output = "Good afternoon"),
    card(input = "Buonasera", output = "Good evening"),
    card(input = "Buonanotte", output = "Good night"),
    card(input = "Salve", output = "Hello"),
    card(input = "Ciao", output = "Hi"),
    card(input = "Come stai?", output = "How are you"),
    card(input = "Molto bene, grazie", output = "Very good, thank you"),
    card(input = "Come ti chiami?", output = "What is your name?"),
    card(input = "Mi chiamo Pietro", output = "My name is Pietro"),
    card(input = "Piacere", output = "Nice to meet you"),
    card(input = "Per piacere", output = "Please"),
    card(input = "Grazie", output = "Thank you"),
    card(input = "Mi dispiace", output = "I’m sorry"),
    card(input = "Prego", output = "You’re welcome"),
    card(input = "Mi scusi", output = "Excuse me"),
    card(input = "Non c’è problema", output = "No problem"),
    card(input = "Parli lentamente", output = "Speak slowly"),
    card(input = "Buona giornata", output = "Have a good day"),
    card(input = "Potrebbe aiutarmi, per favore?", output = "Could you help me, please?"),
    card(input = "Dove posso trovare un taxi?", output = "Where can I find a taxi?"),
    card(input = "Dov’è un supermercato?", output = "Where is a supermarket?"),
    card(input = "Mi porti a questo indirizzo", output = "Drive me to this address"),
    card(input = "Si fermi qui, per favore", output = "Please stop here"),
    card(input = "Aspetti un momento, per favore", output = "Please wait a moment"),
)

val fakeDecks = listOf(
    Deck(
        id = "deck1",
        title = "20 Most Popular Italian Nouns",
        cards = cards1.map { it.copy(deckId = "deck1") },
    ),
    Deck(
        id = "deck2",
        title = "Words for Italian Tourists",
        cards = cards2.map { it.copy(deckId = "deck2") },
    ),
)

private fun card(
    input: String,
    output: String,
): DeckCard = DeckCard(
    id = UUID.randomUUID().toString(),
    deckId = "",
    input = input,
    outputs = listOf(output),
)