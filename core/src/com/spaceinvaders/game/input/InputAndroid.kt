package com.spaceinvaders.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.spaceinvaders.game.logic.GameLogic
import com.spaceinvaders.game.screens.GameScreen
import kotlin.math.abs

class InputAndroid : InputHandler {

    private val accelerometerAvailable: Boolean = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)
    private var touchPos: Vector3 = Vector3()
    private val maxDistance: Float = 500f * Gdx.graphics.deltaTime

    fun getDelta(): Vector2{
        if(Gdx.input.isTouched){
            touchPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            GameScreen.camera.unproject(touchPos)
            val tmp = Vector2()
            tmp.set(touchPos.x, touchPos.y).sub(GameLogic.player.body.x, GameLogic.player.body.y)
            if(tmp.len() < 15)
                return Vector2(0f,0f)
            return tmp
        }
        else return Vector2(0f,0f)
    }

    override fun moveLeft(): Boolean{
        return getDelta().x < 0
    }

    override fun moveRight(): Boolean{
        return getDelta().x > 0
    }

    override fun moveUp(): Boolean{
        return getDelta().y > 0
    }

    override fun moveDown(): Boolean{
        return getDelta().y < 0
    }

    override fun horizontalSpeed(): Float {
        return abs(getDelta().nor().scl(maxDistance).x)
    }

    override fun verticalSpeed(): Float {
        return abs(getDelta().nor().scl(maxDistance).y)
    }

    override fun shoot(): Boolean{
        return Gdx.input.isTouched
    }
}