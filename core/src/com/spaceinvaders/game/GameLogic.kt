package com.spaceinvaders.game

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Rectangle
import com.spaceinvaders.game.entities.Enemy
import com.spaceinvaders.game.input.InputAndroid
import com.spaceinvaders.game.input.InputDesktop
import com.spaceinvaders.game.input.InputHandler
import com.spaceinvaders.game.entities.Entity
import com.spaceinvaders.game.entities.Player
import com.spaceinvaders.game.entities.Projectile
import com.spaceinvaders.game.screens.GameScreen

import kotlin.concurrent.thread
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.sync.Mutex
import kotlinx.coroutines.experimental.sync.withLock

class GameLogic {

    val enemies: MutableList<Enemy> = mutableListOf()
    val player: Player = Player()
    val playerProjectiles: MutableList<Projectile> = mutableListOf()
    val enemiesProjectiles: MutableList<Projectile> = mutableListOf()
    var lifes: Int = 3
    var score: Int = 0
    val scoreMutex: Mutex = Mutex()

    companion object {
        lateinit var inputHandler: InputHandler
    }

    init {
        inputHandler = if (Gdx.app.type == Application.ApplicationType.Desktop)
            InputDesktop()
        else
            InputAndroid()
    }

    fun update(){

        player.move()
        val p: Projectile? = player.shoot()
        if (p != null) {
            playerProjectiles.add(p)
            GameScreen.shotSound.play()
        }

        for(e in enemies) {
            e.move()
            val p: Projectile? = e.shoot()
            if(p != null) enemiesProjectiles.add(p)
        }

        playerProjectiles.removeAll { it.shouldDelete }
        enemiesProjectiles.removeAll { it.shouldDelete }

        runBlocking {
            launch {
                for (p in enemiesProjectiles) {
                    p.move()
                    if (player.collides(p)) {
                        //GameScreen.diedSound.play()
                        scoreMutex.withLock {
                            score -= 100
                        }
                        player.resetPosition()
                        p.shouldDelete = true
                    }
                }
            }
            launch {
                for (p in playerProjectiles) {
                    p.move()
                    for (e in enemies) {
                        if (e.collides(p)) {
                            e.takeShot()

                            if(e.shouldDelete) {
                                scoreMutex.withLock {
                                    score += e.score
                                }
                            }

                            p.shouldDelete = true
                        }
                    }
                }
            }
        }
    }

    fun getAllElements(): List<Entity>{
        return enemies + playerProjectiles + enemiesProjectiles + player
    }
}

