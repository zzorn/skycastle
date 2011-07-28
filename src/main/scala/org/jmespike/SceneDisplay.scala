package org.jmespike

import scene.Scene
import com.jme3.scene.Spatial

/**
 * Something that can show the specified scene
 */
trait SceneDisplay {
  def addScene(scene: Spatial)
  def removeScene(scene: Spatial)
}