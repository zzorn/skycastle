package org.skycastle.core.data.loader

import java.lang.String
import java.io._
import org.skycastle.core.data.{Value, Data}
import org.skycastle.core.data.parser.DataParser

/**
 * Provides functions for loading data from the file system.
 */
// TODO: Add support to load from resource path.
object DataLoader {
  val extension: String = "data"

  private val dataFileFilter = new FileFilter()
  {
    def accept(pathname: File) =
      pathname.isFile &&
      pathname.getName.endsWith("."+extension) &&
      pathname.getName.length > extension.length + 1
  }

  private val dataDirFilter = new FileFilter()
  {
    def accept(pathname: File) = pathname.isDirectory && !pathname.getName.contains(".")
  }

  private def fileNameToMemberName(f: File): Symbol = {
    var s = f.getName
    if (s.endsWith("." + extension)) s = s.substring(0, s.length -1 -extension.length)
    Symbol(s)
  }

  /**
   * Load all data files from a directory and all subdirectories.
   */
  def loadDirectory(dir: File): Data = {
    if (!dir.exists ) throw new IOException("Specified director '"+dir+"' not found")
    if (!dir.isDirectory) throw new IOException("Specified director '"+dir+"' is not a directory")

    var members: Map[Symbol, Value] = Map()
    members ++= dir.listFiles(dataDirFilter).map(f => Symbol(f.getName) -> loadDirectory(f))
    members ++= dir.listFiles(dataFileFilter).map(f => fileNameToMemberName(f) -> loadDataFile(f))
    new Data(members)
  }

  /**
   * Load a data file
   */
  def loadDataFile(file: File): Value = DataParser.parse(file)

}