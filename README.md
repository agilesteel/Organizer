[Java 7]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[SBT]: https://github.com/harrah/xsbt/wiki

# Organizer 1.0

This tool organizes the files on your system.

## Currently supported files

* mp3
* flac

# Setup

## Requirements 

* [Java 7]
* [SBT]
* Internet connection (in order for [SBT] to be able to download the necessary dependencies).

## Build

1. Get the source code.

		$ git clone https://github.com/agilesteel/Organizer.git
		$ cd Organizer

2. Launch [SBT].

		$ sbt

3. Compile/Test/Run (Only a textual user interface is supported at the moment).

		$ compile
		$ test
		$ run