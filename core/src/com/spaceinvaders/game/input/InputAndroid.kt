package com.spaceinvaders.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.spaceinvaders.game.screens.GameScreen
import kotlin.math.abs

class InputAndroid : InputHandler {

    private val accelerometerAvailable: Boolean = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)

    override fun moveLeft(): Boolean{
        return GameScreen.touchpad.knobX < 0
    }

    override fun moveRight(): Boolean{
        return GameScreen.touchpad.knobX > 0
    }

    override fun moveUp(): Boolean{
        return GameScreen.touchpad.knobY > 0
    }

    override fun moveDown(): Boolean{
        return  GameScreen.touchpad.knobY < 0
    }

    override fun horizontalSpeed(): Float {
        return GameScreen.touchpad.knobPercentX * 2.5f
    }

    override fun verticalSpeed(): Float {
        return GameScreen.touchpad.knobPercentY * 2.5f
    }

    override fun shoot(): Boolean{
        return GameScreen.shootBt.isPressed
    }
}