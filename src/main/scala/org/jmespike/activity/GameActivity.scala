package org.jmespike.activity

import org.jmespike.scene.{SceneFactory, TestScene}

/**
 * 
 */
class GameActivity(scene: SceneFactory = new TestScene) extends Activity {

  protected def sceneFactory = scene
}