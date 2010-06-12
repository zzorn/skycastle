package org.skycastle.core.platform.configuration.directory

import org.skycastle.core.platform.configuration.ConfigurationService
import org.skycastle.core.data.loader.DataLoader
import java.io.File

/**
 * Loads configuration from a directory.
 */
// TODO: Change default to ./conf
class DirectoryConfigurationService(path: String = "./src/main/data") extends ConfigurationService {
  protected def loadConfiguration() = DataLoader.loadDirectory(new File(path))
}