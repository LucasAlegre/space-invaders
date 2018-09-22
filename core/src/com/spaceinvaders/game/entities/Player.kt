package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.screens.GameScreen

class Player : Entity(x=GameScreen.WIDHT/2 - 50, y=0f, width=100f, height=100f, texture = GameScreen.playerTexture){

    override lateinit var body: Sprite
    override var speed: Float = 200f
        set(value){
            field = if(value < 0f) 0f else value
        }
    private val shooter: Shooter = Shooter(300f, 0.5f, GameScreen.bluelaserTexture)

    fun shoot() : Projectile?{
        shooter.timeSinceLastShoot += Gdx.graphics.deltaTime

        return if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && shooter.canShoot())
            shooter.shoot(body.x + body.width/2 - 2.5f, body.y + body.height)
        else
            null
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
        else if(body.x > GameScreen.WIDHT - width)
            body.x = GameScreen.WIDHT - width
    }
}