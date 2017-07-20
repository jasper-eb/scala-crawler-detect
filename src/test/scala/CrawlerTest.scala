import java.nio.file.{Files, Paths}

import scala.collection.JavaConverters._
import com.eventbrite.datafoundry.CrawlerDetect
import org.scalatest.FunSuite

class CrawlerTest extends FunSuite {

  val detector = new CrawlerDetect

  test("All known useragent crawlers should return true") {
    val uas = Files.readAllLines(Paths.get("src/main/resources/known_crawler_useragents.txt")).asScala
    uas.foreach(ua =>
      assert(detector.isCrawler(ua))
    )
  }

  test("A regular useragent should return false") {
    val ua = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36"
    assert(!detector.isCrawler(ua))
  }
}
