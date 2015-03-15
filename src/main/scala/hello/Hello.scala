package hello

object Hello {
  def main(args: Array[String]): Unit = {
    var count = 1
    while (true) {
     println(s"Hello, world! #$count")
     Thread.sleep(5000)
     count = count + 1
    }
  }
}

