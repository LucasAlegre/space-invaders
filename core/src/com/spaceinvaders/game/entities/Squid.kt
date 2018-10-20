package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen

class Squid(x: Float, y: Float, timeInit:Float): Enemy(x=x, y=y, width=70f, height=70f, texture= GameScreen.squidTexture, speed=100f, score=50, lives=1, shootDelay=8f, timeInit=timeInit){

    constructor() : this(listOf(100f,200f,300f,400f,500f,600f,700f).shuffled().first(), listOf(300f,400f,500f,600f).shuffled().first(), listOf(0f,1f,2f,3f,4f,5f,6f,7f).shuffled().first())

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection

        hasReachedLeftLimit()
        hasReachedRightLimit()
    }
}