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

/**
 * 
 */
class PipeAppearance extends BasicAppearance {

  material := "Common/MatDefs/Light/Lighting.j3md"
  textureMap := "textures/pipes/brass_pipe.diffuse.jpg"
  normalMap := "textures/pipes/brass_pipe.normal.jpg"
  specularMap := "textures/pipes/brass_pipe.specular.jpg"

  case class PipeType(insideRadius: Float = 0.8f,
                      outsideRadius: Float = 1f,
                      flangeHeight: Float = 0.5f,
                      flangeWidth: Float = 0.3f,
                      textureWraps: Float = 2f,
                      gridSize: Float = 10f,
                      sides: Int = 16)

  var pipeType: PipeType = PipeType()

  var pipeShape: Symbol = 'straight

  def createSpatial(assetManager: AssetManager): Spatial = {

    val ir = pipeType.insideRadius
    val or = pipeType.outsideRadius
    val fr = pipeType.outsideRadius + pipeType.flangeHeight
    val fw = pipeType.flangeWidth
    val gz = pipeType.gridSize
    val wraps = pipeType.textureWraps
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
    val mesh: Mesh = LatheBuilder.createMesh(segments, pipeType.sides)

    // Material
    val mat = new Material(assetManager, material());
    // TODO: Wrap material properties in block and get all, or use custom beans for different materials..

    val tex = assetManager.loadTexture(textureMap())
    tex.setWrap(Texture.WrapMode.Repeat)
    mat.setTexture("m_DiffuseMap", tex);

    val nor = assetManager.loadTexture(normalMap())
    nor.setWrap(Texture.WrapMode.Repeat)
    mat.setTexture("m_NormalMap", nor);

    val spec = assetManager.loadTexture(specularMap())
    spec.setWrap(Texture.WrapMode.Repeat)
    mat.setTexture("m_SpecularMap", spec);

    mat.setFloat("m_Shininess", 0.999f)


    // Geometry object
    val geom = new Geometry(name(), mesh);
    geom.setMaterial(mat);
    // TODO: Rotation etc could be extracted, as well as material properties?
    geom.setLocalTranslation(pos().toVector3f)
    geom.setLocalScale(scale().toVector3f)
    geom.setModelBound(new BoundingBox())

    geom
  }

}