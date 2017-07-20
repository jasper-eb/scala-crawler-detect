package com.eventbrite.datafoundry

import java.io.InputStream

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex
import scala.io.Source.fromInputStream

class CrawlerDetect {
  //Can't use nio, falling back to input streams
  //When spark is compatible with scala 2.12 change
  //to scala.io.Source.fromInputStream
  def getFile(file: String): InputStream = getClass.getResourceAsStream(file)
  val exclusionList = loadPatterns("/exclusions_regex_list.txt")
  val crawlerList = loadPatterns("/crawler_regex_list.txt")
  val exclusionRegex = exclusionList.mkString("|").r

  def loadPatterns(file: String): List[Regex] = {
    val lines = fromInputStream(getFile(file)).getLines.toList
    val lb: ListBuffer[Regex] = new ListBuffer()
    lines.foreach(l => lb += regexIgnoreCase(l).r)
    lb.toList
  }

  def regexIgnoreCase(regex: String): String = "(?i)" + regex

  def isCrawler(userAgent: String): Boolean = {
    val cleanAgent = exclusionRegex.replaceAllIn(userAgent, "").stripMargin
    val resultList = for(pattern <- crawlerList) yield {
      pattern.findFirstIn(cleanAgent).isDefined
    }
    resultList.max
  }

  private def checkPath(path: String): String = {
    if(path.last.toString == "/") path else path + "/"
  }
}
