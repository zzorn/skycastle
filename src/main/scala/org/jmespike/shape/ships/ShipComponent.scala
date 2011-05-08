package org.jmespike.shape.ships

import org.jmespike.conf.Conf
import com.jme3.scene.Spatial
import simplex3d.math.float.functions._
import simplex3d.math.float._

/**
 * A ship component.  Can be connected to other components.
 */
trait ShipComponent extends Conf {

  // TODO: Provide the vertexes for the corners, so we can create one continuous mesh,
  // that uses one texture
  def createModel(style: ShipConf, base: ComponentBase): Spatial

  // TODO: Some mirroring utilities?
//  def copyVerticalMirror(): Hull = null
//  def copyHorizontalMirror(): Hull = null

}