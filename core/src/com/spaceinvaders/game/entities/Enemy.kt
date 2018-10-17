package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.GameLogic
import com.spaceinvaders.game.screens.GameScreen

abstract class Enemy(x: Float, y: Float, width: Float, height: Float, texture: Texture, speed: Float, score: Int, lives: Int, shootDelay: Float) : Entity(x=x, y=y, width=width, height=height, texture=texture){

    override var speed: Float = speed
        set(value) {
            field = if(value < 0f) 0f else value
        }

    var score: Int = score
        set(value) {
            field = if(value < 0) 0 else value
        }

    var lives: Int = lives
        set(value) {
            field = if(value < 1) 1 else value
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

    protected var verticalDirection: Int = -1
        set(value) {
            if(value < -1)
                field = -1
            else if(value > 1)
                field = 1
            else
                field = value
        }

    var shouldDelete: Boolean = false

    private val shooter: Shooter = Shooter(speed, shootDelay, GameScreen.redlaserTexture)

    open fun shoot() : Projectile? {
        shooter.timeSinceLastShoot += Gdx.graphics.deltaTime

        return if(shooter.canShoot())
            shooter.shoot(body.x + body.width/2 - 2.5f, body.y + body.height, -1)
        else
            null
    }

    open fun takeShot() {
        lives -= 1
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
            verticalDirection = 1
            return true
        }

        return false
    }

    override fun hasReachedUpperLimit(): Boolean {
        if(super.hasReachedUpperLimit()) {
            verticalDirection = -1
            return true
        }

        return false
    }

    abstract override fun move()
}

class Squid(x: Float, y: Float): Enemy(x=x, y=y, width=8f, height=8f, texture=GameScreen.squidTexture, speed=100f, score=10, lives=1, shootDelay=3f){

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection

        hasReachedLeftLimit()
        hasReachedRightLimit()
    }
}

class Crab(x: Float, y: Float): Enemy(x=x, y=y, width=11f, height=8f, texture=GameScreen.crabTexture, speed=200f, score=20, lives=2, shootDelay=2f){

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection

        if(hasReachedLeftLimit() || hasReachedRightLimit()) {
            body.y += Gdx.graphics.deltaTime * speed * verticalDirection

            hasReachedBottomLimit()
            hasReachedUpperLimit()
        }
    }
}

class Octopus(x: Float, y: Float): Enemy(x=x, y=y, width=12f, height=8f, texture=GameScreen.octopusTexture, speed=250f, score=40, lives=3, shootDelay=1.5f){

    override fun hasReachedBottomLimit(): Boolean {
        if(super.hasReachedBottomLimit()) {
            verticalDirection = 0
            return true
        }

        return false
    }

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection

        if((hasReachedLeftLimit() || hasReachedRightLimit()) && verticalDirection != 0) {
            body.y += Gdx.graphics.deltaTime * speed * verticalDirection
            hasReachedBottomLimit()
        }
    }
}

class UFO(y: Float): Enemy(x=0f, y=y, width=12f, height=8f, texture=GameScreen.ufoTexture, speed=300f, score=150, lives=3, shootDelay=1f){

    override fun hasReachedRightLimit(): Boolean {
        if(super.hasReachedRightLimit()) {
            shouldDelete = true
            return true
        }

        return false
    }

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection
        hasReachedRightLimit()
    }
}