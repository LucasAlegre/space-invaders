package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.logic.GameLogic
import com.spaceinvaders.game.screens.GameScreen

class Player : Entity(originX=GameScreen.WIDHT/2 - 50, originY=0f, width=60f, height=60f, texture = GameScreen.playerTexture){

    override var speed: Float = 200f
        set(value){
            field = if(value < 0f) 0f else value
        }
    private val shooter: Shooter = Shooter(300f, 0.5f, GameScreen.bluelaserTexture, 0f)

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
        body.setPosition(originX, originY)
    }
}