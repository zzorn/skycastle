package org.jmespike.scene

import com.jme3.scene.Spatial
import org.jmespike.conf.Conf

/**
 * Creates a scene
 */
trait SceneFactory extends Conf {

  def createScene: Spatial

}