package org.skycastle.core.mechanics.material

import org.skycastle.core.entity.Facet

/**
 * Describers the material of some item
 * @param flexibility how easily a material can be bent (0 = not at all, 0.5 = stiffly, 1 = freely).
 */
case class Material(flexibility: Double) extends Facet {

}