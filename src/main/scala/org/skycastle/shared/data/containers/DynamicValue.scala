package org.skycastle.shared.data.containers

import _root_.org.skycastle.shared.data._
import primitives.{Path, Func}

/**
 * A value that updates over time or based on other values in a context.
 */
final case class DynamicValue(function: Func, context: Value) extends Value with Updateable {

  private var startTime: Long = null
  private var value: Value = null
  private var parameters: Value = new MapValue( ('context -> context) )

  def update(time: Time) {
    if (startTime == null) startTime = time.currentTime
    val duration = time.currentTime - startTime

    parameters.set(Path(List(SymbolIndex('duration))), Num(duration)) // Should be:  paramter('duration) = duration
    parameters.set(Path(List(SymbolIndex('time))), Num(time.currentTime)) // Should be:  paramter('time) = time.currentTime
    value = function.calculate(parameters)
  }

  override def getValue: Value = {
    if (value == null) value = function.calculate(parameters)
    value
  }
  def add(v: Value) = throw new UnsupportedOperationException("Can not add values to a dynamic value");
  def set(index: Index, v: Value) = throw new UnsupportedOperationException("Can not set values in a dynamic value");
  protected def removeValue(index: Index) = throw new UnsupportedOperationException("Can not remove values in a dynamic value");
  protected def getValue(index: Index) = getValue.getValue(index)

}
