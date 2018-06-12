# Prerequisite

1. Install
    * Spring CLI
    * maven
    * git
    * java v1.8+
    * mysql
    * postgresql
    * oracle
    * <s>mongodb (not supported yet)</s>
2. Setup application settings

# Run

    mvn spring-boot:run
    
# Usage

![diagram](src/main/resources/static/diagram.png)    
    
1. Go to: http://localhost:8080/benchmark
2. ![form](img/form.png) 
3. ![report](img/report.png)

# Test

    mvn test
    
# Todo

* unit testing
* separate text field for mongo commands
* bigger database
