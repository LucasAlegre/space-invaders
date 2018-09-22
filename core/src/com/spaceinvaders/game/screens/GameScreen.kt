package com.spaceinvaders.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.spaceinvaders.game.GameLogic
import com.spaceinvaders.game.SpaceInvaders
import com.spaceinvaders.game.entities.Player
import javax.xml.soap.Text


class GameScreen(val game : SpaceInvaders) : Screen {

    var camera: OrthographicCamera
        private set

    var gameLogic: GameLogic
        private set

    companion object {
        val WIDHT: Float = Gdx.graphics.width.toFloat()
        val HEIGHT: Float = Gdx.graphics.height.toFloat()

        lateinit var assetManager: AssetManager
        lateinit var playerTexture: Texture
        lateinit var backgroundTexture: Texture
        lateinit var bluelaserTexture: Texture
        lateinit var diedSound: Sound
    }

    init {
        camera = OrthographicCamera()
        camera.setToOrtho(false, GameScreen.WIDHT, GameScreen.HEIGHT)

        assetManager = AssetManager()
        assetManager.load("spaceship.png", Texture::class.java)
        assetManager.load("space.png", Texture::class.java)
        assetManager.load("arco.mp3", Sound::class.java)
        assetManager.load("bluelaser.png", Texture::class.java)
        assetManager.finishLoading()
        playerTexture = assetManager.get("spaceship.png")
        backgroundTexture = assetManager.get("space.png")
        diedSound = assetManager.get("arco.mp3")
        bluelaserTexture = assetManager.get("bluelaser.png")

        gameLogic = GameLogic()
    }

    override fun render(delta: Float) {

        Gdx.gl.glClearColor(0f, 0f, 0.0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        game.batch.setProjectionMatrix(camera.combined)

        game.batch.begin()

        game.batch.disableBlending()
        game.batch.draw(backgroundTexture, 0f, 0f, WIDHT, HEIGHT)

        gameLogic.update()

        game.batch.enableBlending()

        for(element in gameLogic.getAllElements())
            game.batch.draw(element.texture, element.body.x, element.body.y, element.body.width, element.body.height)

        game.font.draw(game.batch, "Score: ${gameLogic.score}", 5f, WIDHT - 5)

        game.batch.end()
    }

    override fun pause() {}
    override fun hide() {}
    override fun show() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}

    override fun dispose() {
        assetManager.dispose()
    }
}