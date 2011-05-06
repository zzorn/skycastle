package org.jmespike.scene

import com.jme3.scene.{Spatial, Node}

/**
 * 
 */
class SpatialScene(node: Spatial) extends Scene {

  def createRoot = node

}