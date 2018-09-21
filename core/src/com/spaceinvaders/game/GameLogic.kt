package com.spaceinvaders.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Rectangle
import com.spaceinvaders.game.entities.Entity
import com.spaceinvaders.game.entities.Player
import com.spaceinvaders.game.entities.Projectile
import com.spaceinvaders.game.screens.GameScreen

class GameLogic {

    val enemies: MutableList<Entity> = mutableListOf<Entity>()
    var score: Int = 0
    val player: Player = Player()
    val projectiles: MutableList<Projectile> = mutableListOf<Projectile>()
    var lives: Int = 3

    init {
    }

    fun update(){
        var p: Projectile? = player.shoot()
        if (p != null) projectiles.add(p)

        player.move()
        projectiles.removeAll { it.shouldDelete }
        for(p in projectiles) {
            p.move()
            if(player.collides(p)){
                GameScreen.diedSound.play()
                p.shouldDelete = true
            }
        }
    }

    fun getAllElements(): List<Entity>{
        return enemies + projectiles + player
    }
}

