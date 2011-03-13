package org.skycastle.client.appearance

import com.jme3.asset.AssetManager
import com.jme3.scene.shape.Box
import com.jme3.math.{ColorRGBA, Vector3f}
import com.jme3.scene.{Geometry, Mesh, Spatial}
import com.jme3.material.Material
import org.skycastle.client.wrappers.Vec3

/**
 * 
 */
class BoxAppearance extends BasicAppearance {

  val size = p('size, new Vec3(1, 1, 1))

  def createSpatial(assetManager: AssetManager): Spatial = {
    val p = pos().toVector3f
    val s = size().toVector3f.multLocal(scale().toVector3f)
    val mesh: Mesh = new Box(p, s.x, s.y, s.z)

    val geom = new Geometry(name(), mesh);
    
    val mat = new Material(assetManager, material());
    // TODO: Wrap material properties in block and get all, or use custom beans for different materials..
    mat.setColor("m_Color", color().toColorRGBA);
    geom.setMaterial(mat);

    geom
  }
}