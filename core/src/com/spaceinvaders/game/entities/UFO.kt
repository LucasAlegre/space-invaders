package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen


class UFO(y: Float): Enemy(x=0f, y=y, width=140f, height=70f, texture= GameScreen.ufoTexture, speed=200f, score=300, lives=1, shootDelay=1f, timeInit=0f){

    override fun hasReachedRightLimit(): Boolean {
        if(super.hasReachedRightLimit()) {
            shouldDelete = true
            return true
        }

        return false
    }

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection
        hasReachedRightLimit()
    }
}

