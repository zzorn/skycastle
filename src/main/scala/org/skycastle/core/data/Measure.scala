package org.skycastle.core.data

case class Measurable(unit: Symbol, expoent: Int)

/**
 * A numerical value with a unit.
 * Can be converted to a number.
 */
// TODO: Support getting the measure and checking the type at the same time.
case class Measure(value: Double, units: List[Measurable]) extends Value {
  
}