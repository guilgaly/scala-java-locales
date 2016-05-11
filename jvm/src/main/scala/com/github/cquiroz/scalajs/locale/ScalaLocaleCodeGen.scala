package com.github.cquiroz.scalajs.locale

import java.io.File
import java.nio.file.{Files, Path, Paths}
import javax.xml.parsers.SAXParserFactory

import scala.collection.JavaConverters._
import scala.xml._

object ScalaLocaleCodeGen extends App {

  def constructClass(f: File, xml: Elem): String = {
    val language = (xml \ "identity" \ "language" \ "@type").text
    val territory = Option((xml \ "identity" \ "territory" \ "@type").text).filter(_.nonEmpty)
    val variant = Option((xml \ "identity" \ "variant" \ "@type").text).filter(_.nonEmpty)
    f.getName + ":" + language + territory.map(t => s"_$t").getOrElse("") + variant.map(t => s"_$t").getOrElse("")
  }

  val parser: SAXParser = {
    // Use a non validating parser for speed
    val f = SAXParserFactory.newInstance()
    f.setNamespaceAware(false)
    f.setValidating(false)
    f.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
    f.newSAXParser()
  }

  val files = Files.newDirectoryStream(Paths.get("jvm/src/main/resources/common/main")).iterator().asScala

  val clazzes = for {
    f <- files.map(_.toFile)
  } yield constructClass(f, XML.withSAXParser(parser).loadFile(f))

  clazzes.foreach(println)
}