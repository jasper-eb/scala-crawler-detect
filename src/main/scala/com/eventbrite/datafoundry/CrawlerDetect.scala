package com.eventbrite.datafoundry

import java.io.InputStream

import scala.util.matching.Regex
import scala.io.Source.fromInputStream

class CrawlerDetect extends Serializable {
  private val exclusionList = loadPatterns("/exclusions_regex_list.txt")
  private val crawlerList = loadPatterns("/crawler_regex_list.txt")
  private val exclusionRegex = exclusionList.mkString("|").r

  //Can't use nio, falling back to input streams
  //When spark is compatible with scala 2.12 change
  //to scala.io.Source.fromInputStream
  def getFile(file: String): InputStream = getClass.getResourceAsStream(file)

  def loadPatterns(file: String): List[Regex] = {
    val lines = fromInputStream(getFile(file)).getLines.toList
    lines.map(l => regexIgnoreCase(l).r)
  }

  def regexIgnoreCase(regex: String): String = "(?i)" + regex

  def isCrawler(userAgent: String): Boolean = {
    userAgent match {
      case null => false
      case _ => val cleanAgent = exclusionRegex.replaceAllIn (userAgent, "").stripMargin
        val resultList = crawlerList.map (crawler => crawler.findFirstIn (cleanAgent).isDefined)
        resultList.max
    }
  }
}
