package com.spaceinvaders.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.spaceinvaders.game.SpaceInvaders
import com.spaceinvaders.game.screens.GameScreen

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.height = GameScreen.HEIGHT.toInt()
        config.width = GameScreen.WIDHT.toInt()
        config.title = "Space Invaders"
        LwjglApplication(SpaceInvaders(), config)
    }
}
