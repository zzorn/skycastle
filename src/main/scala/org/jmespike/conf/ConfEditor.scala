package org.jmespike.conf

import java.awt.{BorderLayout, Dimension}
import java.awt.Window._
import javax.swing._
import java.awt.event.{ActionEvent, ActionListener}
import org.scalaprops.{Property, Bean, BeanListener}
import org.scalaprops.exporter.JsonBeanExporter
import java.io._
import net.miginfocom.swing.MigLayout
import org.scalaprops.ui.editors.{NestedBeanEditor}
import org.scalaprops.parser.ParseError

/**
 *
 */
class ConfEditor[T <: Conf](confChangeListener: (T) => Unit, defaultSavePath: File, confType: Class[T]) {


  private val frame = new JFrame()
  private var settings: T = null.asInstanceOf[T]
  private val mainPanel = new JPanel(new BorderLayout())
  private var levelEditorUi: NestedBeanEditor[_] = null
  private val changeListener= new BeanListener {
    def onPropertyRemoved(bean: Bean, property: Property[ _ ]) {}
    def onPropertyAdded(bean: Bean, property: Property[ _ ]) {}
    def onPropertyChanged(bean: Bean, property: Property[ _ ]) {
      reLoadSettings()
    }
  }

  private var mostRecentSavePath: File = defaultSavePath

  def setSettings(_settings: T) {
    if (_settings != settings) {
      if (settings != null) settings.removeDeepListener(changeListener)

      settings = _settings

      if (levelEditorUi != null) mainPanel.remove(levelEditorUi)
      levelEditorUi = makeEditorUi
      if (levelEditorUi != null) mainPanel.add(levelEditorUi, BorderLayout.CENTER)
      mainPanel.invalidate()
      mainPanel.validate()
      mainPanel.repaint()
      frame.pack()

      if (settings != null) settings.addDeepListener(changeListener)
    }
  }

  def start() {
    levelEditorUi = makeEditorUi

    if (levelEditorUi!=null) mainPanel.add(levelEditorUi, BorderLayout.CENTER)

    val buttonPanel = new JPanel(new MigLayout())

    mainPanel.add(buttonPanel, BorderLayout.NORTH)
    buttonPanel.add(makeReloadButton)
//    buttonPanel.add(makeLoadLevelButton)
    buttonPanel.add(makeSaveButton)
    buttonPanel.add(makeLoadButton)

    setupFrame(mainPanel)
  }

  def setActive(active: Boolean) {
    frame.setVisible(active)
  }

  private def setupFrame(mainPanel: JComponent) {
    frame.setTitle("Level Editor")
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    frame.setPreferredSize(new Dimension(600, 800))
    frame.setContentPane(mainPanel)
    frame.pack()
    frame.setVisible(true)
  }

  private def makeReloadButton: JComponent = {
    val button = new JButton("Reload Game")

    button.addActionListener(new ActionListener{
      def actionPerformed(e: ActionEvent) {
        reLoadSettings()
      }
    })

    button
  }

  /*
  private def makeLoadLevelButton: JComponent = {
    val button = new JButton("Load Level")

    button.addActionListener(new ActionListener{
      def actionPerformed(e: ActionEvent) {
        loadLevel()
      }
    })

    button
  }
*/
  private def makeSaveButton: JComponent = {
    val button = new JButton("Save")

    button.addActionListener(new ActionListener{
      def actionPerformed(e: ActionEvent) {
        save(settings)
      }
    })

    button
  }

  private def makeLoadButton: JComponent = {
    val button = new JButton("Load")

    button.addActionListener(new ActionListener{
      def actionPerformed(e: ActionEvent) {
        load()
      }
    })

    button
  }

  private def reLoadSettings() {
    if (settings != null) confChangeListener(settings)
  }

  /*
  private def loadLevel() {
    if (settings != null) {
      val obj = levelEditorUi.beanSelector.getLastSelectedPathComponent
      if (obj != null && classOf[Level].isInstance(obj)) {
        Ludum20.loadLevel(obj.asInstanceOf[Level])
      }
    }
  }
*/

  private def makeEditorUi: NestedBeanEditor[_] = {
    if (settings != null) {
      val editor: NestedBeanEditor[_] = settings.createNestedEditor()
      editor
    }
    else null
  }

  private def save(settings: T) {
    val exporter = new JsonBeanExporter()

    val fc = new JFileChooser(mostRecentSavePath)
    val result = fc.showSaveDialog(frame)
    if (result == JFileChooser.APPROVE_OPTION) {
      val outfile: File = fc.getSelectedFile
      val writer = new BufferedWriter(new FileWriter(outfile, false))
      exporter.export(settings, writer)
      writer.close()
    }
    mostRecentSavePath = fc.getCurrentDirectory

    /* NOTE: This is needed and only works if we are running from webstart:
    val inputStream = new ByteArrayInputStream(exporter.exportAsString(settings).getBytes("UTF-8"));

    var fss : FileSaveService = null

    try {
      fss = (ServiceManager.lookup("javax.jnlp.FileSaveService")).asInstanceOf[FileSaveService]
    } catch {
      case e: UnavailableServiceException  =>
        fss = null
      println("FileSaveService not available: " + e.getMessage)
    }

    if (fss != null) {
      try {
        val output: FileContents  = fss.saveFileDialog(null, null, inputStream, "settings.json");
        println("Save success: " + (output != null))
      } catch {
        case e: Exception => e.printStackTrace()
      }
    }
    */

  }

  private def load() {
    val parser: ConfParser = new ConfParser()

    val fc = new JFileChooser(mostRecentSavePath)
    val result = fc.showOpenDialog(frame)
    if (result == JFileChooser.APPROVE_OPTION) {
      val file: File = fc.getSelectedFile
      try {
        val bean: Bean = parser.parse(new FileReader(file), file.getPath)

        if (confType.isInstance(bean)) {
          val conf: T = bean.asInstanceOf[T]
          setSettings(conf)
          confChangeListener(conf)
        }
        else {
          JOptionPane.showMessageDialog(frame,
                                        "The selected file doesn't contain a configuration file of type " + confType.getName +
                                        ".\nDid not load the config file.",
                                        "Wrong configuration type",
                                        JOptionPane.WARNING_MESSAGE)
        }
      } catch {
        case p: ParseError =>
          JOptionPane.showMessageDialog(frame,
                                        "The selected file contains syntax errors:\n" + p.getMessage +
                                        "\nDid not load the config file.",
                                        "Parse error",
                                        JOptionPane.ERROR_MESSAGE)
      }
    }
    mostRecentSavePath = fc.getCurrentDirectory

  }
}