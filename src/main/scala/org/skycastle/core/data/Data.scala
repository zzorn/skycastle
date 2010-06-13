package org.skycastle.core.data

import context.DataContext
import scala.math._

/**
 * A data object that has named values.
 */
case class Data(values: Map[Symbol, Value]) extends Value {

  def getValue(name: Symbol): Option[Value] = values.get(name)
  def getValue(name: Symbol, default: Value): Value = values.get(name).getOrElse(default)

  def getBoolean(name: Symbol): Boolean = values(name).asInstanceOf[Bool].value
  def getBoolean(name: Symbol, default: Boolean): Boolean = if (values.contains(name)) getBoolean(name) else default

  def getInt(name: Symbol): Int = values(name).asInstanceOf[Num].value.toInt
  def getInt(name: Symbol, default: Int): Int = if (values.contains(name)) getInt(name) else default
  def getInt(name: Symbol, default: Int, minValue: Int): Int = minValue max getInt(name, default)
  def getInt(name: Symbol, default: Int, minValue: Int, maxValue: Int): Int = maxValue min (minValue max getInt(name, default))

  def getFloat(name: Symbol): Float = values(name).asInstanceOf[Num].value.toFloat
  def getFloat(name: Symbol, default: Float): Float = if (values.contains(name)) getFloat(name) else default
  def getFloat(name: Symbol, default: Float, minValue: Float): Float = minValue max getFloat(name, default)
  def getFloat(name: Symbol, default: Float, minValue: Float, maxValue: Float): Float = maxValue min (minValue max getFloat(name, default))

  def getDouble(name: Symbol): Double = values(name).asInstanceOf[Num].value
  def getDouble(name: Symbol, default: Double): Double = if (values.contains(name)) getDouble(name) else default
  def getDouble(name: Symbol, default: Double, minValue: Double): Double = minValue max getDouble(name, default)
  def getDouble(name: Symbol, default: Double, minValue: Double, maxValue: Double): Double = maxValue min (minValue max getDouble(name, default))

  def getString(name: Symbol): String = values(name).asInstanceOf[Text].value
  def getString(name: Symbol, default: String): String = if (values.contains(name)) getString(name) else default

  def getList(name: Symbol): List[Value] = values(name).asInstanceOf[Arr].values
  def getList(name: Symbol, default: List[Value]): List[Value] = if (values.contains(name)) getList(name) else default

  def getData(name: Symbol): Data = values(name).asInstanceOf[Data]
  def getData(name: Symbol, default: Data): Data = if (values.contains(name)) getData(name) else default

  def apply(path: Symbol*): Option[Value] = getPath(path.toList)
  
  /**
   * Returns a value from a nested path
   */
  def getPath(path: List[Symbol]): Option[Value] = {
    var v: Option[Value] = Some(this)
    path.foreach( p=> {
      if (v.isDefined && v.get.isInstanceOf[Data]) v = v.get.asInstanceOf[Data].getValue(p)
      else v = None
    })
    return v
  }

  override def evaluate(context: DataContext): Value = {
    // Add the current members to the context
    // TODO: Is it ok if they are unevaluated??  What if they depend on each other??
    val extendedContext: DataContext = context // TODO: Extend the existing context with own memebers

    // Evaluate each memeber and create a Data from them
    Data(values.map((entry:(Symbol, Value)) => entry._1 -> entry._2.evaluate(extendedContext)))
  }


  override def prettyPrint(out: StringBuilder, indent: Int) {
    dent(out, indent).append("{")
    values.foreach(e =>{
      dent(out, indent + 1).append(e._1.name).append(": ")
      e._2.prettyPrint(out, indent + 1)
    })
    dent(out, indent).append("}")
  }

}

