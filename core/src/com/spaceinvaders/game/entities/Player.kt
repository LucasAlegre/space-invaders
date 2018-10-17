package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.GameLogic
import com.spaceinvaders.game.input.*
import com.spaceinvaders.game.screens.GameScreen

class Player : Entity(x=GameScreen.WIDHT/2 - 50, y=0f, width=100f, height=100f, texture = GameScreen.playerTexture){

    override var speed: Float = 200f
        set(value){
            field = if(value < 0f) 0f else value
        }
    private val shooter: Shooter = Shooter(300f, 0.5f, GameScreen.bluelaserTexture)

    fun shoot() : Projectile?{
        shooter.timeSinceLastShoot += Gdx.graphics.deltaTime

        return if(GameLogic.inputHandler.shoot() && shooter.canShoot())
            shooter.shoot(body.x + body.width/2 - 2.5f, body.y + body.height)
        else
            null
    }

    override fun move() {

        if(GameLogic.inputHandler.moveRight())
            body.x += Gdx.graphics.deltaTime * speed * GameLogic.inputHandler.horizontalSpeed()
        else if(GameLogic.inputHandler.moveLeft())
            body.x -= Gdx.graphics.deltaTime * speed * GameLogic.inputHandler.horizontalSpeed()

        if(GameLogic.inputHandler.moveUp())
            body.y += Gdx.graphics.deltaTime * speed * GameLogic.inputHandler.verticalSpeed()
        else if(GameLogic.inputHandler.moveDown())
            body.y -= Gdx.graphics.deltaTime * speed * GameLogic.inputHandler.verticalSpeed()

        hasReachedLeftLimit()
        hasReachedRightLimit()

        hasReachedBottomLimit()
        hasReachedUpperLimit()
    }

    fun resetPosition(){
        body.setPosition(GameScreen.WIDHT/2 - 50, 0f)
    }
}