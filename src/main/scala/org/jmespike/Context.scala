package org.jmespike

import com.jme3.asset.AssetManager
import com.jme3.scene.Spatial
import java.util.ArrayList
import com.jme3.bounding.BoundingBox
import scala.collection.JavaConversions._
import org.scalaprops.parser.{BeanParser, JsonBeanParser}
import org.scalaprops.Bean

/**
 * Singleton for accessing services.
 */
object Context {

  def assetManager: AssetManager = Main.getAssetManager

}