package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * Something that can calculate a value, based on a parameter context.
 */
trait Expr {

  def name = this.getClass.getSimpleName

  def calculate(context: Parameters): Any

  def num(context: Parameters): Number = {
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

  def bool(context: Parameters): Boolean = {
    val value = calculate(context)
    if (classOf[Boolean].isInstance(value)) value.asInstanceOf[Boolean]
    else throw ExpressionException("The value of " +name + " is not a boolean", this)

  }

}

case class Neg(a: Expr) extends Expr {
  def calculate(context: Parameters) = -a.num(context).doubleValue
}


case class Add(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.num(context).doubleValue + b.num(context).doubleValue
}

case class Sub(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.num(context).doubleValue - b.num(context).doubleValue
}

case class Mul(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.num(context).doubleValue * b.num(context).doubleValue
}

case class Div(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.num(context).doubleValue / b.num(context).doubleValue
}


case class And(a:  Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.bool(context) && b.bool(context)
}

case class Or(a:  Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.bool(context) || b.bool(context)
}


case class Not(a:  Expr) extends Expr {
  def calculate(context: Parameters) = !a.bool(context)
}


case class LessThan(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.num(context).doubleValue < b.num(context).doubleValue
}

case class LessThanOrEqual(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.num(context).doubleValue <= b.num(context).doubleValue
}

case class GreaterThan(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.num(context).doubleValue > b.num(context).doubleValue
}

case class GreaterThanOrEqual(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.num(context).doubleValue >= b.num(context).doubleValue
}


// NOTE: Probably doesn't work when comparing e.g. int and long with the same value.
case class EqualTo(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.calculate(context) == b.calculate(context)
}

case class NotEqual(a: Expr, b: Expr) extends Expr {
  def calculate(context: Parameters) = a.calculate(context) != b.calculate(context)
}
