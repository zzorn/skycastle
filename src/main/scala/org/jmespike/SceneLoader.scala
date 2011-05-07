package org.jmespike

import scene.SceneFactory

/**
 * Something that can show the specified scene
 */
trait SceneLoader {
  def loadScene(scene: SceneFactory)
}