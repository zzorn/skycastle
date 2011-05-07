package org.jmespike

import activity.{GameActivity, MainMenuActivity}
import com.jme3.app.SimpleApplication
import conf.{ConfLoader, ConfEditor}
import com.jme3.scene.Spatial
import com.jme3.asset.plugins.FileLocator
import input.KeyBindings
import java.io.File
import scene.{SceneFactory, TestScene}
import org.skycastle.util.Logging
import java.util.logging.{ConsoleHandler, Level, Logger}

/**
 * Attempt at using JME classes for storing most stuff.
 */
object BaseGame extends SimpleApplication with SceneLoader with Logging {


  var editor: ConfEditor[SceneFactory] = null

  var reload = false

  var currentScene: Spatial = null

  var conf: SceneFactory = null

  var keyBindings: KeyBindings = new KeyBindings()

  def main(args: Array[ String ]) {
    // Set a sane logging level on jme
    Logger.getLogger("com.jme3").setLevel(Level.SEVERE)

    setupConsoleLogging("")

    start()
  }

  private def loadSceneSettings(path: String): SceneFactory = {
    log.info("Loading settings from " + path)
    var conf = assetManager.loadAsset(path).asInstanceOf[TestScene]
    if (conf == null) {
      log.warning("  Failed to load settings from "+path+", using defaults")
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

    // Load game settings

    // TODO: Load keybindings and other game options

    keyBindings.setupInput(inputManager)

    // Setup initial activity in the game
    // TODO: Load these from config or such?  Or create in subclass?
    val gameActivity = new GameActivity(loadSceneSettings("config/jmespike.json"))
    val initialActivity = new MainMenuActivity(gameActivity, loadSceneSettings("config/blueplanets.json"))
    stateManager.attach(initialActivity)


    // Setup editor
    val absoluteSettingsPath = (new File("./assets/config" )).getAbsoluteFile
    editor = new ConfEditor[SceneFactory](loadScene _,  absoluteSettingsPath, classOf[SceneFactory])
    editor.start()
    editor.setActive(true)

    // Configure camera for editing
    flyCam.setEnabled(true)
    flyCam.setDragToRotate(true)
    flyCam.setMoveSpeed(100)

    // Keep running even if focus is lost
    setPauseOnLostFocus(false)
  }


  def loadScene(scene: SceneFactory) {
    conf = scene
    reload = true

    editor.setSettings(conf)
  }

  def reloadScene() {
    reload = true
  }

  override def simpleUpdate(tpf: Float) {

    if (reload) {
      reload = false
      load()
    }

  }

  private def load() {
    if (currentScene != null) rootNode.detachChild(currentScene)

    if (conf != null) {
      currentScene = conf.createScene
      rootNode.attachChild(currentScene)
    }
  }

}

