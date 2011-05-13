package org.jmespike.utils

import java.lang.reflect.Constructor

/**
 * 
 */

object ClassUtils {

  /**
   * The first constructor of the kind type that has one parameter that is a subtype of the specified type, or None if
   * no such constructor exist.
   */
  def getConstructorWithParameter(kind: Class[_], parameterType: Class[_]): Option[Constructor[_]] = {
    val constructors = kind.getConstructors
    constructors.find(c => c.getParameterTypes.length == 1 &&
                           parameterType.isAssignableFrom(c.getParameterTypes.apply(0)))
  }

  /**
   * Creates a new instance of type kind, using a constructor that takes a parameter of the specified type,
   * and using the specified value as argument.
   * Returns the default value if no matching constructor could be found.
   * If parameterType is null or omitted, it will be guessed from the parameter value (which should not be null in that case).
   */
  def newInstance[T <: AnyRef](kind: Class[_], parameter: T, defaultResult: => AnyRef = null, parameterType: Class[T] = null): AnyRef = {
    val paramType = if(parameterType != null) parameterType
                    else parameter.getClass.asInstanceOf[Class[T]]

    getConstructorWithParameter(kind, paramType) match {
      case None => defaultResult
      case Some(c: Constructor[_]) => c.newInstance(parameter)
    }
  }


}