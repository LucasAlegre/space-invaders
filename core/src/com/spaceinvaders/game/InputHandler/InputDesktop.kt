package com.spaceinvaders.game.InputHandler

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

class InputDesktop : InputHandler{

    override fun moveLeft(): Float{
        return if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) 1f else 0f
    }

    override fun moveRight(): Float{
        return if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 1f else 0f
    }

    override fun shoot(): Boolean{
        return Gdx.input.isKeyPressed(Input.Keys.SPACE)
    }
}