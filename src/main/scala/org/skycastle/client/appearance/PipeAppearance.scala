package org.skycastle.client.appearance

import com.jme3.asset.AssetManager
import com.jme3.scene.Spatial
import com.jme3.asset.AssetManager
import com.jme3.math.Vector3f
import com.jme3.scene.{Geometry, Mesh, Spatial}
import com.jme3.material.Material
import org.skycastle.util.mesh.{Segment, RoundSegment, LatheBuilder}
import com.jme3.texture.Texture
import com.jme3.bounding.BoundingBox
import org.skycastle.util.parameters.Parameters

/**
 * 
 */
class PipeAppearance extends BasicAppearance {

  val material = "Common/MatDefs/Light/Lighting.j3md"
  val textureMap = "textures/pipes/brass_pipe.diffuse.jpg"
  val normalMap = "textures/pipes/brass_pipe.normal.jpg"
  val specularMap = "textures/pipes/brass_pipe.specular.jpg"

  def doCreateSpatial(assetManager: AssetManager): Spatial = {

    val pipeShape = parameters.getSymbol('pipeShape, 'straight)
    val ir = parameters.getFloat('insideRadius, 0.8f)
    val or = parameters.getFloat('outsideRadius, 1f)
    val fh = parameters.getFloat('flangeHeight, 0.5f)
    val fw = parameters.getFloat('flangeWidth, 0.3f)
    val gz = parameters.getFloat('gridSize, 10f)
    val sides = parameters.getInt('sides, 16)
    val wraps = parameters.getFloat('textureWraps, 2f)
    val fr = or + fh
    val tg = 1f/32f // Texture grids

    // Create mesh
    val segments: List[Segment] = pipeShape match {
      case 'straight =>
        val y = gz / 2
        val z = gz / 2
        val len = gz
        RoundSegment(len/3,y,z,  ir, 31*tg, wraps, 31.9f*tg) ::
        RoundSegment(0,y,z,      ir, 29*tg, wraps) ::
        RoundSegment(0,y,z,      ir, 29*tg, wraps) ::
        RoundSegment(0,y,z,      fr, 26*tg, wraps) ::
        RoundSegment(0,y,z,      fr, 26*tg, wraps) ::
        RoundSegment(fw,y,z,     fr, 25*tg, wraps) ::
        RoundSegment(fw,y,z,     fr, 25*tg, wraps) ::
        RoundSegment(fw,y,z,     or, 23*tg, wraps) ::
        RoundSegment(fw,y,z,     or, 23*tg, wraps) ::
        RoundSegment(fw,y,z,     or,  6*tg, wraps) ::
        RoundSegment(len-fw,y,z, or, 23*tg, wraps) ::
        RoundSegment(len-fw,y,z, or, 23*tg, wraps) ::
        RoundSegment(len-fw,y,z, fr, 25*tg, wraps) ::
        RoundSegment(len-fw,y,z, fr, 25*tg, wraps) ::
        RoundSegment(len,y,z,    fr, 26*tg, wraps) ::
        RoundSegment(len,y,z,    fr, 26*tg, wraps) ::
        RoundSegment(len,y,z,    ir, 29*tg, wraps) ::
        RoundSegment(len,y,z,    ir, 29*tg, wraps) ::
        RoundSegment(len/3,y,z,  ir, 31*tg, wraps, 31.9f*tg) ::
        Nil
      case _ => throw new IllegalStateException("Unknown pipe shape "+pipeShape)
    }
    val mesh: Mesh = LatheBuilder.createMesh(segments, sides)

    // Material
    val mat = new Material(assetManager, material);
    // TODO: Wrap material properties in block and get all, or use custom beans for different materials..

    val tex = assetManager.loadTexture(textureMap)
    tex.setWrap(Texture.WrapMode.Repeat)
    mat.setTexture("m_DiffuseMap", tex);

    val nor = assetManager.loadTexture(normalMap)
    nor.setWrap(Texture.WrapMode.Repeat)
    mat.setTexture("m_NormalMap", nor);

    val spec = assetManager.loadTexture(specularMap)
    spec.setWrap(Texture.WrapMode.Repeat)
    mat.setTexture("m_SpecularMap", spec);

    mat.setFloat("m_Shininess", 1f)


    // Geometry object
    val geom = new Geometry(name, mesh);
    geom.setMaterial(mat);

    geom
  }

}