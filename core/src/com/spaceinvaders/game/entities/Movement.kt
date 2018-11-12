package com.spaceinvaders.game.logic

import com.spaceinvaders.game.screens.GameScreen
import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.logic.*

//// Movimentação do projétil
fun move(projectile: Projectile): Projectile {
    projectile.body.y += projectile.speed * Gdx.graphics.deltaTime * projectile.direction

    if(projectile.body.y > GameScreen.HEIGHT || projectile.body.y < 0)
        projectile.shouldDelete = true

    return projectile
}

//// Movimentação do jogador
fun updateLimits(player: Player): Player {
    if (player.body.x < 0f)
        player.body.x = 0f
    else if(player.body.x > GameScreen.WIDHT - player.width)
        player.body.x = GameScreen.WIDHT - player.width

    if (player.body.y < 0f)
        player.body.y = 0f
    else if(player.body.y > GameScreen.HEIGHT - player.height)
        player.body.y = GameScreen.HEIGHT - player.height

    return player
}

fun move(player: Player): Player {

    if(inputHandler.moveRight())
        player.body.x += Gdx.graphics.deltaTime * player.speed * inputHandler.horizontalSpeed()
    else if(inputHandler.moveLeft())
        player.body.x -= Gdx.graphics.deltaTime * player.speed * inputHandler.horizontalSpeed()

    if(inputHandler.moveUp())
        player.body.y += Gdx.graphics.deltaTime * player.speed * inputHandler.verticalSpeed()
    else if(inputHandler.moveDown())
        player.body.y -= Gdx.graphics.deltaTime * player.speed * inputHandler.verticalSpeed()

    return updateLimits(player)
}

fun resetPosition(player: Player): Player{
    player.body.setPosition(player.originX, player.originY)
    return player
}

//// Movimentação do inimigo
fun hasReachedLeftLimit(enemy: Enemy): Boolean {
    return enemy.body.x < 0f
}

fun hasReachedRightLimit(enemy: Enemy): Boolean  {
    return enemy.body.x > GameScreen.WIDHT - enemy.width
}

fun hasReachedBottomLimit(enemy: Enemy): Boolean {
    return enemy.body.y < 0f
}

fun hasReachedUpperLimit(enemy: Enemy): Boolean {
    return enemy.body.y > GameScreen.HEIGHT - enemy.height
}

fun updateLeftLimit(enemy: Enemy): Enemy {
    if (hasReachedLeftLimit(enemy)) {
        enemy.body.x = 0f
        enemy.horizontalDirection = 1
    }
    return enemy
}

fun updateRightLimit(enemy: Enemy): Enemy {
    if(hasReachedRightLimit(enemy)) {
        enemy.body.x = GameScreen.WIDHT - enemy.width
        enemy.horizontalDirection = -1
    }
    return enemy
}

fun updateBottomLimit(enemy: Enemy): Enemy {
    if (hasReachedBottomLimit(enemy)) {
        enemy.body.y = 0f
        enemy.verticalDirection = 5
    }
    return enemy
}

fun updateUpperLimit(enemy: Enemy): Enemy {
    if(hasReachedUpperLimit(enemy)) {
        enemy.body.y = GameScreen.HEIGHT - enemy.height
        enemy.verticalDirection = -5
    }
    return enemy
}

fun move(enemy: Enemy): Enemy{
    var enemy = enemy
    return when (enemy.type){
        EnemyType.SQUID -> {
            enemy.body.x += Gdx.graphics.deltaTime * enemy.speed * enemy.horizontalDirection

            updateLeftLimit(updateRightLimit(enemy))
        }
        EnemyType.CRAB -> {
            enemy.body.x += Gdx.graphics.deltaTime * enemy.speed * enemy.horizontalDirection

            if(hasReachedLeftLimit(enemy) || hasReachedRightLimit(enemy)) {
                enemy = updateLeftLimit(enemy)
                enemy = updateRightLimit(enemy)

                enemy.body.y += Gdx.graphics.deltaTime * enemy.speed * enemy.verticalDirection

                enemy = updateBottomLimit(enemy)
                enemy = updateUpperLimit(enemy)

            }
            enemy
        }
        EnemyType.OCTOPUS -> {
            enemy.body.x += Gdx.graphics.deltaTime * enemy.speed * enemy.horizontalDirection

            if((hasReachedLeftLimit(enemy) || hasReachedRightLimit(enemy)) && enemy.verticalDirection != 0) {
                enemy = updateLeftLimit(enemy)
                enemy = updateRightLimit(enemy)

                enemy.body.y += Gdx.graphics.deltaTime * enemy.speed * enemy.verticalDirection

                if (hasReachedBottomLimit(enemy)) {
                    enemy.verticalDirection = 0
                }

            }
            enemy
        }
        EnemyType.UFO -> {
            enemy.body.x += Gdx.graphics.deltaTime * enemy.speed * enemy.horizontalDirection
            if(hasReachedRightLimit(enemy)){
                enemy.shouldDelete = true
            }
            enemy
        }
    }
}