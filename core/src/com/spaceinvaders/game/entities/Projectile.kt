package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.spaceinvaders.game.screens.GameScreen
import java.lang.IllegalArgumentException

class Projectile(x: Float, y: Float, speed: Float, texture: Texture, direction: Int = 1) : Entity(originX=x, originY=y, width = 5f, height = 10f, texture = texture){

    override var speed: Float = speed
    var shouldDelete: Boolean = false

    // Negative value makes projectile go down
    // Positive value makes projectile go up (default)
    var direction: Int = direction
        set(value) {
            if(value < -1)
                field = -1
            else if(value > 1)
                field = 1
            else
                throw IllegalArgumentException("Direction should not be zero.")
        }

    override fun move() {
        body.y += speed * Gdx.graphics.deltaTime * direction

        if(body.y > GameScreen.HEIGHT || body.y < 0)
            shouldDelete = true
    }
}