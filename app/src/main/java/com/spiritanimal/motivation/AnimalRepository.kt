package com.spiritanimal.motivation

import android.content.Context
import kotlin.random.Random

class AnimalRepository(context: Context) {
    private val prefs = context.getSharedPreferences("spirit_animal_prefs", Context.MODE_PRIVATE)

    val animals = listOf(
        Animal("fox", "Fox", "ADAPTABILITY", "When the path changes, change your strategy — not your purpose.", "img_fox"),
        Animal("horse", "Horse", "DRIVE", "Run toward the storm. Momentum solves what overthinking creates.", "img_horse"),
        Animal("shark", "Shark", "DECISIVENESS", "Stop circling the decision. Choose a direction and attack it.", "img_shark"),
        Animal("falcon", "Falcon", "FOCUS", "See clearly. Ignore the noise. Strike with full conviction.", "img_falcon"),
        Animal("bison", "Bison", "ENDURANCE", "Do not wait for conditions to improve. Lean into the gale.", "img_bison"),
        Animal("ant", "Ant", "DISCIPLINE", "Small actions repeated daily become impossible to tear down.", "img_ant")
    )

    fun getCurrent(): Animal {
        val index = prefs.getInt("current_index", 0)
        return animals[index.coerceIn(0, animals.size - 1)]
    }

    fun pickRandom(): Animal {
        val currentIndex = prefs.getInt("current_index", 0)
        var nextIndex = Random.nextInt(animals.size)
        if (nextIndex == currentIndex) {
            nextIndex = (currentIndex + 1) % animals.size
        }
        prefs.edit().putInt("current_index", nextIndex).apply()
        return animals[nextIndex]
    }
}
