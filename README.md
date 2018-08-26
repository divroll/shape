#Shape: A fluent HTTP Client for TeaVM (and Javascript soon!)

This library wraps TeamVM XMLHttpRequest into a fluent API.

```
Shape.post("http://httpbin.org/post")
  .queryString("name", "Mark")
  .field("last", "Polo")
  .asJson()
```

## Setup with Maven

Add dependency

```
<dependency>
    <groupId>com.divroll.shape</groupId>
	<artifactId>shape</artifactId>
	<version>0-SNAPSHOT-TEAVM</version>
</dependency>
```

Add Bintray JCenter repository

```
<repositories>
    <repository>
	    <snapshots>
		    <enabled>false</enabled>
		</snapshots>
		<id>bintray-dotweblabs-maven</id>
		<name>bintray</name>
		<url>http://dl.bintray.com/dotweblabs/maven</url>
	</repository>
</repositories>
<pluginRepositories>
	<pluginRepository>
		<snapshots>
			<enabled>false</enabled>
		</snapshots>
		<id>bintray-dotweblabs-maven</id>
		<name>bintray-plugins</name>
		<url>http://dl.bintray.com/dotweblabs/maven</url>
	</pluginRepository>
</pluginRepositories>
```