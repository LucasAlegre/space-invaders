package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.screens.GameScreen

class Projectile(x: Float, y: Float, speed: Float, texture: Texture) : Entity(x=x, y=y, width = 5f, height = 10f, texture = texture){

    override lateinit var body: Sprite
    override var speed: Float = speed
    var shouldDelete: Boolean = false

    override fun move() {
        body.y += speed * Gdx.graphics.deltaTime

        if(body.y > GameScreen.HEIGHT || body.y < 0)
            shouldDelete = true
    }
}