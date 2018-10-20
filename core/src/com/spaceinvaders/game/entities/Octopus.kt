package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen

class Octopus(x: Float, y: Float, timeInit: Float): Enemy(x=x, y=y, width=70f, height=70f, texture= GameScreen.octopus3Texture, speed=250f, score=200, lives=3, shootDelay=3f, timeInit=timeInit){

    constructor() : this((100..700 step 100).shuffled().first().toFloat(), (500..600 step 100).shuffled().first().toFloat(), (0..2).shuffled().first().toFloat())

    override fun takeShot() {
        lives--
        shouldDelete = lives == 0
        if(lives == 2)
            texture = GameScreen.octopus2Texture
        else
            texture = GameScreen.octopusTexture
    }

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