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

var enemies: List<Enemy> = listOf<Enemy>()
var projectiles: List<Projectile> = listOf<Projectile>()

val scoreMutex: Mutex = Mutex()
var timeSinceLastUFO: Float = 0f
var lifes: Int = 3
var score: Int = 0
var inputHandler: InputHandler = if (Gdx.app.type == Application.ApplicationType.Desktop)
    InputDesktop()
else
    InputAndroid()
var player: Player = Player()

fun <T> List<T>.tail() = drop(1)
fun <T> List<T>.head() = first()

fun restartGame(){
    enemies = listOf()
    projectiles = listOf()
    lifes = 3
    score = 0
    player = Player()
}

fun initialEnemies(): List<Enemy>{
    return listOf(
        Squid(100f, 400f, 1f),
        Squid(200f, 400f, 4f),
        Squid(300f, 400f, 2f),
        Squid(400f, 400f, 0f),
        Squid(500f, 400f, 3f),
        Squid(100f, 500f, 3f),
        Squid(200f, 500f, 2f),
        Squid(300f, 500f, 0f),
        Squid(400f, 500f, 4f),
        Squid(500f, 500f, 1f),
        Crab(100f, 600f, 1f),
        Crab(300f, 600f, 2f),
        Crab(500f, 600f, 0f)
    )
}

fun checkUFO(enemies: List<Enemy>): List<Enemy>{
    timeSinceLastUFO += Gdx.graphics.deltaTime

    return when {
        timeSinceLastUFO > 10f -> {
            timeSinceLastUFO = 0f
            enemies + UFO(600f)
        }
        else -> enemies
    }
}

fun addEnemies(enemies: List<Enemy>): List<Enemy>{

    return if(enemies.isEmpty()) {
        initialEnemies()
    }
    else {
        when{
            score <= 500 && enemies.size < 8 ->
                enemies + listOf(Squid(), Squid(), Squid())
            score in 501..2000 && enemies.size < 10 ->
                enemies + listOf(Crab(), Crab(), Squid())
            score > 2000 && enemies.size < 8 ->
                enemies + listOf(Octopus(), Octopus(), Crab(), Squid())
            else -> enemies
        }
    }
}

fun move(e : Enemy): Enemy{
    e.move()
    return e
}

fun move(e : Projectile): Projectile{
    e.move()
    return e
}

tailrec fun enemiesMove(enemies: List<Enemy>, newEnemies: List<Enemy> = listOf()) : List<Enemy> {
    return if(enemies.isEmpty()) {
        newEnemies
    }
    else{
        enemiesMove(enemies.tail(), newEnemies + move(enemies.head()))
    }
}

tailrec fun checkEnemyPlayerCollision(player: Player, enemies: List<Entity>){
    if(enemies.isEmpty())
        return
    else{
        if(enemies.head().collides(player)){
            score -= 100
            lifes--
            player.resetPosition()
            GameScreen.diedSound.play()
        }
        checkEnemyPlayerCollision(player, enemies.tail())
    }
}

fun newProjectiles(player: Player, enemies: List<Enemy>): List<Projectile>{
    val newEnemiesProjectiles = fun (enemies : List<Enemy>) : List<Projectile?>{
        return enemies.map { it -> it.shoot() }
    }
    return (listOf(player.shoot()) + newEnemiesProjectiles(enemies)).filterNotNull()
}

fun update() {

    player.move()

    enemies = enemiesMove(checkUFO(addEnemies(enemies)))

    checkEnemyPlayerCollision(player, enemies)

    projectiles = (projectiles + newProjectiles(player, enemies)).filter { !it.shouldDelete }.map { it -> move(it) }

    enemies = enemies.filter{ !it.shouldDelete }

    runBlocking{
        projectiles = projectilesCollisions(projectiles)
    }
}

suspend fun projectilesCollisions(projectiles: List<Projectile>): List<Projectile> = coroutineScope {

    val isPlayerProjectile = fun (projectile: Projectile): Boolean { return projectile.direction > 0 }

    val enemiesProjectiles = async { enemiesProjectilesCollisions(projectiles.filterNot(isPlayerProjectile)) }
    val playerProjectiles = async { playerProjectilesCollisions(projectiles.filter(isPlayerProjectile)) }

    return@coroutineScope enemiesProjectiles.await() + playerProjectiles.await()
}

suspend fun enemiesProjectilesCollisions(projectiles: List<Projectile>) : List<Projectile> {
    val projectileHit = fun (projectile: Projectile, player: Player): Projectile {
        return when {
            projectile.collides(player) -> {
                async{scoreMutex.withLock {
                    score -= 100
                }}
                lifes--
                player.resetPosition()
                GameScreen.diedSound.play()
                projectile.shouldDelete = true
                projectile
            }
            else -> projectile
        }
    }
    return projectiles.map { it -> projectileHit(it, player) }
}

suspend fun playerProjectilesCollisions(projectiles: List<Projectile>) : List<Projectile> {
    val projectileHit = fun (projectile: Projectile) : Projectile {
        for(it in enemies){
            when{
                it.collides(projectile) -> {
                    it.takeShot()
                    if(it.shouldDelete){
                        async {scoreMutex.withLock {
                            score += it.score
                        }}
                        GameScreen.killSound.play()
                    }
                    projectile.shouldDelete = true
                }
            }
        }
        return projectile
    }

    return projectiles.map(projectileHit)
}

fun getAllElements(): List<Entity>{
    return enemies + projectiles + player
}


