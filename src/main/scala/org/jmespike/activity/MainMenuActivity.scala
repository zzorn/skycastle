package org.jmespike.activity

import com.jme3.input.controls.ActionListener
import java.lang.String
import com.jme3.scene.Spatial
import org.jmespike.scene.{SceneFactory, TestScene}

/**
 * 
 */
class MainMenuActivity(gameActivity: Activity, scene: SceneFactory = new TestScene) extends Activity {

  addActionListeners(List("ok", "action1", "action2"), {(name: String, pressed: Boolean, value: Float) =>
    // Activate menu option
    // TODO
    changeActivityTo(gameActivity)
  })

  addActionListeners(List("prev", "up", "left"), {(name: String, pressed: Boolean, value: Float) =>
    // Go to Previous menu option
    // TODO
    null
  })

  addActionListeners(List("next", "down", "right"), {(name: String, pressed: Boolean, value: Float) =>
    // Go to Next menu option
    // TODO
    null
  })

  addActionListeners(List("escape", "secondary1"), {(name: String, pressed: Boolean, value: Float) =>
    // Go up a menu level
    // TODO
    stopGame()
  })

  protected def sceneFactory = scene

}