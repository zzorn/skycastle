package org.jmespike.activity

import com.jme3.input.controls.ActionListener
import java.lang.String
import com.jme3.scene.Spatial
import org.jmespike.scene.{SceneFactory, TestScene}
import org.jmespike.input.InputNames._

/**
 * 
 */
class MainMenuActivity(gameActivity: Activity, scene: SceneFactory = new TestScene) extends Activity {

  whenAction(Ok, Action1, Action2) {
    // Activate menu option
    // TODO
    changeActivityTo(gameActivity)
  }

  whenAction(Previous, Up, Left) {
    // Go to Previous menu option
    // TODO
    null
  }

  whenAction(Next, Down, Right) {
    // Go to Next menu option
    // TODO
    null
  }

  whenAction(Escape, Secondary1) {
    // Go up a menu level
    // TODO
    stopGame()
  }


  protected def createScene = scene.createScene

}