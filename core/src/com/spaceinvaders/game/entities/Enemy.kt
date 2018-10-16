package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.screens.GameScreen

class Enemy(x: Float, y: Float, width: Float, height: Float, texture: Texture, speed: Float, strength: Float) : Entity(x=x, y=y, width=width, height=height, texture=texture){

    override lateinit var body: Sprite
    override var speed: Float = speed
        set(value) {
            field = if(value < 0f) 0f else value
        }

    var strength: Float = strength
        set(value) {
            field = if(value < 0f) 0f else value
        }

    private val shooter: Shooter = Shooter(speed, 0.5f, GameScreen.redlaserTexture)

    fun shoot() : Projectile?{
        shooter.timeSinceLastShoot += Gdx.graphics.deltaTime

        return if(shooter.canShoot())
            shooter.shoot(body.x + body.width/2 - 2.5f, body.y + body.height, -1)
        else
            null
    }

    override fun move() {
        // TODO: random move
    }

}