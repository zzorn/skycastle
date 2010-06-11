package org.skycastle.core.data

/**
 * A data object that has named values.
 */
case class Data(value: Map[Symbol, Value]) extends Value {

  def get(name: Symbol, default: Value): Value = value.getOrElse(name, default)

  /**
   * Returns a value from a nested path
   */
/*
  def apply(path: Symbol*): Option[Value] = {
    var v: Option[Value] = Some(this)
    path.foreach( p=> {
      if (v != None && v.get.isInstanceOf[Data]) v = v.get.asInstanceOf[Data].value.get(p)
      else v = None
    })
    return v
  }
*/

}

