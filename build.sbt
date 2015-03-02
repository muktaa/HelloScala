import com.github.retronym.SbtOneJar._

oneJarSettings

name := "hello-scala"

version := "1.4"

organization := "learn.scala"

scalaVersion := "2.11.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.6" % "test"

mainClass in (Compile, run) := Some("hello.Hello")
