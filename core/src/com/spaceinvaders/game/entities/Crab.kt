package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen

class Crab(x: Float, y: Float, timeInit: Float): Enemy(x=x, y=y, width=70f, height=70f, texture= GameScreen.crab2Texture, speed=200f, score=100, lives=2, shootDelay=5f, timeInit=timeInit){

    constructor() : this((100..700 step 100).shuffled().first().toFloat(), (400..600 step 100).shuffled().first().toFloat(), (0..4).shuffled().first().toFloat())

    override fun takeShot() {
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