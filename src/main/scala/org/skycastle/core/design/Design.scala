package org.skycastle.core.design

import com.jme3.scene.Spatial
import java.util.HashMap
import org.skycastle.util.grid.{GridSize, GridPos}
import com.jme3.math.Vector3f
import org.skycastle.util.Vec3i
import org.skycastle.util.octree.GridStorage


trait Change {
  def canDo: ChangeResult
  def doChange
  def undoChange
}

case class ChangeImpl(_canDo: () => ChangeResult,
                      _doChange: () => Unit,
                      _undoChange: () => Unit) extends Change {
  def canDo = _canDo()
  def doChange = _doChange()
  def undoChange = _undoChange()
}

trait HistoryDocument[T <: Change] {
  private var undoQueue: List[T] = Nil
  private var redoQueue: List[T] = Nil

  def canDoChange(change: T): Boolean = change.canDo.isSuccess

  def doChange(change: T): ChangeResult = {
    val result = change.canDo
    result.onSuccess{
      change.doChange
      undoQueue = change :: undoQueue
    }
    result
  }

  def undo(): Boolean = {
    if (undoQueue.isEmpty) false
    else {
      val change = undoQueue.head
      undoQueue = undoQueue.tail
      change.undoChange
      redoQueue = change :: redoQueue
      true
    }
  }

  def redo(): Boolean = {
    if (redoQueue.isEmpty) false
    else {
      val change = redoQueue.head
      redoQueue= redoQueue.tail
      change.doChange
      undoQueue ::= change
      true
    }
  }



}

trait DesignChange extends Change

/**
 * Contains a design for some structure.
 * The design is made up of Parts.
 * Provides a full set of editing operations, including undo.
 */
class Design extends HistoryDocument[DesignChange] {

  private val gridStorage = new GridStorage[Part](GridSize(0))
  private var parts: List[Part] = Nil
  private var selectedParts: Set[Part] = Set()

  case class AddChange(part: Part) extends DesignChange {
    def canDo = checkCanAdd(part)
    def doChange = doAddPart(part)
    def undoChange = doRemovePart(part)
  }

  case class RemoveChange(part: Part) extends DesignChange {
    def canDo = checkCanRemove(part)
    def doChange = doRemovePart(part)
    def undoChange = doAddPart(part)
  }


  def addPart(part: Part): ChangeResult = {
    doChange(AddChange(part))
  }

  private def checkCanAdd(part: Part): ChangeResult = {
    if (part == null) return ChangeNotPossible("Added part should not be null")
    if (parts.contains(part)) return ChangeNotPossible("Can't add the same part " + part + " twice.")

    // Check occupation
    val occupants: List[Part] = part.occupiedCells.toList flatMap {c => gridStorage.getDataAt(c)}
    if (!occupants.isEmpty) return AlreadyOccupied(occupants)

    // Check connections
    // TODO

    // Check support?
    // TODO

    ChangeSuccess
  }

  private def checkCanRemove(part: Part): ChangeResult = {
    if (part == null) ChangeNotPossible("Removed part should not be null")
    else if (!parts.contains(part)) ChangeNotPossible("Part " + part + " not found.")
    else ChangeSuccess
  }

  private def doAddPart(part: Part) {
    parts = part :: parts
    part.occupiedCells.toList.foreach(c => gridStorage.put(c, part))
  }

  private def doRemovePart(part: Part) {
    parts = parts.filterNot(_ == part)
    part.occupiedCells.toList.foreach(c => gridStorage.remove(c))
  }

  def selectPart(part: Part) {
    if (part == null) throw new IllegalArgumentException("Part should not be null")
    if (!parts.contains(part)) throw new IllegalArgumentException("Can't find the part " + part + ".")

    selectedParts += part
  }


  def unSelectPart(part: Part) {}
  def togglePartSelection(part: Part) {}
  def selectAll() {}
  def selectNone() {}

  def groupSelectedParts() {}
  def unGroupSelectedGroups() {}

  // TODO: Select all in volume

  def deletePart(part: Part) {}
  
  def getView(): Spatial = {null}

}

case class AlreadyOccupied(occupants: List[Part]) extends ChangeFailure
case class MismatchingConnections() extends ChangeFailure
case object MissingSupport extends ChangeFailure

