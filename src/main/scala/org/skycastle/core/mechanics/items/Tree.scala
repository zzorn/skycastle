package org.skycastle.core.mechanics.items

import org.skycastle.core.mechanics.work.{Hacking, WorkItem}

/**
 * 
 */
class Tree {

  private lazy val cuttingWork: WorkItem = new WorkItem(work = Map((Hacking(0.1) -> 10)))

  /**
   * Starts a cutting work project for this tree, or returns the existing one if it already exists.
   */
  def cut(): WorkItem = cuttingWork


}