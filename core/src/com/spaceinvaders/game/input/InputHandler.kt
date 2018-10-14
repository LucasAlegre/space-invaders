package com.spaceinvaders.game.input

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

interface InputHandler {

    fun moveLeft(): Boolean
    fun moveRight(): Boolean
    fun moveUp(): Boolean
    fun moveDown(): Boolean
    fun horizontalSpeed(): Float
    fun verticalSpeed(): Float
    fun shoot(): Boolean
}


