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

    private val enemies: MutableList<Enemy> = mutableListOf()
    private val player: Player = Player()
    private val playerProjectiles: MutableList<Projectile> = mutableListOf()
    private val enemiesProjectiles: MutableList<Projectile> = mutableListOf()
    private val scoreMutex: Mutex = Mutex()
    private var timeSinceLastUFO: Float = 0f
    var lifes: Int = 3
    var score: Int = 0


    companion object {
        lateinit var inputHandler: InputHandler
    }

    init {
        inputHandler = if (Gdx.app.type == Application.ApplicationType.Desktop)
            InputDesktop()
        else
            InputAndroid()
    }

    private fun initialEnemies(){
        enemies.add(Squid(100f, 400f, 1f))
        enemies.add(Squid(200f, 400f, 4f))
        enemies.add(Squid(300f, 400f, 2f))
        enemies.add(Squid(400f, 400f, 0f))
        enemies.add(Squid(500f, 400f, 3f))

        enemies.add(Squid(100f, 500f, 3f))
        enemies.add(Squid(200f, 500f, 2f))
        enemies.add(Squid(300f, 500f, 0f))
        enemies.add(Squid(400f, 500f, 4f))
        enemies.add(Squid(500f, 500f, 1f))

        enemies.add(Crab(100f, 600f, 1f))
        enemies.add(Crab(300f, 600f, 2f))
        enemies.add(Crab(500f, 600f, 0f))
    }

    private fun updateEnemies(){
        timeSinceLastUFO += Gdx.graphics.deltaTime;

        if(enemies.isEmpty()) {
            initialEnemies()
        }

        if(timeSinceLastUFO > 10f){
            enemies.add(UFO(600f))
            timeSinceLastUFO = 0f
        }

        if(score <= 500 && enemies.size < 8){
            enemies.add(Squid())
            enemies.add(Squid())
            enemies.add(Squid())
        }
        else if(score in 501..2000 && enemies.size < 10){
            enemies.add(Crab())
            enemies.add(Crab())
            enemies.add(Squid())
        }
        else if(score > 2000 && enemies.size < 8){
            enemies.add(Octopus())
            enemies.add(Octopus())
            enemies.add(Crab())
            enemies.add(Squid())
        }
    }

    fun update() {

        player.move()
        val p: Projectile? = player.shoot()
        if (p != null) {
            playerProjectiles.add(p)
            GameScreen.shotSound.play()
        }

        updateEnemies()

        for (e in enemies) {
            e.move()
            if(e.collides(player)) {
                score -= 100
                lifes--
                player.resetPosition()
                GameScreen.diedSound.play()
            }
            val p: Projectile? = e.shoot()
            if (p != null) enemiesProjectiles.add(p)
        }

        playerProjectiles.removeAll { it.shouldDelete }
        enemiesProjectiles.removeAll { it.shouldDelete }
        enemies.removeAll { it.shouldDelete }

        runBlocking {
            launch {
                enemiesProjectilesCollisions()
            }
            launch {
                playerProjectilesCollisions()
            }
        }
    }

    private suspend fun enemiesProjectilesCollisions(){
        for (p in enemiesProjectiles) {
            p.move()
            if (player.collides(p)) {
                scoreMutex.withLock {
                    score -= 100
                }
                lifes--
                player.resetPosition()
                GameScreen.diedSound.play()
                p.shouldDelete = true
            }
        }
    }

    private suspend fun playerProjectilesCollisions(){
        for (p in playerProjectiles) {
            p.move()
            for (e in enemies) {
                if (e.collides(p)) {
                    e.takeShot()
                    if (e.shouldDelete) {
                        scoreMutex.withLock {
                            score += e.score
                        }
                        GameScreen.killSound.play()
                    }
                    p.shouldDelete = true
                }
            }
        }
    }

    fun getAllElements(): List<Entity>{
        return enemies + playerProjectiles + enemiesProjectiles + player
    }
}

