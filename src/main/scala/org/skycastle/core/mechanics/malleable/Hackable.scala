package org.skycastle.core.mechanics.malleable

import org.skycastle.core.entity.Entity
import org.skycastle.core.space.Item

/**
 * Something that can be severed by hacking at it with a sharp tool.
 */
class Hackable(createHackedParts: (Double)=>List[Entity]) extends Malleable {

  /**
   * Executed when the process is done.
   */
  def hack(relativePosition: Double) {
    val e: Entity = entity.getForUpdate

    val parts = createHackedParts(relativePosition)

    e.facet[Item] match {
      case Some(item) =>
        // Add created objects in the surrounding space at the same location
        // TODO: Add some randomness to the location later so we don't get perfectly stacked objects?
        parts foreach {(part: Entity) => item.space.add(part, item.position)}

      case None =>
        // The object existed in a formless void, no need to add the parts anywhere
    }

    // Remove the entity if it didn't live on as one of the parts
    if (!parts.contains(e)) e.delete
  }
  
}