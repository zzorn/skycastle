package org.skycastle.client.appearance

import org.scalaprops.Bean
import com.jme3.scene.Spatial
import com.jme3.asset.AssetManager

/**
 * 
 */
trait Appearance extends Bean {

  val name = p('name, this.getClass.getSimpleName + "-" + hashCode)

  val defaultMaterial = "Common/MatDefs/Misc/SolidColor.j3md"

  def createSpatial(assetManager: AssetManager): Spatial

}