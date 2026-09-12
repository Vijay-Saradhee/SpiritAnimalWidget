package com.spiritanimal.motivation
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
class SpiritAnimalWidgetProvider : AppWidgetProvider() {
  override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
    val repo = AnimalRepository(context)
    for (id in appWidgetIds) render(context, appWidgetManager, id, repo.getCurrent())
  }
  override fun onReceive(context: Context, intent: Intent) {
    super.onReceive(context, intent)
    if (intent.action == ACTION_RANDOMIZE) {
      val repo = AnimalRepository(context)
      val nextAnimal = repo.pickRandom()
      val manager = AppWidgetManager.getInstance(context)
      val ids = manager.getAppWidgetIds(ComponentName(context, SpiritAnimalWidgetProvider::class.java))
      for (id in ids) render(context, manager, id, nextAnimal)
    }
  }
  companion object {
    const val ACTION_RANDOMIZE = "com.spiritanimal.motivation.ACTION_RANDOMIZE"
    fun render(context: Context, manager: AppWidgetManager, id: Int, animal: Animal) {
      val views = RemoteViews(context.packageName, R.layout.widget_spirit_animal).apply {
        setTextViewText(R.id.tv_animal_name, animal.name)
        setTextViewText(R.id.tv_mentality, animal.mentality)
        setTextViewText(R.id.tv_quote, animal.message)
        val resId = context.resources.getIdentifier(animal.drawableResName, "drawable", context.packageName)
        if (resId != 0) setImageViewResource(R.id.iv_animal, resId)
        val randIntent = Intent(context, SpiritAnimalWidgetProvider::class.java).apply { action = ACTION_RANDOMIZE }
        val pendingRand = PendingIntent.getBroadcast(context, 0, randIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        setOnClickPendingIntent(R.id.btn_randomize, pendingRand)
        val appIntent = Intent(context, MainActivity::class.java)
        val pendingApp = PendingIntent.getActivity(context, 1, appIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        setOnClickPendingIntent(R.id.widget_root, pendingApp)
      }
      manager.updateAppWidget(id, views)
    }
  }
}