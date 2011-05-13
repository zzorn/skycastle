package org.jmespike.activity

import org.jmespike.scene.{SceneFactory, TestScene}
import org.jmespike.input.InputNames._
import org.jmespike.entity.ShipConf
import com.jme3.scene.{Node, Spatial}

/**
 * Provides player with a ship to control, and some space to fly in.
 */
// TODO: Do the action input handling in an EntityControl?
class SpaceGameActivity(testScene: SceneFactory = new TestScene) extends Activity {

  val playerShipConf = new ShipConf()

  var playerShip: Spatial = null


  whenAction(Up) {
//    playerShip.getControl()
  }

  whenAction(Down) {
  }

  whenAction(Left) {
  }

  whenAction(Right) {
  }


  whenAction(Action1) {
    // Shoot stuff
  }


  whenAction(Escape) {
    stopGame()
  }

  protected def createScene: Spatial = {

    playerShip = playerShipConf.createEntity()

    val sceneAndShip = new Node()
    sceneAndShip.attachChild(testScene.createScene)
    sceneAndShip.attachChild(playerShip)
    sceneAndShip
  }

}