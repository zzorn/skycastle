package org.skycastle.core.design

import com.jme3.scene.Spatial
import grid.GridSize
import java.util.HashMap

/**
 * Contains a design for some structure.
 * The design is made up of Parts.
 * Provides a full set of editing operations, including undo.
 */
class Design {

  private val gridStorage = new GridStorage()

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
  private val gridHash: HashMap[List[GridPos], Part] = new HashMap()

  def addPartToCell(cell: GridPos, part: Part) {
    // Add gridpos -> part association
    gridHash.put(cell.gridHashList(rootCellSize), part)
  }

  // TODO: Remove part (from keys)

  def getIntersecting(gridPos: GridPos): List[Part] = {
    // TODO: go one step at a time, searching for matches
    val gridHashList = gridPos.gridHashList(rootCellSize)
    
  }

}

