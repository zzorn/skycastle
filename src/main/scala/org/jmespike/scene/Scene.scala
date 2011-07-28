package org.jmespike.scene

import com.jme3.scene.Spatial
import org.jmespike.conf.Conf

/**
 * Creates a scene
 */
trait Scene extends Conf {

  def createScene: Spatial

}