package org.jmespike

import com.jme3.app.SimpleApplication
import conf.ConfEditor
import scene.{TestSceneConf, TestScene, Scene}

/**
 * Attempt at using JME classes for storing most stuff.
 */
object Main extends SimpleApplication {

  var editor: ConfEditor = null

  var reload = true

  var scene: Scene = null

  var conf: TestSceneConf = new TestSceneConf

  def main(args: Array[ String ])
  {
    start()
  }

  def simpleInitApp() {

    // Load game settings


    // Setup initial activity in the game

    editor = new ConfEditor()
    editor.start()
    editor.setSettings(conf)
    editor.setActive(true)

    flyCam.setEnabled(true)
    flyCam.setDragToRotate(true)
    flyCam.setMoveSpeed(100)

    setPauseOnLostFocus(false)
  }


  override def simpleUpdate(tpf: Float) {

    if (reload) {
      reload = false
      load()
    }

  }

  def load() {
    if (scene != null) rootNode.detachChild(scene.root)

    scene = new TestScene(conf)

    rootNode.attachChild(scene.root)
  }

}

