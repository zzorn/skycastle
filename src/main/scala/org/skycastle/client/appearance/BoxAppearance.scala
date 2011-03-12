package org.skycastle.client.appearance

import com.jme3.asset.AssetManager
import com.jme3.scene.shape.Box
import com.jme3.math.{ColorRGBA, Vector3f}
import com.jme3.scene.{Geometry, Mesh, Spatial}
import com.jme3.material.Material

/**
 * 
 */
class BoxAppearance extends Appearance {

  val name = p('name, this.getClass.getSimpleName + "-" + hashCode)

  val x = p('x, 0f)
  val y = p('y, 0f)
  val z = p('z, 0f)

  val w = p('w, 1f)
  val h = p('h, 1f)
  val d = p('d, 1f)

  val material = p('material, defaultMaterial)

  val color = p('color, new ColorBean(1, 0, 0))

  def createSpatial(assetManager: AssetManager): Spatial = {
    val pos = new Vector3f(x(), y(), z())
    val mesh: Mesh = new Box(pos, w(), h(), d())

    val geom = new Geometry(name(), mesh);
    
    val mat = new Material(assetManager, material());
    // TODO: Wrap material properties in block and get all, or use custom beans for different materials..
    mat.setColor("m_Color", color().toColorRGBA);
    geom.setMaterial(mat);

    geom
  }
}