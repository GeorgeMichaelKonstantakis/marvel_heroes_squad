package com.gkonstantakis.marvelsquads.ui.intents

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.gkonstantakis.marvelsquads.ui.activities.HeroActivity
import com.gkonstantakis.marvelsquads.ui.activities.MainActivity
import com.gkonstantakis.marvelsquads.data.model.Hero

class Intents {
    fun mainActivityIntent(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

        try {
            startActivity(context, intent, Bundle())
        } catch (e: Exception) {
        }
    }

    fun heroActivityIntent(hero: Hero, heroInSquad: Boolean, context: Context) {
        val intent = Intent(context, HeroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        intent.putExtra("HERO_ID", hero.id)
        intent.putExtra("HERO_NAME", hero.name)
        intent.putExtra("HERO_DESCRIPTION", hero.description)
        intent.putExtra("HERO_IMAGE_PATH", hero.imageUrl)
        intent.putExtra("IS_HERO_IN_SQUAD", heroInSquad)

        try {
            startActivity(context,intent, Bundle())
        } catch (e: Exception) {
        }
    }
}