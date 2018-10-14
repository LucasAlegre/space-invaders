package com.spaceinvaders.game.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.spaceinvaders.game.GameLogic
import com.spaceinvaders.game.SpaceInvaders
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import java.awt.geom.FlatteningPathIterator


class MenuScreen(val game: SpaceInvaders) : Screen {

    private val stage: Stage
    private val textureBackground: Texture = Texture(Gdx.files.internal("space.png"))
    private val menuSong: Music = Gdx.audio.newMusic(Gdx.files.internal("menusong.wav"))

    init {
        stage = Stage()
        stage.setDebugAll(true)
        Gdx.input.setInputProcessor(stage)

        var skin = Skin()
        var tex1 = Texture("alien.png")
        skin.add("up", TextureRegion(tex1, 0, 0, 200, 100))
        skin.add("down", TextureRegion(tex1, 0, 96, 200, 100))

        var playButton = ImageTextButton("START", with(ImageTextButton.ImageTextButtonStyle()){
            up = skin.getDrawable("up")
            down = skin.getDrawable("down")
            font = BitmapFont()
            font.data.setScale(3f)
            font.color = Color.BLACK
            this
        })
        playButton.setPosition(GameScreen.WIDHT/2 - 100,GameScreen.HEIGHT/2)
        playButton.addListener(object : ClickListener(){
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                menuSong.stop()
                menuSong.dispose()
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

        stage.batch.begin()
        stage.batch.draw(textureBackground, 0f, 0f, GameScreen.WIDHT, GameScreen.HEIGHT)
        stage.batch.end()
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