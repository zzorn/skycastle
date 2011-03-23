package org.skycastle.core.design

import com.jme3.scene.Spatial
import java.util.HashMap
import org.skycastle.util.grid.{GridSize, GridPos}

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
    // TODO: go one step at a time, searching for matches

    // Get same size
    val gridHash = gridPos.gridHash(rootCellSize)
    storage.get(gridHash)
  }

  def addPartToCell(cell: GridPos, part: Part) {
    // Add gridpos -> part association
    storage.put(cell.gridHash(rootCellSize), part)
  }

  // TODO: Remove part (from keys)


}


// TODO: Octree

class OctTreeCell(pos: GridPos) {

  private var _part: Part = null
  private var _children: Map[GridPos, OctTreeCell] = Map()

  def hasPartAt(at: GridPos): Boolean = {
    if (at == pos) return _part != null
    else if (!_children.isEmpty && at.isInside(pos)) {false} // TODO: Check children
    else false
  }

}




