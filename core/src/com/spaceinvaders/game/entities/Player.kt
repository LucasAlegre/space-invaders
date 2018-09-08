package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.screens.GameScreen

class Player : Entity(x=GameScreen.WIDHT/2 - 50, y=0f, width=100f, height=100f){

    override val texture: Texture = GameScreen.playerTexture
    override lateinit var body: Sprite
    override var speed: Float = 200f

    init {
        body = Sprite(texture, x.toInt(), y.toInt(), width.toInt(), height.toInt())
        body.x = x
        body.y = y
    }

    override fun move() {

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            body.x += Gdx.graphics.deltaTime * speed
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            body.x -= Gdx.graphics.deltaTime * speed
        }
        if (body.x < 0f)
            body.x = 0f
        else if(body.x > GameScreen.HEIGHT - height)
            body.x = GameScreen.HEIGHT - height
    }
}