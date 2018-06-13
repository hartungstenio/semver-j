# SemVer-J
A Java API for parsing and checking [Semantic Versioning](http://semver.org/).

## Features
* Version parsing
* Version equality check

### What's to come
* Full version comparison (greater than, compatible with, etc.)
* Maven

## Getting Started
The SemVer-J API is intented to be a standalone API for version comparison. No dependencies. You should depend on it ;)

### Quickstart
Parsing the version information

```java
// Create a new version from a string
Version version = Version.valueOf("1.0.0-alpha.1");

// Use the information
version.getMajor(); // 1
version.getMinor(); // 0
version.getPatch(); // 0
version.getPreReleaseIdentifiers(); // [alpha, 1]
version.getBuildMetadata(); // []

// Are the versions compatible?
CompatibleComparator comparer = new CompatibleComparator();
comparer.compare(version, other); // compatible versions (same version major version)
```

## Problems?

If you find any issues please [report them](https://github.com/hartungstenio/semver-j/issues) or better, send a [pull request](https://github.com/hartungstenio/semver-j/pulls).

## Authors:
* Christian Hartung