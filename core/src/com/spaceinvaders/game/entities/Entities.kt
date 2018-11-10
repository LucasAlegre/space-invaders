package com.spaceinvaders.game.logic


import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.screens.GameScreen

data class Player(val shootSpeed: Float,
                  val shootDelay: Float,
                  val shootTexture: Texture,
                  var timeSinceLastShoot: Float,
                  val originX: Float,
                  val originY: Float,
                  val width: Float,
                  val height: Float,
                  var texture: Texture,
                  var speed: Float){
    var body: Sprite
    init {
        body = Sprite(texture, 0,0, width.toInt(), height.toInt())
        body.setPosition(originX, originY)
    }
}

enum class EnemyType {
    CRAB, SQUID, OCTOPUS, UFO
}

data class Enemy(val type: EnemyType,
                 val speed: Float,
                 val score: Int,
                 var lives: Int,
                 val shootSpeed: Float,
                 val shootDelay: Float,
                 val shootTexture: Texture,
                 var horizontalDirection: Int,
                 var verticalDirection: Int,
                 var timeSinceLastShoot: Float,
                 val originX: Float,
                 val originY: Float,
                 val width: Float,
                 val height: Float,
                 var texture: Texture,
                 var shouldDelete: Boolean){
    var body: Sprite
    init {
        body = Sprite(texture, 0,0, width.toInt(), height.toInt())
        body.setPosition(originX, originY)
    }
}

data class Projectile(val direction: Int,
                      val originX: Float,
                      val originY: Float,
                      val width: Float,
                      val height: Float,
                      var texture: Texture,
                      var shouldDelete: Boolean,
                      var speed: Float){
    var body: Sprite
    init {
        body = Sprite(texture, 0,0, width.toInt(), height.toInt())
        body.setPosition(originX, originY)
    }
}

fun makePlayer():Player{
    return Player(300f, 0.5f, GameScreen.bluelaserTexture, 0f, GameScreen.WIDHT/2 - 50, 0f, 60f, 60f, GameScreen.playerTexture, 200f)
}

fun makeEnemy(type: EnemyType, x: Float, y: Float, timeInit: Float):Enemy{
    return when{
        type == EnemyType.SQUID -> Enemy(EnemyType.SQUID,100f,50,1,100f,8f, GameScreen.redlaserTexture, 1, -5, timeInit, x, y, 70f, 70f, GameScreen.squidTexture, false)
        type == EnemyType.CRAB -> Enemy(EnemyType.CRAB,200f,100,2,200f,5f, GameScreen.redlaserTexture, 1, -5, timeInit, x, y, 70f, 70f, GameScreen.crab2Texture, false )
        type == EnemyType.OCTOPUS -> Enemy(EnemyType.OCTOPUS,250f,200,3,250f,3f, GameScreen.redlaserTexture, 1, -5, timeInit, x, y, 70f, 70f, GameScreen.octopus3Texture, false)
        else -> Enemy(EnemyType.UFO, 200f, 150, 1,200f,1f, GameScreen.redlaserTexture, 1, -5, 0f, 0f, y, 140f, 70f, GameScreen.ufoTexture, false)
    }

}

fun makeEnemy(type: EnemyType):Enemy{
    return when{
        type == EnemyType.SQUID -> makeEnemy(EnemyType.SQUID, (100..700 step 100).shuffled().first().toFloat(), (300..600 step 100).shuffled().first().toFloat(), (0..7).shuffled().first().toFloat())
        type == EnemyType.CRAB -> makeEnemy(EnemyType.CRAB, (100..700 step 100).shuffled().first().toFloat(), (400..600 step 100).shuffled().first().toFloat(), (0..4).shuffled().first().toFloat())
        else -> makeEnemy(EnemyType.OCTOPUS, (100..700 step 100).shuffled().first().toFloat(), (500..600 step 100).shuffled().first().toFloat(), (0..2).shuffled().first().toFloat())
    }

}

fun makeProjectileUp(makeP: (Int) -> (Projectile)):Projectile{
    return makeP(1)
}

fun makeProjectileDown(makeP: (Int) -> (Projectile)):Projectile{
    return makeP(-1)
}

fun makeProjectile(entity: Enemy): (Int) -> (Projectile){
    return {direction -> Projectile(direction, entity.body.x + entity.body.width / 2 - 2.5f, entity.body.y + entity.body.height, 5f, 10f, entity.shootTexture, false, entity.shootSpeed)}
}

fun makeProjectile(entity: Player): (Int) -> (Projectile){
    return {direction -> Projectile(direction, entity.body.x + entity.body.width / 2 - 2.5f, entity.body.y + entity.body.height, 5f, 10f, entity.shootTexture, false, entity.shootSpeed)}
}