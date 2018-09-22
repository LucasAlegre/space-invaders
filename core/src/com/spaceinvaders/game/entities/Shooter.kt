package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

interface ShooterInterface{
    fun shoot(x: Float, y: Float): Projectile
    fun canShoot(): Boolean
}

class Shooter(val shootSpeed: Float, val shootDelay: Float, val shootTexture: Texture) : ShooterInterface{

    var timeSinceLastShoot = 0f

    override fun shoot(x: Float, y: Float): Projectile {
        timeSinceLastShoot = 0f
        return Projectile(x, y, shootSpeed, shootTexture)
    }

    override fun canShoot(): Boolean{
        return timeSinceLastShoot > shootDelay
    }
}