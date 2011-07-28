package org.gunship

import level.Level
import org.jmespike.BaseGame
import org.jmespike.activity.Activity

/**
 * Main class for the Gunship Ranger game.
 */
object GunshipGame extends BaseGame {

  var playerShip = new Gunship()

  def createInitialActivity(): Activity = {
    new Level()
  }

}
