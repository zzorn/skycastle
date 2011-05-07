package org.jmespike

import com.jme3.app.SimpleApplication
import conf.{ConfLoader, ConfEditor}
import scene.{TestScene}
import com.jme3.scene.Spatial
import com.jme3.asset.plugins.FileLocator
import java.util.logging.Logger
import java.io.File

/**
 * Attempt at using JME classes for storing most stuff.
 */
object Main extends SimpleApplication {

  val log = Logger.getLogger(Main.getClass.getName)

  var editor: ConfEditor[TestScene] = null

  var reload = true

  var currentScene: Spatial = null

  var conf: TestScene = new TestScene

  def main(args: Array[ String ]) {
    start()
  }

  def simpleInitApp() {

    // Config ways to get assets
    assetManager.registerLocator("assets", classOf[FileLocator])
    assetManager.registerLoader(classOf[ConfLoader], "json")

    // Load game settings
    val settingsPath = "config/jmespike.json"
    log.info("Loading settings from " + settingsPath)
    conf = assetManager.loadAsset(settingsPath).asInstanceOf[TestScene]
    if (conf == null) {
      log.warning("  Failed to load settings, using defaults")
      conf = new TestScene
    }


    // Setup initial activity in the game


    // Setup editor
    val absoluteSettingsPath = (new File("./assets/config" )).getAbsoluteFile
    editor = new ConfEditor[TestScene](reloadConf _,  absoluteSettingsPath, classOf[TestScene])
    editor.start()
    editor.setSettings(conf)
    editor.setActive(true)

    // Configure camera for editing
    flyCam.setEnabled(true)
    flyCam.setDragToRotate(true)
    flyCam.setMoveSpeed(100)

    // Keep running even if focus is lost
    setPauseOnLostFocus(false)
  }


  def reloadConf(sceneConf: TestScene) {
    conf = sceneConf
    reload = true
  }

  override def simpleUpdate(tpf: Float) {

    if (reload) {
      reload = false
      load()
    }

  }

  def load() {
    if (currentScene != null) rootNode.detachChild(currentScene)

    currentScene = conf.createScene

    rootNode.attachChild(currentScene)
  }

}

