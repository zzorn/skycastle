package org.skycastle.core.entity.types

import java.io.{BufferedInputStream, InputStreamReader, InputStream}
import org.scalaprops.parser.{JsonBeanParser, BeanParser}
import org.skycastle.client.wrappers.{ColorBean}
import org.skycastle.client.appearance.{BoxAppearance}
import org.scalaprops.{BeanFactory, PropertyBean, Bean}
import org.skycastle.util.parameters.Parameters

/**
 * Loader for loading EntityTypes.
 */
object EntityTypeLoader {

  val entityTypeFileExtension = ".conf"
  val parser: BeanParser = new JsonBeanParser()

  def registerBeanType[T <: Bean](kind: Class[T]) {
    registerBeanType(kind, Symbol(kind.getSimpleName))
  }

  def registerBeanType[T <: Bean](kind: Class[T], name: Symbol) {
    parser.beanFactory.registerBeanType(name, {() => kind.newInstance})
  }

  def load(inputStream: InputStream, sourceName: String): Archetype = {
    val streamReader = new InputStreamReader(new BufferedInputStream(inputStream))

    val assetName: Symbol = prepareName(sourceName)
    val loadedData: Bean = parser.parse(streamReader, assetName.name)

    streamReader.close

    // TODO: New loader that loads JSON into EntityParameters.
    /*
    // TODO: Add an immutable EmptyBean or similar object.
    val facetTypes: List[FacetType] = (loadedData.get[Bean]('facets, new PropertyBean()).toMap map extractFacetType).toList
    val entityParameters: Parameters = Parameters(loadedData.toMap - 'facets)
    new Archetype(assetName, entityParameters, facetTypes)
    */
    new Archetype('fixme, new EntityParameters())
  }

  private def prepareName(sourceName: String): Symbol = {
    Symbol(sourceName.stripSuffix(entityTypeFileExtension)
                     .replace("/", ".")
                     .replace("\\", "."))
  }
/*
  private def extractFacetType(entry: (Symbol, AnyRef)): FacetType = {
    if (entry._2.isInstanceOf[Bean]) {
      val facetName: Symbol = entry._1
      val data: Bean = entry._2.asInstanceOf[Bean]
      new FacetType(facetName, Parameters(data.toMap))
    }
    else throw new IllegalArgumentException("Facets should be beans")
  }
*/
}