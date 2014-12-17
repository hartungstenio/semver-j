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

// Create by setting properties explicitly
Version other = new Version();
other.setMajor(1);
other.setMinor(0);
other.setPatch(0);
other.getPreReleaseIdentifiers().add("alpha");
other.getPreReleaseIdentifiers().add("1");
other.getBuildMetadata().add("first");

// Are both the same version?
VersionComparator comparer = new VersionComparator();
comparer.compare(version, other); // same version - build metadata ignored

other.getPreReleaseIdentifiers().remove(1);
comparer.compare(version, other); // version greater than other
```

## Problems?

If you find any issues please [report them](semver-j/issues) or better, send a [pull request](semver-j/pull-requests).

## Authors:
* Christian Hartung