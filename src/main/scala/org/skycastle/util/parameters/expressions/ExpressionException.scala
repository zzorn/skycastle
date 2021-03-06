package org.skycastle.util.parameters.expressions

/**
 * Thrown if there was some problem with an expression.
 */
case class ExpressionException(message: String, exp: Expr) extends Exception("Problem in expression "+exp.name + ": " + message)