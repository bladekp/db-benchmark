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

1. Just test:

   
    mvn test 

2. Test with coverage:

    
    mvn cobertura:cobertura

and open /target/site/cobertura/index.html in browser
    
# Todo

* unit testing
* separate text field for mongo commands
* unit test coverage