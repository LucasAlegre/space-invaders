package com.spaceinvaders.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.spaceinvaders.game.SpaceInvaders
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener


class EndScreen(val game: SpaceInvaders, val score : Int, val seconds : Long) : Screen {

    private val stage: Stage
    private val font: BitmapFont
    private val textureBackground: Texture = Texture(Gdx.files.internal("space-1.png"))
    private val menuSong: Music = Gdx.audio.newMusic(Gdx.files.internal("menusong.wav"))

    init {
        stage = Stage()
        font = BitmapFont()
        stage.setDebugAll(true)
        Gdx.input.setInputProcessor(stage)

        var playButton = TextButton("PLAY AGAIN", with(TextButton.TextButtonStyle()){
            font = game.font
            this
        })
        playButton.setPosition(Gdx.graphics.width/2f - 220f,Gdx.graphics.height/2f)
        playButton.addListener(object : ClickListener(){
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                menuSong.stop()
                menuSong.dispose()
                stage.clear()
                game.screen = GameScreen(game)
            }
        })
        stage.addActor(playButton)

        menuSong.play()
        menuSong.isLooping = true
    }

    override fun render(delta: Float) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        game.batch.begin()
        game.batch.disableBlending()
        game.batch.draw(textureBackground, 0f, 0f, GameScreen.WIDHT, GameScreen.HEIGHT)
        game.batch.enableBlending()
        game.font.data.setScale(0.5f, 0.5f)
        game.font.draw(game.batch, "Final score: ${score}", 250f, GameScreen.HEIGHT - 200f)
        game.font.draw(game.batch, "Time: ${seconds}", 250f, GameScreen.HEIGHT - 100f)

        game.font.data.setScale(1f, 1f)
        game.batch.end()

        stage.draw()
        stage.act()
    }

    override fun pause() {}
    override fun hide() {}
    override fun show() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}
    override fun dispose() {}
}