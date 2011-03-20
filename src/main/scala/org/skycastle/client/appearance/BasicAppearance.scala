package org.skycastle.client.appearance

import org.skycastle.client.wrappers.{Vec3, ColorBean}
import com.jme3.asset.AssetManager
import org.skycastle.util.parameters.Parameters
import com.jme3.scene.Spatial
import com.jme3.bounding.BoundingBox

/**
 * Base class for appearances that applies general adjustments based on parameters.
 */
// TODO: Add utility method in BasicAppearance that creates a material from the parameters
trait BasicAppearance extends Appearance {

  final def createSpatial(assetManager: AssetManager): Spatial = {
    val spatial = doCreateSpatial(assetManager)

    // Apply generic adjustments based on parameters
    spatial.move(
      parameters.getFloat('x, 0f),
      parameters.getFloat('y, 0f),
      parameters.getFloat('z, 0f))
    spatial.scale(
      parameters.getFloat('xScale, 1f),
      parameters.getFloat('yScale, 1f),
      parameters.getFloat('zScale, 1f))
    spatial.rotate(
      parameters.getFloat('xRotate, 0f),
      parameters.getFloat('yRotate, 0f),
      parameters.getFloat('zRotate, 0f))

    spatial.setModelBound(new BoundingBox())

    spatial
  }

  def doCreateSpatial(assetManager: AssetManager): Spatial
}