package com.spaceinvaders.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.spaceinvaders.game.SpaceInvaders

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.height = 1000
        config.width = 1000
        config.title = "Space Invaders"
        LwjglApplication(SpaceInvaders(), config)
    }
}
