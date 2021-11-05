# automation framework with spring
This project shows how SpringBoot can be used in automation testing framework.

## Table of content 
* [General Info](#general-information)
* [Design Patterns](#design-patterns)
* [Features](#features)
* [Running BDD scenarios](#running-bdd-scenarios)
* [Continuous Integration](#continuous-integration)
* [Additional information](#additional-information)
* [Reporting capabilities](#reporting-capabilities)
* [Logging capabilities](#logging-capabilities)
* [Spring extended description](#spring-extended-description)
* [BDD extended description](#bdd-extended-description)
* [Room for Improvement](#room-for-improvement)

## General Information
- Purpose of this project is to demonstrate how an automation framework may look like with the support of SpringBoot.
- Framework is created as Maven project, it focuses on User Interface (web shop) and test cases are written with BDD approach (with support of Cucumber and Gherkin language). For reporting there is Extent Report used, and for logging Log4j. CI works with Github Actions.


## Design patterns
- There is design pattern used for Selenium: POM (Page Object Model) with the usage of Page Factory class.
    - POM is a design pattern used in Selenium, where we create an object repository to store web elements. Package `pages` consists java classes in which there are locators present which correspond to WebElements on specific pages.
      PageFactory is a way of implementing the POM.
      
- There are also 2 design patterns: Singleton and Strategy.
  - Strategy Pattern is used in Web Drivers, because I want to check more just than one driver.
  - Singleton Pattern is used in connection to Web Driver. I want a single instance of a driver to be reachable from any place from a code. 
   

## Features
- There are 2 basic BDD scenarios in `features` package : SignIn and Checkout. 


## Running BDD scenarios
- `RunTests` class in `automation/glue` package is responsible for running tests.
  Annotation `RunWith` specifies with what library those tests run `@RunWith(Cucumber.class)`. There are also two options : first one `plugin` is for creating a report and second one `features` is to link to feature files.
- Running class `RunTests` will run feature files specified in feature="..." option.


## Continuous integration
-  CI is done thanks to Github Actions. Selected workflow builds a Java Maven project when any changes are merged to master. Workflow is in `.github/workflows` package in file `ci.yml`.


## Additional information
- To avoid hardcoding, values which won’t change and are needed through entire framework are in `Constants` class.
- Password is encoded with base64 api (present in `Utils` class), so it is not publicly visible.
- As mentioned above Locators are in pages package and every POM class has a corresponding name to specific page.
- There can be screenshots taken (implementation of it is in `Utils` class).


## Reporting capabilities
- Reporting is done after every run of automation suit with the usage of Extent Reports. Results are in `report` package.
- Test cases are in Enum `TestCases` and are incremented through whenever test is started, so a new report in a file is created.
- In `StepDefinition` class there is:
  - `ExtentTest` object which logs actions in report.
  - Object `ExtentReport` which is responsible for creating reports and when it is instantiated, then the path will be created automatically.
  - Defining start of a test case is in `initializeObject()` method under @Before annotation. There is an array of Enums (test case names), and a counter from `Utils` class because we instantiate through this array (counter is incremented every time a test is executed).
  - A method `closeObjects()` under @After annotation states the end of report with `report.endTest()` and `report.flush()` - saving report to disc.
- In steps `test.log()` is added.


## Logging capabilities
- Log4j is used for logging (added as maven dependency).
- Class `Log` in `utils` package consists needed methods (info, warn, error, etc.). In `log4j.xml` file are other parameters.
- `Log.startTest()` method is placed in `initializeObjects()` method with @Before annotation (log file is created at the beginning of every test), and log methods are present in other methods as well.


## Spring extended description
- This is an additional part of README file. It could not be here, but I wanted to clarify how Spring features look in this framework.

    - There is a usage of Spring Boot framework. Automation framework could be used without it, but here it is treated as a foundation for BDD tests.
  It helps to handle objects in a better way thanks to spring beans. They help to manage objects during their whole lifecycle. They provide a way to efficiently manage those objects, to allow the execution of the tests. The framework knows when to inject those objects, when to create them, when to delete them and all other operations that are usually handled by people.  
  This provides a good management of those objects, and it makes everything more efficient in terms of performance, in terms of managing our resources within the framework when test cases are executed.
  Additionally it implements the concept of inversion of control – it is basically a design principle.
  Control of an object is inverted to a more professional dedicated framework, that handles that object and responsibility of the object on our behalf.
  In class `ConfigurationProperties` in `utils` package there is added annotation `@Component` - a spring bean, so it’s a part of inversion of control. Also there are annotations `@Value` which define a variable. Additional annotation `@PropertySource` points to file `framework.properties` where are pairs key-value placed.
    - There is also a basic class `AutomationFrameworkConfiguration` in package `confi`’, which scans framework for components. There is annotation `@Configuration` and annotation `@ComponentScan` where we specify a package where do we want to perform component scan in a search for `@Components` annotations.


## BDD extended description
- Similarly to Spring description, I added more detailed explanation about BDD in this section.

  - Tests are defined as user stories in `features` package. They are written first, and then they are developed further. It is implemented through cucumber library.
  - In `StepDefinition` there is annotation `@ContextConfiguration(classes = AutomationFrameworkConfiguration.class)` which connects Spring and Cucumber. Class provided in brackets will scan framework for @Component annotation, so it includes variables, and they can be injected in runtime in step definition.    
    There is also `@Autowired` annotation which points to `Configuration properties` class in import, and there is an important `initializeObjects()` method. 
    

## Room for Improvement 
- As mentioned above, the idea of this project was to put focus on spring functionalities, so there are only basic features created.
  In time README file can be improved and implementation of new features can also grow.
  