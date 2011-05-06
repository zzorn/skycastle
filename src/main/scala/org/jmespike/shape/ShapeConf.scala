package org.jmespike.shape

import com.jme3.scene.Mesh
import org.jmespike.Conf

/**
 * 
 */
trait ShapeConf extends Conf {

  def createShape: Mesh

}