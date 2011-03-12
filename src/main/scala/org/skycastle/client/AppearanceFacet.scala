package org.skycastle.client

import org.skycastle.core.entity.Facet
import com.jme3.scene.shape.Box
import com.jme3.scene.{Mesh, Geometry, Spatial}
import com.jme3.material.Material
import com.jme3.math.{ColorRGBA, Vector3f}
import com.jme3.asset.AssetManager

/**
 * 
 */
class AppearanceFacet extends Facet {

  private var _appearance: Spatial = null

  def appearance(assetManager: AssetManager): Spatial = {
    if (_appearance == null) _appearance = createAppearance(assetManager)
    _appearance
  }

  private def createAppearance(assetManager: AssetManager): Spatial = {
    // TODO: Type accessors to wrapper

    val name = stringParam(facetParameters, 'name, this.getClass.getSimpleName + "-" + hashCode)

    val x = floatParam(facetParameters, 'x, 0)
    val y = floatParam(facetParameters, 'y, 0)
    val z = floatParam(facetParameters, 'z, 0)
    val pos = new Vector3f(x, y, z)

    val shapeType = symbolParam(facetParameters, 'shape, 'box)
    val mesh: Mesh = shapeType match {
      case 'box =>
        val w = floatParam(facetParameters, 'w, 1)
        val h = floatParam(facetParameters, 'h, 1)
        val d = floatParam(facetParameters, 'd, 1)
        new Box(pos, w, h, d)
//      case 'sphere =>
//      case 'cylinder =>
      case _ => throw new IllegalStateException("Unknown shape type '"+shapeType+"'")
    }

    val materialPath = stringParam(facetParameters, 'material, "Common/MatDefs/Misc/SolidColor.j3md")
    // TODO: Register custom parser for color
    val color = colorParam(facetParameters, 'color, ColorRGBA.Red)

    val geom = new Geometry(name, mesh);
    val mat = new Material(assetManager, materialPath);

    // TODO: Wrap material properties in block and get all..
    mat.setColor("m_Color", color);
    geom.setMaterial(mat);

    geom
  }


}