# OpenLMIS Example Extension Module
This example is meant to show an OpenLMIS 3.x Example Extension Module at work.

## Prerequisites
* Gradle 2.14+

## Quick Start
1. Fork/clone this repository from GitHub.

 ```shell
 git clone https://github.com/weronika-ciecierska/openlmis-example-extension.git
 ```
2. Fork/clone `openlmis-example` repository from GitHub.

 ```shell
 git clone https://github.com/OpenLMIS/openlmis-example.git
 ```
3. Specify mainProjectPath (path to `openlmis-example`) in `gradle.properties` file.
4. To assemble the outputs of project and create jar file run `gradle assemble`.
5. To be able to use extension module with `openlmis-example` put jar file from `build/libs` to `etc/openlmis/extensions`.
6. Edit configuration file `extensions.properties` from openlmis-example repository to use your defined extension.
7. Run `openlmis-example` and go to `http://<yourDockerIPAdress>:8080/extensionPoint` to see
that the extended implementation of OrderQuantity interface is used.

## <a name="extensions"></a> Example of extensions usage

In the sample repository, the following classes are example of extensions usage:

- **AMCOrderQuantity.java** - class extending `OrderQuantity` interface from `openlmis-example` repository with `Component` annotation
that contains extension ID.


## <a name="extensions"></a> Extensions & configuration file directory

It is possible to change the path where the jar files and configuration file should be placed.
It is defined in `docker-compose.yml` file of openlmis-example service:

```
volumes:
    - '/etc/openlmis/extensions:/extensions'
```