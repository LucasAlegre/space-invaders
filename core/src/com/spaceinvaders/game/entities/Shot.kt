package com.spaceinvaders.game.logic

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.logic.*

//// Funções de colisão
fun collides(enemy: Enemy, player: Player): Boolean {
    return enemy.body.boundingRectangle.overlaps(player.body.boundingRectangle)
}

fun collides(enemy: Enemy, projectile: Projectile): Boolean {
    return enemy.body.boundingRectangle.overlaps(projectile.body.boundingRectangle)
}

fun collides(projectile: Projectile, player: Player): Boolean {
    return projectile.body.boundingRectangle.overlaps(player.body.boundingRectangle)
}

fun enemyCollidesWithPlayer(enemy: Enemy): (Player) -> (Boolean) {
    return {player -> collides(enemy, player)}
}

fun enemyCollidesWithProjectile(enemy: Enemy): (Projectile) -> (Boolean) {
    return {projectile -> collides(enemy, projectile)}
}

fun projectileCollidesWithPlayer(projectile: Projectile): (Player) -> (Boolean) {
    return {player -> collides(projectile, player)}
}

fun takeShot(enemy: Enemy) : Enemy {
    enemy.lives--
    enemy.shouldDelete = enemy.lives == 0
    return when(enemy.type){
        EnemyType.CRAB -> {
            enemy.texture = GameScreen.crabTexture
            enemy
        }
        EnemyType.OCTOPUS -> {
            if(enemy.lives == 2)
                enemy.texture = GameScreen.octopus2Texture
            else
                enemy.texture = GameScreen.octopusTexture
            enemy
        }
        else -> {
            enemy
        }
    }
}

fun canShoot(player: Player): Boolean{
    return player.timeSinceLastShoot > player.shootDelay
}

fun canShoot(enemy: Enemy): Boolean{
    return enemy.timeSinceLastShoot > enemy.shootDelay
}

fun shoot(enemy: Enemy) : Projectile? {
    enemy.timeSinceLastShoot += Gdx.graphics.deltaTime

    return if(canShoot(enemy)) {
        enemy.timeSinceLastShoot = 0f
        makeProjectileDown(makeProjectile(enemy))
    }
    else
        null
}

fun shoot(player:Player) :  Projectile? {
    player.timeSinceLastShoot += Gdx.graphics.deltaTime

    return if (inputHandler.shoot() && canShoot(player)){
        player.timeSinceLastShoot = 0f
        makeProjectileUp(makeProjectile(player))
    }
    else
        null
}