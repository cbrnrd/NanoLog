# NanoLog [ ![Download](https://api.bintray.com/packages/codepace/NanoLog/NanoLog/images/download.svg) ](https://bintray.com/codepace/NanoLog/NanoLog/_latestVersion)
A simple and extremely small logging library for Java.

## Getting Started

A simple program using NanoLog looks like so:

```java
import io.codepace.nanolog.NanoLog;

public class Example{
  public static void main(String[] args){
    NanoLogger logger = new NanoLogger(HOME_DIR + "/example");
    logger.log("Message here", LogType.INFO);
  }
}
```

## Installing
***I am in the process of uploading the library on jCenter, for now, build the project from source***

To install and use NanoLog in your projects you can download the source and build it with gradle, or
you can include it in your project dependencies:

#### Gradle (build.gradle)

```
compile 'io.codepace:nanolog:1.0'
```


#### Maven (pom.xml)

```xml
<dependency>
  <groupId>io.codepace</groupId>
  <artifactId>nanolog</artifactId>
  <version>1.0</version>
</dependency>
```

## Documentation
Available [here](https://cbrnrd.github.io/NanoLog/)

## Authors
- Carter Brainerd (cbrnrd) - [GitHub](https://github.com/cbrnrd)

## Legal stuff 
NanoLog is licensed under the MIT license (see [LICENSE](https://github.com/cbrnrd/NanoLog/blob/master/LICENSE))

## Built with
* [Gradle](https://gradle.org/)

## Thank you

Thank you for using NanoLog 👏.
If you're feeling generous, donations are always appreciated:

```
19XiyrvqyYNLehf89ckBjPQYCfW77F9rx7 (Ƀ, BTC)
0xf6f247e4a929890926F88144111f5E27d87bD07a (ETH)
LQRUJUpSkmi5BfT6nyPVNKKoLWbnpZ64sL (Ł, LTC)
https://www.paypal.me/0xCB (PayPal)
```
