package org.skycastle.core.data

abstract case class Bool(value: Boolean) extends Value 

case object True extends Bool(true)
case object False extends Bool(false)


