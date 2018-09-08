package com.spaceinvaders.game.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle

abstract class Entity(val x: Float, val y: Float, val width: Float, val height: Float){

    abstract var body: Sprite
    abstract val texture: Texture
    abstract var speed: Float

    abstract fun move()
}