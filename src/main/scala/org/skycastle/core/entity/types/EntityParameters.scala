package org.skycastle.core.entity.types

import org.skycastle.util.parameters.Parameters

/**
 * Utility class for specifying a set of parameters for the different facets in an Entity.
 * Mutable for easier programmatic initialization.
 */
class EntityParameters() {

  var parent: EntityParameters = null

  private var parameters: Map[Symbol, Parameters] = Map()

  /**
   * Initialize these EntityParameters from a set of parameters where the parameter names are prefixed by the facet category and a dot.
   * E.g. Parameters(Map(Symbol("appearance.xOffset") -> 3))
   */
  def this(dotSeparatedParameters: Parameters) {
    this()
    dotSeparatedParameters.parameters.keys foreach {key =>
      val s = key.name
      val dotIndex = s.findIndexOf(_ == '.')
      if (dotIndex > 0 && dotIndex < s.length - 1) {
        val facetCategory = Symbol(s.take(dotIndex))
        val facetProperty = Symbol(s.drop(dotIndex + 1))
        val value = dotSeparatedParameters.parameters.get(key).get
        set(facetCategory, facetProperty, value)
      }
    }
  }

  def setFacetParameters(facetCategory: Symbol, facetParameters: Parameters) {
    if (facetCategory.name.isEmpty) throw new IllegalArgumentException("facetCategory should not be empty")

    parameters += (facetCategory -> facetParameters)
  }

  def set(facetCategory: Symbol, parameter: Symbol, value: Any) {
    if (facetCategory.name.isEmpty) throw new IllegalArgumentException("facetCategory should not be empty")
    if (parameter.name.isEmpty) throw new IllegalArgumentException("parameter name should not be empty")

    parameters += (facetCategory -> (parameters.get(facetCategory) match {
      case Some(fp) => Parameters(fp.parameters + (parameter -> value))
      case None => Parameters(Map(parameter -> value))
    }))
  }

  def getFacetCategories: Set[Symbol] = {
    val facetCategories = parameters.keySet.toSet // toSet needed because of bug http://lampsvn.epfl.ch/trac/scala/ticket/4001 in Scala
    if (parent != null) facetCategories ++ parent.getFacetCategories
    else facetCategories
  }

  def getFacetParameters(facetCategory: Symbol): Parameters = {
    val facetParameters = parameters.get(facetCategory).getOrElse(Parameters())
    if (parent != null) facetParameters chain parent.getFacetParameters(facetCategory)
    else facetParameters
  }

  /**
   * Creates a new set of entity parameters, based on these and using the provided ones as default fallbacks.
   */
  def chain(fallback: EntityParameters): EntityParameters = {
    val eps = new EntityParameters()
    eps.parameters = parameters
    eps.parent = fallback
    eps
  }

  override def toString: String = parameters.iterator.mkString("{", ", ", "}")
}