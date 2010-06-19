package org.skycastle.core.mechanics.items

import org.skycastle.core.mechanics.material.Material
import org.skycastle.core.entity.Entity
import org.skycastle.core.mechanics.form.Shaped
import org.skycastle.core.mechanics.ability.Fastener

/**
 * Bolt factory.  Could be implemented as data object in text file.
 */
object Bolt {

  sealed trait HeadType
  case object Hex extends HeadType

  def apply(material: Material, diameter_mm: Int = 8, length_mm: Int = 10, headType: HeadType = Hex): Entity = {
    val entity: Entity = new Entity()
    entity.addFacet(Shaped(null)) // TODO: Create bolt shape
    entity.addFacet(material)
    entity.addFacet(Fastener())
    entity
  }

}