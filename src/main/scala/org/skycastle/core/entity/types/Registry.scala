package org.skycastle.core.entity.types

/**
 * Registry for various classes that need to be instantiated.
 */
object Registry {

  private var registry: Map[(Symbol, Symbol), Class[_]] = Map()

  def registerType(category: Symbol, kind: Class[_]): Unit = registerType(category, kind, Symbol(kind.getSimpleName))
  def registerType(category: Symbol, kind: Class[_], alias: Symbol): Unit = {
    registry += ((category, alias) -> kind)
  }

  def createType[T](category: Symbol, alias: Symbol): T = {
    registry.get((category, alias)) match {
      case Some(kind) =>
        try {
          kind.newInstance().asInstanceOf[T]
        }
        catch {
          case e: ClassCastException => throw new RegistryException(category, alias, "Could not cast the created instance to the requested type", e)
          case e => throw new RegistryException(category, alias, "Problem instantiating the requested type", e)
        }
      case None =>
        throw new RegistryException(category, alias, "Requested type not found.")
    }
  }


}

class RegistryException(category: Symbol, alias: Symbol, message: String, cause: Throwable = null)
        extends Exception("Problem when trying to create instance of registered type "+category.name+"."+alias.name + ": " +message + ": "+(if (cause != null) cause.getMessage else ""), cause)


