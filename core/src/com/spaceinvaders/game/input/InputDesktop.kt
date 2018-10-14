package com.spaceinvaders.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

class InputDesktop : InputHandler {

    override fun moveLeft(): Boolean{
        return Gdx.input.isKeyPressed(Input.Keys.LEFT)
    }

    override fun moveRight(): Boolean{
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT)
    }

    override fun moveUp(): Boolean{
        return Gdx.input.isKeyPressed(Input.Keys.UP)
    }

    override fun moveDown(): Boolean{
        return Gdx.input.isKeyPressed(Input.Keys.DOWN)
    }

    override fun horizontalSpeed(): Float {
        return 1f
    }

    override fun verticalSpeed(): Float {
        return 1f
    }

    override fun shoot(): Boolean{
        return Gdx.input.isKeyPressed(Input.Keys.SPACE)
    }
}