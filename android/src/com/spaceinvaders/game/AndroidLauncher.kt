package com.spaceinvaders.game

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.spaceinvaders.game.SpaceInvaders

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        config.useAccelerometer = true
        initialize(SpaceInvaders(), config)
    }
}
