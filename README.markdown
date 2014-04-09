Checkbleed
================

Checkbleed is a command-line utility written in Java (JRE 7+) that allows batch check of URL lists against the [Heartbleed](http://heartbleed.com/) vulnerability.

What you can do today - scan your bookmarks and notify the dizzy website owners before they get 0wn3d.

In order to run checkbleed, you must build it with maven (`mvn assembly:assembly`) and then run it with parameters:

`java -jar target/me.berezovskiy.checkbleed-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file "/path/to/file/here"`