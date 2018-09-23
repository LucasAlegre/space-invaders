package com.spaceinvaders.game.InputHandler

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

class InputAndroid : InputHandler{

    private val accelerometerAvailable: Boolean = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)

    override fun moveLeft(): Float{
        return if (accelerometerAvailable) {
            Gdx.input.accelerometerX
        } else 0f
    }

    override fun moveRight(): Float{
        return if (accelerometerAvailable) {
            -Gdx.input.accelerometerX
        } else 0f
    }

    override fun shoot(): Boolean{
        return Gdx.input.isTouched
    }
}