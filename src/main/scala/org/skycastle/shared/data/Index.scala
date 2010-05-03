package org.skycastle.shared.data

/**
 * Used to access a value in a container.
 * Is either a symbol or number.
 */
sealed abstract class Index {
  def asSymbol: Option[Symbol] = None
  def asNumber: Option[Long] = None

  def symbol: Symbol = throw new IndexOutOfBoundsException("The index " + this + " is not a symbol"); 
  def number: Long = throw new IndexOutOfBoundsException("The index " + this + " is not a number");
}

case class SymbolIndex(index: Symbol) extends Index {
  // TODO: Check that the symbol is a valid path element (is a java identifier)
  
  override def asSymbol = Some(index)
  override def symbol = index

  override def toString = index.name
}

case class NumberIndex(index: Long) extends Index {
  override def asNumber = Some(index)
  override def number = index

  override def toString = index.toString()
}

