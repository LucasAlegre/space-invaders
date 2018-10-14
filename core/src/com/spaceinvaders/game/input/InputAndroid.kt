package com.spaceinvaders.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import kotlin.math.abs

class InputAndroid : InputHandler {

    private val accelerometerAvailable: Boolean = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)

    override fun moveLeft(): Boolean{
        return Gdx.input.accelerometerX > 0
    }

    override fun moveRight(): Boolean{
        return Gdx.input.accelerometerX < 0
    }

    override fun moveUp(): Boolean{
        return Gdx.input.accelerometerY < 5.5f
    }

    override fun moveDown(): Boolean{
        return Gdx.input.accelerometerY > 5.5f
    }

    override fun horizontalSpeed(): Float {
        return abs(Gdx.input.accelerometerX)
    }

    override fun verticalSpeed(): Float {
        return abs(Gdx.input.accelerometerY - 5.5f)
    }

    override fun shoot(): Boolean{
        return Gdx.input.isTouched
    }
}