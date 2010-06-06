package org.skycastle.core.data

import _root_.java.io.Serializable

/**
 * A data object, that has named values.
 */
// TODO: Should be loadable from configuration files too
class Data extends AbstractValue {

  def this(data: Map[Symbol, Value]) {
    this()
    value = data
  }

  type T = Map[Symbol, Value]
  type Self = this.type
  def self = this

  def defaultValue: T = Map()

  override def asMap = value
  override def isMap = true

  def get(name: Symbol, default: Value): Value = value.getOrElse(name, default)
  def set(name: Symbol, member: Value) = value = value.+( name -> member )

  /**
   * Returns a value from a nested path
   */
  def apply(path: Symbol*): Option[Value] = {
    var v: Option[Value] = Some(this)
    path.foreach( p=> {
      if (v != None && v.get.isInstanceOf[Data]) v = v.get.asInstanceOf[Data].value.get(p)
      else v = None
    })
    return v
  }

}

