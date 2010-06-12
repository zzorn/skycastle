package org.skycastle.server

import java.io.File
import org.skycastle.core.data.loader.DataLoader
import org.skycastle.core.data.Data
import org.skycastle.util.options.OptionParser

/**
 * 
 */
object SkycastleServer {

  def main(args: Array[String]) {
    var dataPath: String = "./src/main/data"
    val parser = new OptionParser("SkycastleServer") {
      opt("d", "data", "root directory for data files, defaults to " + dataPath, {v: String => dataPath = v})
    }
    if (parser.parse(args)) {

      // Load configuration data
      val configuration: Data = DataLoader.loadDirectory(new File(dataPath))

      // DEBUG:
      println (configuration.toString)

    }

  }
}