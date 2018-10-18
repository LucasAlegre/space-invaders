package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen

class Octopus(x: Float, y: Float): Enemy(x=x, y=y, width=100f, height=100f, texture= GameScreen.octopusTexture, speed=250f, score=40, lives=3, shootDelay=1.5f){

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