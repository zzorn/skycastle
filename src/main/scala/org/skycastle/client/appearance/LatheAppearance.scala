package org.skycastle.client.appearance

import com.jme3.asset.AssetManager
import com.jme3.math.Vector3f
import com.jme3.scene.shape.Box
import com.jme3.scene.{Geometry, Mesh, Spatial}
import com.jme3.material.Material
import org.skycastle.util.mesh.{Segment, RoundSegment, LatheBuilder}
import com.jme3.texture.Texture

/**
 * 
 */
class LatheAppearance extends Appearance {
  val x = p('x, 0f)
  val y = p('y, 0f)
  val z = p('z, 0f)

  val material = p('material, "Common/MatDefs/Misc/SimpleTextured.j3md")

  val color = p('color, new ColorBean(1, 0, 0))
  val texture = p('texture, "textures/pipes/brass_pipe.diffuse.jpg")

  var segments: List[Segment] = Nil

  val sides = p('sides, 32)

  def createSpatial(assetManager: AssetManager): Spatial = {
    val pos = new Vector3f(x(), y(), z())

    val mesh: Mesh = LatheBuilder.createMesh(segments, sides())

    val geom = new Geometry(name(), mesh);

    val mat = new Material(assetManager, material());
    // TODO: Wrap material properties in block and get all, or use custom beans for different materials..
//    mat.setColor("m_Color", color().toColorRGBA);
    val tex = assetManager.loadTexture(texture())
    tex.setWrap(Texture.WrapMode.Repeat)
    mat.setTexture("m_ColorMap", tex);

    geom.setMaterial(mat);

    geom
  }
}