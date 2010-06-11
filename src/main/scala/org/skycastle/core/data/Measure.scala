package org.skycastle.core.data

case class Measurable(unit: Symbol, exponent: Int) {
  override def toString: String = if (exponent == 0) "" else if (exponent == 1) unit.name else unit.name + exponent
}

/**
 * A numerical value with a unit.
 * Can be converted to a number.
 */
// TODO: Support getting the measure and checking the type at the same time.
case class Measure(value: Double, units: List[Measurable]) extends Value {
  
  override def toString: String = value.toString + " " + units.mkString("") 
}