package org.skycastle.core.data.context

import org.skycastle.core.data.Value
import collection.immutable.List

object EmptyDataContext extends DataContext {

  def getEvaluatedValue(path: List[Symbol]): Option[Value] = None

/*
  def getValue(path: List[Symbol]): Option[Value] = None
  def getContext(path: List[Symbol]) = None
*/
}

/**
 * Defines the context of a value, that is, the other visible named value from the point of the value. 
 */
trait DataContext {


  // TODO: Maybe we need to create a parallel structure for evaluated values,
  // and if an unevaluated version is encountered lazily evaluate it?
  //  - sounds good, lazily evaluates only as much as needed, and allows cahcing the evaluated result for later use.
  def getEvaluatedValue(path: List[Symbol]): Option[Value]

/*

  // TODO: Group the methods together?  Return a tuple?
  def getValue(path: List[Symbol]): Option[Value]

  /**
   * The context for the specified path relative to this context.
   */
  // TODO: How to implement?
  // Maybe we need to create a parallel structure for evaluated values, and if an unevaluated version is encountered lazily evaluate it? - sounds good, lazily evaluates only as much as needed, and allows cahcing the evaluated result for later use.
  def getContext(path: List[Symbol]): Option[DataContext]
*/

}