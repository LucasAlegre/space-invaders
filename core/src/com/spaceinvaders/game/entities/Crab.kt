package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen

class Crab(x: Float, y: Float, timeInit: Float): Enemy(x=x, y=y, width=70f, height=70f, texture= GameScreen.crab2Texture, speed=200f, score=100, lives=2, shootDelay=5f, timeInit=timeInit){

    constructor() : this(listOf(100f,200f,300f,400f,500f,600f,700f).shuffled().first(), listOf(400f,500f,600f).shuffled().first(), listOf(0f,1f,2f,3f,4f).shuffled().first())

    override open fun takeShot() {
        lives--
        shouldDelete = lives == 0
        texture = GameScreen.crabTexture
    }

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection

        if(hasReachedLeftLimit() || hasReachedRightLimit()) {
            body.y += Gdx.graphics.deltaTime * speed * verticalDirection

            hasReachedBottomLimit()
            hasReachedUpperLimit()
        }
    }
}