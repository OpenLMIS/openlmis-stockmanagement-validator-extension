# OpenLMIS Stock Management Validator Extension Module
This example is meant to show an OpenLMIS 3.x Stock Management Validator Extension Module at work.

## Prerequisites
* Docker 1.11+

## Quick Start
1. Fork/clone this repository from GitHub.

 ```shell
 git clone https://github.com/OpenLMIS/openlmis-stockmanagement-validator-extension.git
 ```
2. Fork/clone `openlmis-stockmanagement` repository from GitHub.

 ```shell
 git clone https://github.com/OpenLMIS/openlmis-stockmanagement.git
 ```
3. To assemble the outputs of project and create jar file run `docker-compose -f docker-compose.yml run builder`.
4. Edit configuration file `extensions.properties` from `openlmis-example-extensions` repository to use your defined extension.
5. Run builder for `openlmis-example-extensions` and build image.
6. Run `openlmis-ref-distro` using `docker-compose.openlmis-stockmanagement-validator-extension.yml` and check if your changes has been applied.

## <a name="extensions"></a> Example of extensions usage

In the sample repository, the following classes are example of extensions usage:

- **ExtensionAdjustmentReasonValidator.java** - class extending `AdjustmentReasonValidator` interface from `openlmis-stockmanagement`
repository.