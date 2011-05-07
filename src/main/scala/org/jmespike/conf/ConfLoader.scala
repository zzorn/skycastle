package org.jmespike.conf

import com.jme3.asset.{AssetInfo, AssetLoader}
import org.scalaprops.parser.JsonBeanParser
import java.io.{InputStreamReader, BufferedInputStream}

/**
 * Loads conf beans from json formatted input file.
 * Implements the JME AssetLoader interface.
 */
class ConfLoader extends AssetLoader {

  val parser = new ConfParser

  def load(assetInfo: AssetInfo): Object = {
    val reader = new InputStreamReader(assetInfo.openStream)

    val bean = parser.parse(reader, assetInfo.getKey.getName)

    reader.close()

    bean
  }
}



