package org.skycastle.core.entity.types

import java.io.{BufferedInputStream, InputStreamReader, InputStream}
import org.scalaprops.parser.{JsonBeanParser, BeanParser}
import org.scalaprops.{PropertyBean, Bean}

/**
 * Loader for loading EntityTypes.
 */
object EntityTypeLoader {

  val entityTypeFileExtension = ".conf"

  def load(inputStream: InputStream, sourceName: String): EntityType = {
    val streamReader = new InputStreamReader(new BufferedInputStream(inputStream))

    val parser: BeanParser = new JsonBeanParser()
    val assetName: Symbol = prepareName(sourceName)
    val loadedData: Bean = parser.parse(streamReader, assetName.name)

    streamReader.close

    // TODO: Add an immutable EmptyBean or similar object.
    val facetTypes: List[FacetType] = (loadedData.get[Bean]('facets, new PropertyBean()).toMap map extractFacetType).toList
    val entityParameters: Map[Symbol, Any] = loadedData.toMap - 'facets
    new EntityType(assetName, entityParameters, facetTypes)
  }

  private def prepareName(sourceName: String): Symbol = {
    Symbol(sourceName.stripSuffix(entityTypeFileExtension)
                     .replace("/", ".")
                     .replace("\\", "."))
  }

  private def extractFacetType(entry: (Symbol, AnyRef)): FacetType = {
    if (entry._2.isInstanceOf[Bean]) {
      val facetName: Symbol = entry._1
      val data: Bean = entry._2.asInstanceOf[Bean]
      new FacetType(facetName, data.toMap)
    }
    else throw new IllegalArgumentException("Facets should be beans")
  }

}