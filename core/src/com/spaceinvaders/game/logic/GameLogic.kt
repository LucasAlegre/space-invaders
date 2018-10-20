package com.spaceinvaders.game.logic

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.entities.*
import com.spaceinvaders.game.input.InputAndroid
import com.spaceinvaders.game.input.InputDesktop
import com.spaceinvaders.game.input.InputHandler
import com.spaceinvaders.game.screens.GameScreen

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.sync.Mutex
import kotlinx.coroutines.experimental.sync.withLock

class GameLogic {

    val enemies: MutableList<Enemy> = mutableListOf(Squid(500f,500f), UFO(500f), Crab(500f, 500f))
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
        enemies.removeAll { it.shouldDelete }

        runBlocking {
            launch {
                for (p in enemiesProjectiles) {
                    p.move()
                    if (player.collides(p)) {
                        //GameScreen.diedSound.play()
                        scoreMutex.withLock {
                            score -= 100
                        }
                        lifes--
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

