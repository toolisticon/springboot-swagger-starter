# Springboot Swagger Starter

[![Build Status](https://travis-ci.org/toolisticon/springboot-swagger-starter.svg?branch=master)](https://travis-ci.org/toolisticon/springboot-swagger-starter) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.springboot/springboot-swagger-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.springboot/springboot-swagger-starter)
[![codecov](https://codecov.io/gh/toolisticon/springboot-swagger-starter/branch/master/graph/badge.svg)](https://codecov.io/gh/toolisticon/springboot-swagger-starter)

Springboot Swagger Starter allows to create SpringFox Dockets using Springboot. We make use of SpringFox library, but ban the boilerplate code needed for creation of a Docklet. So instead of providing a `@Bean` that returns your Docket, you can just specify it using your properties or YAML file.

## Installation and requirements

Minimal tested versions:

- SpringFox Swagger 2.7.0
- Springboot 1.5.9.RELEASE

Put the following dependency to your Maven POM file:

    <dependency>
      <groupId>io.toolisticon.springboot</groupId>
      <artifactId>springboot-swagger-starter</artifactId>
      <version>0.0.2</version>
    </dependency>
    
    
In your `application.yml` add the following block:

    swagger:
      enabled: true
      redirect: true
      dockets:
        muppetShow:
          basePackage: "biz.muppetshow.rest"
          path: "/show/**"
          apiInfo:
            title: The Muppet Show API
            description: Use this to get a greeting from Kermit.
            version: 0.1
            termsOfService: use on your own risk
            lisense: APACHE-2.0
            licenseUrl: LICENSE.txt
            contact:
              name: Kermit The Frog
              email: kermit@muppetshow.biz
              url: http://muppetshow.biz
        admin:
          basePackage: "biz.muppetshow.admin"
          path: "/admin/**"
    
## Features 

The library supports configuration of SpringFox Dockets our of `application.yml` or `application.properties`. 
All properties are prefixed with `swagger`. The following properties are available:

<table>
  <tr>
    <th>Property</th><th>Description</th><th>Required</th><th>Default</th><th>Example</th>
  </tr>
  <tr>
    <td>swagger.enabled</td><td>Controls if the configuration with properties is enabled. Set to `false` if you want to disable this feature.</td><td>yes</td><td>true</td><td>false</td>
  </tr>
  <tr>
    <td>swagger.redirect</td><td>Controls if the request to `/` should be redirected to `swagger-ui`.</td><td>no</td><td>false</td><td>true</td>
  </tr>
  <tr>
    <td>swagger.dockets</td><td>Defines a list of named Docket groups. Every key in this list is a name of the group configures with its elements.</td><td>no</td><td>empty</td><td>see below</td>
  </tr>
  <tr>
    <td>swagger.dockets.[groupname]</td><td>Defines group `groupname`.</td><td>yes</td><td></td><td>myGroup</td>
  </tr>
  <tr>
    <td>...[groupname].basePackage</td><td>Base package for the scan of for Swagger-annotated classes.</td><td>yes</td><td>empty</td><td>biz.muppetshow.rest</td>
  </tr>
  <tr>
    <td>...[groupname].path</td><td>Ant path matcher limiting the group operation by URL.</td><td>no</td><td>/**</td><td>"/show/**"</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo</td><td>API info for this group.</td><td>no</td><td></td><td>see below</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo.title</td><td>Title of the API for this group.</td><td>no</td><td></td><td>My API</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo.description</td><td>Description of the API for this group.</td><td>no</td><td></td><td>This is a nice API.</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo.version</td><td>Version of the API for this group.</td><td>no</td><td></td><td>1</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo.termsOfServiceUrl</td><td>Terms of service of the API for this group.</td><td>no</td><td></td><td>Use on your own risk.</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo.license</td><td>License of the API for this group.</td><td>no</td><td></td><td>APACHE 2.0</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo.licenseUrl</td><td>License url of the API for this group.</td><td>no</td><td></td><td>http://my.com/license/</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo.contact.name</td><td>Contact name of the API for this group.</td><td>no</td><td></td><td>Kermit The Frog</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo.contact.email</td><td>Contact email of the API for this group.</td><td>no</td><td></td><td>kermit@muppetshow.biz</td>
  </tr>
  <tr>
    <td>...[groupname].apiInfo.contact.url</td><td>Contact url of the API for this group.</td><td>no</td><td></td><td>http://muppetshow.biz</td>
  </tr>
</table>


## Building

We use Kotlin and Maven in this project. Make sure you have a recent Apache Maven Version installed or use `mnvw` wrapper.

Run to build: 

    mvn clean install  


## Contributing

### Issues

[https://github.com/toolisticon/springboot-swagger-starter](https://github.com/toolisticon/springboot-swagger-starter)

### Contributors

*  _[Jan Galinski](https://github.com/galinski)_
*  _[Simon Zambrovski](https://github.com/zambrovski)_

### License

[BSD-3-Clause](https://github.com/toolisticon/springboot-swagger-starter/blob/master/LICENSE)


