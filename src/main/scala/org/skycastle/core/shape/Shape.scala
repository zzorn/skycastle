package org.skycastle.core.shape

import org.skycastle.core.data.Data
import com.ardor3d.scenegraph.Spatial

object Shape {

  def apply(definition: Data): Shape = null

}

/**
 * A definition of a 3D shape.  May be parametrized.
 */
trait Shape {

  def name: String = this.getClass.getSimpleName

  def defaultParameters: Data

  def create(): Spatial = create(defaultParameters)
  def create(parameters: Data): Spatial
  
}
