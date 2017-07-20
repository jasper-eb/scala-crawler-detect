# Purpose
This library will identify bot based on user agents. It does this using regex defined in `src/main/resources/crawler_regex_list.txt`.

This is based on/a port of [Crawler-Detect](https://github.com/JayBizzle/Crawler-Detect).

## Testing
Run `sbt test`.

## Compiling
Simply run `sbt package`.

## Usage
Using the `isCrawler` method will return a `Boolean`. Before checking this the user agent is cleaned up to prevent false positives.
```Scala
import com.eventbrite.datafoundry.CrawlerDetect

val cd = new CrawlerDectect
val userAgent: String = ???
cd.isCrawler(userAgent)
```
