package org.skycastle.core.design

import com.jme3.scene.Spatial
import java.util.HashMap
import org.skycastle.util.grid.{GridSize, GridPos}
import com.jme3.math.Vector3f
import org.skycastle.util.Vec3i

/**
 * Contains a design for some structure.
 * The design is made up of Parts.
 * Provides a full set of editing operations, including undo.
 */
class Design {

  private val gridStorage = new GridStorage(GridSize(0))

  // TODO: Returns error info if it could not be placed, indicating why not (overlapping components, no support, or invalid connection types).
  def addPart(part: Part, location: GridPos, orientation: Any) {}

  def selectPart(part: Part) {}
  def unSelectPart(part: Part) {}
  def togglePartSelection(part: Part) {}
  def selectAll() {}
  def selectNone() {}

  def groupSelectedParts() {}
  def unGroupSelectedGroups() {}

  // TODO: Select all in volume

  def deletePart(part: Part) {}
  
  def undo() {}
  def redo() {}

  def getView(): Spatial = {null}

}

/**
 * Geohash like storage for parts.
 */
class GridStorage(rootCellSize: GridSize) {
  private val storage: HashMap[List[GridPos], Part] = new HashMap()

  def getIntersecting(gridPos: GridPos): List[Part] = {
   Nil
  }

  def addPartToCell(cell: GridPos, part: Part) {
    null
  }

  // TODO: Remove part (from keys)


}






