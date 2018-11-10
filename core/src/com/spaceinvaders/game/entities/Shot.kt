package com.spaceinvaders.game.logic

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.logic.*


//// Funções de colisão
fun enemyCollidesWithPlayer(enemy:Enemy): (Player) -> (Boolean) {
    return {player -> enemy.body.boundingRectangle.overlaps(player.body.boundingRectangle)}
}

fun enemyCollidesWithProjectile(enemy:Enemy): (Projectile) -> (Boolean) {
    return {projectile -> enemy.body.boundingRectangle.overlaps(projectile.body.boundingRectangle)}
}

fun projectileCollidesWithPlayer(projectile:Projectile): (Player) -> (Boolean) {
    return {player -> projectile.body.boundingRectangle.overlaps(player.body.boundingRectangle)}
}

fun takeShot(enemy: Enemy) : Enemy {
    enemy.lives--
    enemy.shouldDelete = enemy.lives == 0
    return when{
        enemy.type == EnemyType.CRAB -> {
            enemy.texture = GameScreen.crabTexture
            enemy
        }
        enemy.type == EnemyType.OCTOPUS -> {
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
        makeProjectile(enemy.body.x + enemy.body.width / 2 - 2.5f, enemy.body.y + enemy.body.height, enemy.shootSpeed, enemy.shootTexture, -1)
    }
    else
        null
}

fun shoot(player:Player) :  Projectile? {
    player.timeSinceLastShoot += Gdx.graphics.deltaTime

    return if (inputHandler.shoot() && canShoot(player)){
        player.timeSinceLastShoot = 0f
        makeProjectile(player.body.x + player.body.width / 2 - 2.5f, player.body.y + player.body.height, player.shootSpeed, player.shootTexture, 1)
    }
    else
        null
}