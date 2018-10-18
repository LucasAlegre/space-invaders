package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.spaceinvaders.game.screens.GameScreen

class Crab(x: Float, y: Float): Enemy(x=x, y=y, width=100f, height=100f, texture= GameScreen.crabTexture, speed=200f, score=20, lives=2, shootDelay=2f){

    override fun move() {
        body.x += Gdx.graphics.deltaTime * speed * horizontalDirection

        if(hasReachedLeftLimit() || hasReachedRightLimit()) {
            body.y += Gdx.graphics.deltaTime * speed * verticalDirection

            hasReachedBottomLimit()
            hasReachedUpperLimit()
        }
    }
}