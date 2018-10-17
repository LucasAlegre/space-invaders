package com.spaceinvaders.game.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle
import com.spaceinvaders.game.screens.GameScreen

abstract class Entity(val x: Float, val y: Float, val width: Float, val height: Float, val texture: Texture){

    var body: Sprite
    abstract var speed: Float

    init {
        body = Sprite(texture, 0,0, width.toInt(), height.toInt())
        body.setPosition(x, y)
    }

    abstract fun move()

    open fun collides(other: Entity): Boolean {
        return body.boundingRectangle.overlaps(other.body.boundingRectangle)
    }

    open fun hasReachedLeftLimit(): Boolean {
        if (body.x < 0f) {
            body.x = 0f
            return true
        }

        return false
    }

    open fun hasReachedRightLimit(): Boolean {
        if(body.x > GameScreen.WIDHT - width) {
            body.x = GameScreen.WIDHT - width
            return true
        }

        return false
    }

    open fun hasReachedBottomLimit(): Boolean {
        if (body.y < 0f) {
            body.y = 0f
            return true
        }

        return false
    }

    open fun hasReachedUpperLimit(): Boolean {
        if(body.y > GameScreen.HEIGHT - height) {
            body.y = GameScreen.HEIGHT - height
            return true
        }

        return false
    }
}