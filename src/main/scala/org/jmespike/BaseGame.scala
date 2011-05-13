package org.jmespike

import activity.{SpaceGameActivity, MainMenuActivity}
import com.jme3.app.SimpleApplication
import conf.{ConfLoader, ConfEditor}
import com.jme3.asset.plugins.FileLocator
import input.KeyBindings
import java.io.File
import scene.{SceneFactory, TestScene}
import org.skycastle.util.Logging
import java.util.logging.{ConsoleHandler, Level, Logger}
import com.jme3.scene.{Node, Spatial}

/**
 * Attempt at using JME classes for storing most stuff.
 */
object BaseGame extends SimpleApplication with SceneDisplay with Logging {


  var editor: ConfEditor[SceneFactory] = null

  var reload = false

  val scenes: Node = new Node()

  var conf: SceneFactory = null

  var keyBindings: KeyBindings = new KeyBindings()

  def main(args: Array[ String ]) {
    // Set a sane logging level on jme
    Logger.getLogger("com.jme3").setLevel(Level.SEVERE)

    /*
    // Setup logging - works around java logging issues
    setupConsoleLogging("org.skycastle")
    setupConsoleLogging("org.jmespike")
    setupConsoleLogging("org.scalaprops")
    */

    start()
  }

  private def loadSceneSettings(path: String): SceneFactory = {
    log.info("Loading settings from " + path)
    var conf = assetManager.loadAsset(path).asInstanceOf[TestScene]
    if (conf == null) {
      log.warn("  Failed to load settings from "+path+", using defaults")
      conf = new TestScene
    }
    conf
  }

  def simpleInitApp() {


    // Saner logging output format
//    setupConsoleLogging("org.skycastle")
//    setupConsoleLogging("com.jme3")

    // Config ways to get assets
    assetManager.registerLocator("assets", classOf[FileLocator])
    assetManager.registerLoader(classOf[ConfLoader], "json")

    rootNode.attachChild(scenes)

    // Load game settings

    // TODO: Load keybindings and other game options

    keyBindings.setupInput(inputManager)

    // Setup initial activity in the game
    // TODO: Load these from config or such?  Or create in subclass?
    val gameActivity = new SpaceGameActivity(loadSceneSettings("config/jmespike.json"))
    val initialActivity = new MainMenuActivity(gameActivity, loadSceneSettings("config/blueplanets.json"))
    stateManager.attach(initialActivity)

    // Setup editor
    // TODO: Change editor?  - edit one entity archetype at a time, and recreate all appearance instances of it as it changes..
    val absoluteSettingsPath = (new File("./assets/config" )).getAbsoluteFile
    editor = new ConfEditor[SceneFactory]({sf => },  absoluteSettingsPath, classOf[SceneFactory])
    editor.start()
    editor.setActive(true)
    
    // Configure camera for editing
    flyCam.setEnabled(true)
    flyCam.setDragToRotate(true)
    flyCam.setMoveSpeed(100)

    // Keep running even if focus is lost
    setPauseOnLostFocus(false)
  }


/*
  def loadScene(scene: SceneFactory) {
    conf = scene
    reload = true

    editor.setSettings(conf)
  }
*/

  def addScene(scene: Spatial) {
    scenes.attachChild(scene)
  }

  def removeScene(scene: Spatial) {
    scenes.detachChild(scene)
  }


/*
  def reloadScene() {
    reload = true
  }
*/
  override def simpleUpdate(tpf: Float) {
  }


}

