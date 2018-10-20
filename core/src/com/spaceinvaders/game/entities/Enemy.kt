package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.spaceinvaders.game.screens.GameScreen

abstract class Enemy(x: Float, y: Float, width: Float, height: Float, texture: Texture, speed: Float, score: Int, lives: Int, shootDelay: Float, timeInit: Float) : Entity(originX=x, originY=y, width=width, height=height, texture=texture){

    override var speed: Float = speed
        set(value) {
            field = if(value < 0f) 0f else value
        }

    val score: Int = score

    var lives: Int = lives
        set(value) {
            field = if(value < 0) 0 else value
        }

    protected var horizontalDirection: Int = 1
        set(value) {
            if(value < -1)
                field = -1
            else if(value > 1)
                field = 1
            else
                field = value
        }

    protected var verticalDirection: Int = -5
        set(value) {
            if(value < -5)
                field = -5
            else if(value > 5)
                field = 5
            else
                field = value
        }

    var shouldDelete: Boolean = false

    private val shooter: Shooter = Shooter(speed, shootDelay, GameScreen.redlaserTexture, timeInit)

    open fun shoot() : Projectile? {
        shooter.timeSinceLastShoot += Gdx.graphics.deltaTime

        return if(shooter.canShoot())
            shooter.shoot(body.x + body.width/2 - 2.5f, body.y + body.height, -1)
        else
            null
    }

    open fun takeShot() {
        lives--
        shouldDelete = lives == 0
    }

    override fun hasReachedLeftLimit(): Boolean {
        if (super.hasReachedLeftLimit()) {
            horizontalDirection = 1
            return true
        }

        return false
    }

    override fun hasReachedRightLimit(): Boolean {
        if(super.hasReachedRightLimit()) {
            horizontalDirection = -1
            return true
        }

        return false
    }

    override fun hasReachedBottomLimit(): Boolean {
        if(body.y < 130f) {
            verticalDirection = 5
            return true
        }

        return false
    }

    override fun hasReachedUpperLimit(): Boolean {
        if(super.hasReachedUpperLimit()) {
            verticalDirection = -5
            return true
        }

        return false
    }
}