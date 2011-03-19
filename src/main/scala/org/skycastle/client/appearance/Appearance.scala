package org.skycastle.client.appearance

import org.scalaprops.Bean
import com.jme3.scene.Spatial
import com.jme3.asset.AssetManager
import org.skycastle.core.entity.Facet
import org.skycastle.util.parameters.Parameters

/**
 * Provides a 3D appearance for an entity.
 */
trait Appearance extends Facet {

  private var _spatial: Spatial = null

  def facetCategory = 'appearance

  def name = this.getClass.getSimpleName + "-" + hashCode

  def spatial(assetManager: AssetManager): Spatial = {
    if (_spatial == null) _spatial = createSpatial(assetManager)
    _spatial
  }

  def createSpatial(assetManager: AssetManager): Spatial

}