package org.jmespike.shape

import com.jme3.scene.Mesh
import org.jmespike.conf.Conf
import java.util.Random

/**
 * 
 */
trait ShapeConf extends Conf {

  def createShape(rng: Random): Mesh

}