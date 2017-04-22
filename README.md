# dsp-rtl-source
Realtek RTL SDR driver for use with [dsp-common](https://github.com/radiowitness/dsp-common).

## Configure your SDR
Copy the file `example-rtl.properties` to another file named `rtl.properties`,
this file is read from your working directory at runtime and used to configure
your SDR.

## Build
```
$ mvn package
```

## Usage
Copy `target/dsp-rtl-source-x.jar` into the classpath of your dependant project.

## License
Copyright 2017 An Honest Effort LLC
Licensed under the GPLv3: http://www.gnu.org/licenses/gpl-3.0.html
