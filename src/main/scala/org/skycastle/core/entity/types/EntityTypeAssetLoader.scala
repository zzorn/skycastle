package org.skycastle.core.entity.types

import org.skycastle.util.Logging
import com.jme3.asset.{AssetInfo, AssetLoader}

/**
 * JME3 asset loader that can load EntityTypes
 */
class EntityTypeAssetLoader extends AssetLoader {
  def load(assetInfo: AssetInfo): Object =
    EntityTypeLoader.load(assetInfo.openStream, assetInfo.getKey.getName)
}