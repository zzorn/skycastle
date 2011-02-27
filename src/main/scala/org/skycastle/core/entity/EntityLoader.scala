package org.skycastle.core.entity

import com.jme3.asset.{AssetInfo, AssetLoader}
import org.scalaprops.parser.{JsonBeanParser, BeanParser}
import java.io.{InputStreamReader, BufferedInputStream}
import org.scalaprops.Bean
import org.skycastle.util.Logging
import java.lang.String

/**
 * Loads Entities with Facets.
 */
// TODO: Load archetypes instead
@Deprecated
class EntityLoader extends AssetLoader with Logging {

  def load(assetInfo: AssetInfo): Object = {

    val streamReader = new InputStreamReader(new BufferedInputStream(assetInfo.openStream))

    val parser: BeanParser = new JsonBeanParser()
    val assetName: String = assetInfo.getKey.getName
    val loadedData: Bean = parser.parse(streamReader, assetName)

    val entity = new Entity()
    loadedData.toMap.foreach({entry =>
      val name = entry._1
      val value = entry._2
      if (value != null && value.instanceOf[Facet]) {
        logDebug("Adding facet "+name + " to "+assetName)
        val facet: Facet = value.asInstanceOf[ Facet ]
        entity.addFacet(facet)
        facet.init()

      }
      else logWarning("Unknown part '"+name+"' in entity config '"+assetName+"'" )
    })

    streamReader.close

    entity.init()

    entity
  }

}
