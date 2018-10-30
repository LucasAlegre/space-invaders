package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.logic.*
import com.spaceinvaders.game.screens.GameScreen

class Player : Entity(originX=GameScreen.WIDHT/2 - 50, originY=0f, width=60f, height=60f, texture = GameScreen.playerTexture){

    override var speed: Float = 200f
        set(value){
            field = if(value < 0f) 0f else value
        }
    private val shooter: Shooter = Shooter(300f, 0.5f, GameScreen.bluelaserTexture, 0f)

    fun shoot() : Projectile?{
        shooter.timeSinceLastShoot += Gdx.graphics.deltaTime

        return if(inputHandler.shoot() && shooter.canShoot())
            shooter.shoot(body.x + body.width/2 - 2.5f, body.y + body.height)
        else
            null
    }

    override fun move() {

        if(inputHandler.moveRight())
            body.x += Gdx.graphics.deltaTime * speed * inputHandler.horizontalSpeed()
        else if(inputHandler.moveLeft())
            body.x -= Gdx.graphics.deltaTime * speed * inputHandler.horizontalSpeed()

        if(inputHandler.moveUp())
            body.y += Gdx.graphics.deltaTime * speed * inputHandler.verticalSpeed()
        else if(inputHandler.moveDown())
            body.y -= Gdx.graphics.deltaTime * speed * inputHandler.verticalSpeed()

        hasReachedLeftLimit()
        hasReachedRightLimit()

        hasReachedBottomLimit()
        hasReachedUpperLimit()
    }

    fun resetPosition(){
        body.setPosition(originX, originY)
    }
}