package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen

class Squid(x: Float, y: Float, timeInit:Float): Enemy(x=x, y=y, width=70f, height=70f, texture= GameScreen.squidTexture, speed=100f, score=50, lives=1, shootDelay=8f, timeInit=timeInit){

    constructor() : this((100..700 step 100).shuffled().first().toFloat(), (300..600 step 100).shuffled().first().toFloat(), (0..7).shuffled().first().toFloat())

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection

        hasReachedLeftLimit()
        hasReachedRightLimit()
    }
}