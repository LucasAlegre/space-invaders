package com.spaceinvaders.game.logic

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
//import com.spaceinvaders.game.entities.*
import com.spaceinvaders.game.input.InputAndroid
import com.spaceinvaders.game.input.InputDesktop
import com.spaceinvaders.game.input.InputHandler
import com.spaceinvaders.game.screens.GameScreen

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.sync.Mutex
import kotlinx.coroutines.experimental.sync.withLock

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

import com.spaceinvaders.game.logic.*


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
var player: Player = makePlayer()

fun <T> List<T>.tail() = drop(1)
fun <T> List<T>.head() = first()

fun restartGame(){
    enemies = listOf()
    projectiles = listOf()
    lifes = 3
    score = 0
    player = makePlayer()
}

fun initialEnemies(): List<Enemy>{
    return listOf(
            makeEnemy(EnemyType.SQUID, 100f, 400f, 1f),
            makeEnemy(EnemyType.SQUID, 200f, 400f, 4f),
            makeEnemy(EnemyType.SQUID, 300f, 400f, 2f),
            makeEnemy(EnemyType.SQUID, 400f, 400f, 0f),
            makeEnemy(EnemyType.SQUID, 500f, 400f, 3f),
            makeEnemy(EnemyType.SQUID, 100f, 500f, 3f),
            makeEnemy(EnemyType.SQUID, 200f, 500f, 2f),
            makeEnemy(EnemyType.SQUID, 300f, 500f, 0f),
            makeEnemy(EnemyType.SQUID, 400f, 500f, 4f),
            makeEnemy(EnemyType.SQUID, 500f, 500f, 1f),
            makeEnemy(EnemyType.CRAB, 100f, 600f, 1f),
            makeEnemy(EnemyType.CRAB, 300f, 600f, 2f),
            makeEnemy(EnemyType.CRAB, 500f, 600f, 0f)
    )
}

fun checkUFO(enemies: List<Enemy>): List<Enemy>{
    timeSinceLastUFO += Gdx.graphics.deltaTime

    return when {
        timeSinceLastUFO > 10f -> {
            timeSinceLastUFO = 0f
            enemies + makeEnemy(EnemyType.UFO, 0f, 600f, 0f)
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
                enemies + listOf(makeEnemy(EnemyType.SQUID), makeEnemy(EnemyType.SQUID), makeEnemy(EnemyType.SQUID))
            score in 501..2000 && enemies.size < 10 ->
                enemies + listOf(makeEnemy(EnemyType.CRAB), makeEnemy(EnemyType.CRAB), makeEnemy(EnemyType.SQUID))
            score > 2000 && enemies.size < 8 ->
                enemies + listOf(makeEnemy(EnemyType.OCTOPUS), makeEnemy(EnemyType.OCTOPUS), makeEnemy(EnemyType.CRAB), makeEnemy(EnemyType.SQUID))
            else -> enemies
        }
    }
}

tailrec fun enemiesMove(enemies: List<Enemy>, newEnemies: List<Enemy> = listOf()) : List<Enemy> {
    return when{
        enemies.isEmpty() -> newEnemies
        else ->  enemiesMove(enemies.tail(), newEnemies + move(enemies.head()))
    }
}

tailrec fun checkEnemyPlayerCollision(player: Player, enemies: List<Enemy>): Player{
    var player = player
    return when{
        enemies.isEmpty() -> player
        else -> {
            if(enemyCollidesWithPlayer(enemies.head())(player)){
                GameScreen.diedSound.play()
                score -= 100
                lifes--
                resetPosition(player)
            }
            else {
                checkEnemyPlayerCollision(player, enemies.tail())
            }
        }
    }
}

fun newProjectiles(player: Player, enemies: List<Enemy>): List<Projectile>{
    val newEnemiesProjectiles = fun (enemies : List<Enemy>) : List<Projectile?>{
        return enemies.map { it -> shoot(it) }
    }
    return (listOf(shoot(player)) + newEnemiesProjectiles(enemies)).filterNotNull()
}

fun update() {

    enemies = enemiesMove(checkUFO(addEnemies(enemies))).filter{ !it.shouldDelete }

    player = checkEnemyPlayerCollision(move(player), enemies)

    projectiles = (projectiles + newProjectiles(player, enemies)).filter { !it.shouldDelete }.map { it -> move(it) }

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
        val projectCollidesPlayer = {projectile: Projectile -> collides(projectile, player)}
        return when {
            projectCollidesPlayer(projectile) -> {
                async{scoreMutex.withLock {
                    score -= 100
                }}
                lifes--
                resetPosition(player)
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
        val enemyCollidesWithProjectile = { enemy: Enemy -> collides(enemy, projectile)}
        enemies.forEach{it ->
            when{
                enemyCollidesWithProjectile(it) -> {
                    takeShot(it)
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

fun getAllElements(): Triple<Player, List<Projectile>, List<Enemy>>{
    return Triple(player, projectiles, enemies)
}


