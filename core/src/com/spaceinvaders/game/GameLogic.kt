package com.spaceinvaders.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Rectangle
import com.spaceinvaders.game.entities.Entity
import com.spaceinvaders.game.entities.Player
import com.spaceinvaders.game.screens.GameScreen

class GameLogic {

    val enemies: MutableList<Entity> = mutableListOf<Entity>()
    var score: Int = 0
    val player: Player = Player()

    fun update(){
        player.move()
    }
}

