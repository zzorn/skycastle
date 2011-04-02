package org.skycastle.client.designer

import com.jme3.asset.AssetManager
import com.jme3.scene.shape.{Quad, Box}
import com.jme3.math.ColorRGBA
import com.jme3.material.Material
import com.jme3.scene.{Geometry, Mesh, Node, Spatial}
import org.skycastle.util.MathUtils
import com.jme3.bounding.BoundingBox
import com.jme3.material.RenderState.BlendMode
import com.jme3.renderer.queue.RenderQueue.Bucket

/**
 * 
 */
class DesignGrid {

  val aboveColor = new ColorRGBA(0, 0.65f, 1, 0.75f)
  val underColor = new ColorRGBA(0, 0, 1, 0.75f)

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
    for (x <- 0.until(width); z <- 0.until(depth)) {
      node.attachChild(makeCell(assetManager, x, 0, z, true))
      node.attachChild(makeCell(assetManager, x, 0, z, false))
    }
    node
  }

  private def makeCell(assetManager: AssetManager, x: Int, y: Int, z: Int, upperSide: Boolean): Spatial = {
//    val mesh: Mesh = new Quad(gridSize, gridSize)
    val mesh: Mesh = new Quad(gridSize, gridSize)

    val mat = new Material(assetManager, "shaders/SimpleMultiplyColored.j3md");
    mat.setColor("Col", if (upperSide) aboveColor else underColor);
    mat.setTexture("ColorMap", assetManager.loadTexture("textures/grid_cell.png"))
    mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);

    val geom = new Geometry("GridCell", mesh);
    geom.setMaterial(mat);

    if (upperSide) {
      geom.rotate(-MathUtils.Tauf/4, 0, 0)
      geom.move(x*gridSize, y*gridSize, (z+1)*gridSize)
    }
    else {
      geom.rotate(MathUtils.Tauf/4, 0, 0)
      geom.move(x*gridSize, y*gridSize, z*gridSize)
    }

    geom.setModelBound(new BoundingBox())

    geom.setQueueBucket(Bucket.Transparent)
    geom.setUserData("gridCoordinate.x", x)
    geom.setUserData("gridCoordinate.y", y)
    geom.setUserData("gridCoordinate.z", z)

    geom
  }

}