import com.github.retronym.SbtOneJar._
import NativePackagerKeys._
import sbtdocker.Dockerfile
import sbtdocker.Plugin.DockerKeys._

oneJarSettings

name := "hello-scala"

version := "1.4.5"

organization := "muktaa"

scalaVersion := "2.11.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.6" % "test"

mainClass in (Compile, run) := Some("hello.Hello")

dockerSettings

docker <<= (docker dependsOn oneJar)

dockerfile in docker <<= (artifactPath.in(Compile, oneJar), managedClasspath in Compile, mainClass.in(oneJar)) map {
  case (jarFile, classpath, Some(mainClass)) =>
    new Dockerfile {
      // Base image
      from("dockerfile/java")
      // Add the generated JAR file
      val jarTarget = s"/app/${jarFile.getName}"
      add(jarFile, jarTarget)
      // Make a colon seperated classpath with the JAR file
      val classpathString = jarTarget
      // On launch run Java with the classpath and the found main class
      entryPoint("java", "-jar", classpathString)
    }
  case (_, _, None) =>
    sys.error("Expected exactly one main class")
}

publishTo := {
  val url = "http://muktaa.artifactoryonline.com/muktaa/"
  if (isSnapshot.value)
  Some("snapshots" at url + "ext-snapshots-local")
    else
  Some("releases" at url + "ext-releases-local")
}
