package org.skycastle.client.appearance

import com.jme3.asset.AssetManager
import com.jme3.scene.shape.Box
import com.jme3.math.{ColorRGBA, Vector3f}
import com.jme3.scene.{Geometry, Mesh, Spatial}
import com.jme3.material.Material
import org.skycastle.client.wrappers.Vec3
import org.skycastle.util.parameters.Parameters

/**
 * 
 */
class BoxAppearance extends BasicAppearance {


  def doCreateSpatial(assetManager: AssetManager): Spatial = {
    val mesh: Mesh = new Box(
      parameters.getFloat('width, 1f),
      parameters.getFloat('height, 1f),
      parameters.getFloat('depth, 1f))

    val geom = new Geometry(name, mesh);

    // TODO: get material as object?
    // TODO: Divide appearance into mesh and material?
    val mat = new Material(assetManager, parameters.getString('material, "Common/MatDefs/Misc/SolidColor.j3md"));
    mat.setColor("m_Color", parameters.getColor('materialColor, ColorRGBA.Red));
    geom.setMaterial(mat);

    geom
  }
}