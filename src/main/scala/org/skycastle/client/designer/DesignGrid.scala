package org.skycastle.client.designer

import com.jme3.asset.AssetManager
import com.jme3.scene.shape.{Quad, Box}
import com.jme3.math.ColorRGBA
import com.jme3.material.Material
import com.jme3.scene.{Geometry, Mesh, Node, Spatial}

/**
 * 
 */

class DesignGrid {

  var gridExponent: Int = 0
  var width = 4
  var depth = 4

  def gridSize: Float = math.pow(2, gridExponent).toFloat


  private var spatial: Spatial = null

  def getSpatial(assetManager: AssetManager): Spatial ={
    if (spatial == null) spatial = createSpatial(assetManager)
    spatial
  }

  def createSpatial(assetManager: AssetManager): Spatial = {
    val node = new Node("DesignGrid")
    for (x <- 0.until(width); y <- 0.until(depth)) {
      node.attachChild(makeCell(assetManager, x, y))
    }
    node
  }

  private def makeCell(assetManager: AssetManager, x: Int, y: Int): Spatial = {
    val mesh: Mesh = new Quad(gridSize, gridSize)

    val mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
    mat.setColor("m_Color", ColorRGBA.Red);

    val geom = new Geometry("GridCell", mesh);
    geom.setMaterial(mat);

    geom
  }

}