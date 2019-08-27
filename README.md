[![Hits-of-Code](https://hitsofcode.com/github/hartungstenio/semver-j)](https://hitsofcode.com/hartungstenio/semver-j)
[![Actions Status](https://github.com/hartungstenio/semver-j/workflows/run_tests/badge.svg)](https://github.com/hartungstenio/semver-j/actions)


# SemVer-J
A Java API for parsing and checking [Semantic Versioning](http://semver.org/).

## Features
* Version parsing
* Version comparison
* Compatibility check

### What's to come
* Version adjustment (increment, build, tag, release);

## Getting Started
The SemVer-J API is intended to be a standalone API for version comparison. No dependencies. You should depend on it ;)

### Quickstart
Creating a new version

```java
SemVer version = SemVer.of(1, 0, 0);

// Use the information
version.getMajor(); // 1
version.getMinor(); // 0
version.getPatch(); // 0

SemVer another = SemVer.parse("1.1.0");

// Version comparison
version.isNewerThan(another); // false
version.isOlderThan(another); // true
version.isCompatibleWith(another); // false
another.isCompatibleWith(version); // true
```

## Problems?

If you find any issues please [report them](https://github.com/hartungstenio/semver-j/issues) or better, send a [pull request](https://github.com/hartungstenio/semver-j/pulls).

## Authors:
* Christian Hartung