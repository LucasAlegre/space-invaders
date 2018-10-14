package com.spaceinvaders.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.spaceinvaders.game.screens.GameScreen
import com.spaceinvaders.game.screens.MenuScreen

class SpaceInvaders : Game() {

    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()
        // use LibGDX's default Arial font
        font = BitmapFont()
        font.data.setScale(2f,2f)
        this.setScreen(MenuScreen(this))
    }

    override fun render() {
        super.render()  // important!
    }

    override fun dispose() {
        // per @rohansuri's suggestion here:
        //    https://gist.github.com/sinistersnare/6367829#gistcomment-1661438
        this.getScreen().dispose()

        batch.dispose()
        font.dispose()
    }
}
