package org.skycastle.server

import _root_.org.skycastle.core.entity.Entity
import java.io.File
import org.skycastle.core.data.loader.DataLoader
import org.skycastle.core.data.Data
import org.skycastle.util.options.OptionParser

/**
 * 
 */
object SkycastleServer {

  def main(args: Array[String]) {
    var dataPath: String = "./config"
    val parser = new OptionParser("SkycastleServer") {
      opt("d", "data", "root directory for data files, defaults to " + dataPath, {v: String => dataPath = v})
    }
    if (parser.parse(args)) {

      // Load configuration data
      val configuration: Data = DataLoader.loadDirectory(new File(dataPath))

      // DEBUG:
      println (configuration.toString)

      val ghostData: Data = configuration('org, 'skycastle, 'games, 'haunted_house, 'Ghost).get.asInstanceOf[Data]
      val ghost: Entity = Entity.create(ghostData)

      println(ghost)
    }

  }
}