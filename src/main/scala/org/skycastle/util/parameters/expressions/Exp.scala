package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * Something that can calculate a value, based on a parameter context.
 */
trait Exp {

  def name = this.getClass.getSimpleName

  def calculate(context: Parameters): Any

  def calculateNum(context: Parameters): Number = {
    val value = calculate(context)
    if (classOf[Number].isInstance(value)) value.asInstanceOf[Number]
    else if (value.isInstanceOf[Byte]) value.asInstanceOf[Number]
    else if (value.isInstanceOf[Short]) value.asInstanceOf[Number]
    else if (value.isInstanceOf[Int]) value.asInstanceOf[Number]
    else if (value.isInstanceOf[Long]) value.asInstanceOf[Number]
    else if (value.isInstanceOf[Float]) value.asInstanceOf[Number]
    else if (value.isInstanceOf[Double]) value.asInstanceOf[Number]
    else throw ExpressionException("The value of " +name + " is not numeric", this)
  }

  def calculateBoolean(context: Parameters): Boolean = {
    val value = calculate(context)
    if (classOf[Boolean].isInstance(value)) value.asInstanceOf[Boolean]
    else throw ExpressionException("The value of " +name + " is not a boolean", this)

  }

}