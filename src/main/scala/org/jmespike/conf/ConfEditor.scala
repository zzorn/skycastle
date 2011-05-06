package org.jmespike.conf

import java.awt.{BorderLayout, Dimension}
import java.awt.Window._
import javax.swing._
import java.awt.event.{ActionEvent, ActionListener}
import org.scalaprops.{Property, Bean, BeanListener}
import org.scalaprops.exporter.JsonBeanExporter
import java.io._
import net.miginfocom.swing.MigLayout
import org.scalaprops.ui.editors.{NestedBeanEditor, BeanEditor}
import org.jmespike.{Conf, GameConf, Context}

/**
 *
 */
class ConfEditor() {


  private val frame = new JFrame()
  private var settings: Conf = null
  private val mainPanel = new JPanel(new BorderLayout())
  private var levelEditorUi: NestedBeanEditor[_] = null
  private val changeListener= new BeanListener {
    def onPropertyRemoved(bean: Bean, property: Property[ _ ]) {}
    def onPropertyAdded(bean: Bean, property: Property[ _ ]) {}
    def onPropertyChanged(bean: Bean, property: Property[ _ ]) {
      reLoadSettings()
    }
  }

  private var mostRecentSavePath: File = new File("/home/zzorn/projects/ludumdare20/assets/config/")

  def setSettings(_settings: Conf) {
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

  def start() {
    levelEditorUi = makeEditorUi

    if (levelEditorUi!=null) mainPanel.add(levelEditorUi, BorderLayout.CENTER)

    val buttonPanel = new JPanel(new MigLayout())

    mainPanel.add(buttonPanel, BorderLayout.NORTH)
    buttonPanel.add(makeReloadButton)
//    buttonPanel.add(makeLoadLevelButton)
    buttonPanel.add(makeSaveButton)

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

  private def reLoadSettings() {
    if (settings != null) Context.reloadConf()
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

  private def save(settings: Bean) {
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
}