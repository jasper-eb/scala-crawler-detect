organization := "com.eventbrite"

name := "scala-crawler-detect"

version := "1.0"

scalaVersion := "2.11.8"

// Test dependencies
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
