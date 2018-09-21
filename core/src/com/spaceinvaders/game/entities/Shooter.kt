package com.spaceinvaders.game.entities

import com.badlogic.gdx.Gdx

interface ShooterInterface{
    fun shoot(x: Float, y: Float): Projectile
    fun canShoot(): Boolean
}

class Shooter(val shootSpeed: Float, val shootDelay: Float) : ShooterInterface{

    var timeSinceLastShoot = 0f

    override fun shoot(x: Float, y: Float): Projectile {
        timeSinceLastShoot = 0f
        return Projectile(x, y, shootSpeed)
    }

    override fun canShoot(): Boolean{
        return timeSinceLastShoot > shootDelay
    }
}