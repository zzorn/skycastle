package org.skycastle.core.data

/**
 * A data object that has named values.
 */
case class Data(values: Map[Symbol, Value]) extends Value {

  def get[T <: Value](name: Symbol): T = values(name).asInstanceOf[T]
  def get[T <: Value](name: Symbol, default: T): T = values.getOrElse(name, default).asInstanceOf[T]

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

  override def toString: String = values.map(e =>{ e._1.name + ": " + e._2}).mkString("{", ", ", "}")

}

