package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen

class Squid(x: Float, y: Float): Enemy(x=x, y=y, width=100f, height=100f, texture= GameScreen.squidTexture, speed=100f, score=10, lives=1, shootDelay=3f){

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection

        hasReachedLeftLimit()
        hasReachedRightLimit()
    }
}