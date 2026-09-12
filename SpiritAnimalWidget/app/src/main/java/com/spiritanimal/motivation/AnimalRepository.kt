package com.spiritanimal.motivation
import android.content.Context
class AnimalRepository(context: Context) {
  private val prefs = context.getSharedPreferences("spirit_prefs", Context.MODE_PRIVATE)
  private val animals = listOf(
    Animal("1", "SHARK", "DECISIVENESS", "Stop circling the decision. Choose a direction and move.", "img_shark"),
    Animal("2", "BISON", "ENDURANCE", "Walk through the storm. Difficult conditions do not decide your destination.", "img_bison"),
    Animal("3", "ANT", "DISCIPLINE", "Small actions repeated every day become extraordinary results.", "img_ant"),
    Animal("4", "FOX", "ADAPTABILITY", "When the path changes, change your strategy — not your purpose.", "img_fox"),
    Animal("5", "HORSE", "DRIVE", "Keep moving. Momentum is built one step at a time.", "img_horse"),
    Animal("6", "FALCON", "FOCUS", "See clearly. Ignore the noise. Act with precision.", "img_falcon")
  )
  fun getCurrent(): Animal { val id = prefs.getString("curr_id", "6"); return animals.find { it.id == id } ?: animals.first() }
  fun pickRandom(): Animal { val current = getCurrent(); val next = animals.filter { it.id != current.id }.random(); prefs.edit().putString("curr_id", next.id).apply(); return next }
}