package org.skycastle.core.data

abstract class Bool(_value: Boolean) extends Value {
  def value = _value

  override def prettyPrint(out: StringBuilder, indent: Int) {
    out.append(if (_value) "true" else "false")
  }
  
/*
  override def toString: String = if (_value) "true" else "false"
*/
}

case object True extends Bool(true)
case object False extends Bool(false)


