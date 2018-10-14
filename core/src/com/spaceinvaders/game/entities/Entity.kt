package com.spaceinvaders.game.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle

abstract class Entity(val x: Float, val y: Float, val width: Float, val height: Float, val texture: Texture){

    abstract var body: Sprite
    abstract var speed: Float

    init {
        body = Sprite(texture, 0,0, width.toInt(), height.toInt())
        body.setPosition(x, y)
    }

    abstract fun move()

    open fun collides(other: Entity): Boolean {
        return body.boundingRectangle.overlaps(other.body.boundingRectangle)
    }
}