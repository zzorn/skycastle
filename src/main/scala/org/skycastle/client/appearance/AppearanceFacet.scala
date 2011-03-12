package org.skycastle.client.appearance

import org.skycastle.core.entity.Facet
import com.jme3.scene.Spatial
import com.jme3.asset.AssetManager

/**
 * 
 */
class AppearanceFacet extends Facet {

  private var _spatial: Spatial = null

  private var _appearance: Appearance = facetParameters.getOrElse('appearance, new BoxAppearance()).asInstanceOf[Appearance]
  def appearance: Appearance = _appearance
  def appearance_=(a: Appearance) {
    _appearance = a
    _spatial = null
  }

  def spatial(assetManager: AssetManager): Spatial = {
    if (_spatial == null) _spatial = createSpatial(assetManager)
    _spatial
  }

  private def createSpatial(assetManager: AssetManager): Spatial =
    appearance.createSpatial(assetManager)

}
