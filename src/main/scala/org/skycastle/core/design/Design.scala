package org.skycastle.core.design

import com.jme3.scene.Spatial

/**
 * Contains a design for some structure.
 * The design is made up of Parts.
 * Provides a full set of editing operations, including undo.
 */
class Design {

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