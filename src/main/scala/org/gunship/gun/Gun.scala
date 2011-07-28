package org.gunship.gun

import org.jmespike.conf.Conf

import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.scalaprops.ui.editors.{SelectionEditorFactory}

/**
 * A gun controlled by a player.
 */
class Gun extends Conf {

  val sightPic = p('sightPic, "")

  val shotColor = p('color, Vec4(1, 0, 0, 1))
  val shotLength = p('shotLength, 3f)
  val shotSize = p('shotSize, 5f)
  val shotVariation = p('shotVariation, 0.1f)

  val barrelCount = p('barrelCount, 2)
  val barrelSequence = p[BarrelSequence]('barrelSequence, AlternatingBarrelSequence).
          editor(new SelectionEditorFactory[BarrelSequence](BarrelSequence.values))

  val shotDelay = p('shotDelay, 1f)

  val shotVelocity = p('shotVelocity, 1f)
  
  val shotDamage = p('shotDamage, 1f)

  val minBoostTime = p('minBoostTime, 2f)
  val maxBoostTime = p('maxBoostTime, 10f)
  val boostedDamage = p('boostedDamage, 5f)
  val boostedVelocity = p('boostedVelocity, 0.5f)

  val relativeX = p('relativeX, 0.5f)
  val relativeY = p('relativeY, 1f)

  private var powerups: List[PowerUp] = Nil

  private var boostChargeStartedAt: Long = -1
  private var lastSmallShotFiredAt: Long = -1
  private var autoFireActive = false
  private var bigShotLoading = false

  private var mostRecentAimX = 0.5f
  private var mostRecentAimY = 0.5f

  def triggerPressed(boostTrigger: Boolean) {
    if (boostTrigger) {
      boostChargeStartedAt = System.currentTimeMillis()
      bigShotLoading = true
    }
    else {
      fireSmallShot()
      autoFireActive = true
    }
  }

  def triggerReleased(boostTrigger: Boolean) {
    if (boostTrigger) {
      fireBigShot()
      bigShotLoading = false
    }
    else autoFireActive = false
  }

  def aimMoved(relativeX: Float, relativeY: Float) {
    mostRecentAimX = relativeX
    mostRecentAimY = relativeY
  }

  def update() {
    // Auto fire if button pressed
    if (autoFireActive) {
      fireSmallShot()
    }
    
    // Remove expired powerups
    // TODO
  }

  def addPowerup(powerup: PowerUp) {
    powerups ::= powerup
  }

  /** Removes powerups */
  def resetPowerups() {
    powerups = Nil
  }

  private def fireBigShot() {
    val chargeTime: Float = (System.currentTimeMillis() - boostChargeStartedAt) / 1000f
    if (chargeTime > minBoostTime()) {
      val relativePower = min(1f, (chargeTime - minBoostTime()) / (maxBoostTime() - minBoostTime()))
      fireShot(relativePower)
    }
  }

  private def fireSmallShot() {
    val d = System.currentTimeMillis() - lastSmallShotFiredAt

    if (d >= shotDelay() && !bigShotLoading) {
      lastSmallShotFiredAt = System.currentTimeMillis()
      fireShot(0)
    }
  }

  private def fireShot(relativePower: Float) {
    // TODO: Fire at last aimed coordinates
    // TODO: Calculate damage from relative power and active powerups
  }
}

