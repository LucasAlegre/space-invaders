package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen

class Octopus(x: Float, y: Float, timeInit: Float): Enemy(x=x, y=y, width=70f, height=70f, texture= GameScreen.octopusTexture, speed=250f, score=200, lives=3, shootDelay=3f, timeInit=timeInit){

    constructor() : this(listOf(100f,200f,300f,400f,500f,600f,700f).shuffled().first(), listOf(500f,600f).shuffled().first(), listOf(0f,1f,2f).shuffled().first())

    override fun hasReachedBottomLimit(): Boolean {
        if(super.hasReachedBottomLimit()) {
            verticalDirection = 0
            return true
        }

        return false
    }

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection

        if((hasReachedLeftLimit() || hasReachedRightLimit()) && verticalDirection != 0) {
            body.y += Gdx.graphics.deltaTime * speed * verticalDirection
            hasReachedBottomLimit()
        }
    }
}