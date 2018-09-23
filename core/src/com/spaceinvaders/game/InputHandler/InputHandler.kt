package com.spaceinvaders.game.InputHandler

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

interface InputHandler {

    fun moveLeft(): Float
    fun moveRight(): Float
    fun shoot(): Boolean
}


