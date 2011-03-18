package org.skycastle.core.entity

/**
 * 
 */
class FacetNotFoundException(facet: Manifest[_], message: String) extends Exception("Facet " + facet.erasure + " not found.  " + message)