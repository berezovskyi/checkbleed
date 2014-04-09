Checkbleed
================

Checkbleed is a command-line utility written in Java (Java SE 7+) that allows batch check of URL lists against the [Heartbleed](http://heartbleed.com/) vulnerability.

What you can do today - scan your bookmarks and notify the dizzy website owners before they get 0wn3d.

In order to run checkbleed, you must build it (`mvn assembly:assembly`) and then run with parameters:

`java -jar target/me.berezovskiy.checkbleed-0.0.1-SNAPSHOT-jar-with-dependencies.jar -file "/path/to/file/here" -d 64`

* `-file` specifies input file
* `-d` specifies thread pool size

Sample input file:

```
https://www.fontfont.com/fonts/mister-k-informal
https://www.ghostery.com/en-GB/faq
https://www.ghostery.com/faq
https://www.glassdoor.com/profile/login_input.htm?u=%2Findex.htm
https://www.gmx.net/
```

Sample output:

```
Ignoring malformed URL `"https://********.**"`
Lines read: 2979
Lines ignored: 54
Domains added: 309
Domain www.babbel.com was not checked (err 2)
Domain redbooth.com was not checked (err 2)
Domain www.hushmail.com was not checked (err 2)
Domain www.djangopackages.com was not checked (err 2)
Domain www.atom.io was not checked (err 2)
Domain coding4fun.codeplex.com was not checked (err 2)
Domain ml.tu-berlin.de was not checked (err 2)
Domain ru.add-ons.mozilla.com was not checked (err 2)
Domain www.pivotdesk.com was not checked (err 2)
Domain www.rescuetime.com was not checked (err 2)
Domain enterprise37.opnet.com was not checked (err 2)
Domain www.domaintools.com was not checked (err 2)
Domain www.yammer.com was not checked (err 2)
Domain console.aws.amazon.com was not checked (err 2)
Domain dev.windowsphone.com was not checked (err 2)
Domain www.microsoft.com was not checked (err 2)
==============================================================
VULNERABLE DOMAINS:
==============================================================
https://*****.**
https://*****.**
https://*****.**
https://*****.**
https://*****.**
https://*****.**
https://*****.**
```

*(err 2)* is an error returned by [heartbleed executable](https://github.com/FiloSottile/Heartbleed) when a connection was forcibly closed by the remote host.

In order to get all URLs from Firefox profile that begin with "https://", use the following SQLite query on `places.sqlite`:

`select distinct url from moz_places where url like 'https://%';`