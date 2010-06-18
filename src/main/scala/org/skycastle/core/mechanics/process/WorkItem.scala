package org.skycastle.core.mechanics.process

import org.skycastle.core.entity.{EntityQuery, Entity}

/**
 * Describes some planned work, and its progress.
 * The work needs some input of work units, resources and catalysts / tools.
 * It can also specify environment conditions needed (e.g. dry surroundings, no vacum, etc :P)
 *
 * A work item may need several phases of different kinds of work (e.g. forging something will first need heating, then hammering, then cooling) 
 */
trait WorkItem {

  /**
   * The amount of work still needed for this work phase / activity / process.
   */
  def workNeeded: Map[WorkType, Double]

  /**
   * The resources still needed.
   * Basically an entity query defining some shape and material properties, or functionality, or machine node, etc, and the number of them.
   */
  def componentsNeeded: Map[EntityQuery, Int]

  /**
   * Items needed during the work that get freed up after the work completes.
   */
  def requiredCatalysts: Map[EntityQuery, Int]

/* TODO: Add later if needed
  def requiredEnvironmentConditions: List[EnvironmentCondition]
*/

  def addCatalyst(entity: Entity)
  def addComponent(entity: Entity)
  def addWork(workType: WorkType, amount: Double)

  /**
   * Overall progress of this work item, from 0 (nothing) to 1 (completed)
   */
  def progress: Double

  def isCompleted: Boolean

  /**
   * If work is not ready, cancels the work and frees up any free resources and catalysts.
   * If the work is ready, frees up any free resources and catalysts.
   */
  def stop()

}