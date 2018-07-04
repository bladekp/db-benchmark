# Prerequisite

1. Install
    * Spring CLI
    * maven
    * git
    * java v1.8+
    * mysql
    * postgresql
    * oracle
    * mongodb
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

3. Integration tests

   mvn verify
    
# Todo

* mongodb queries

# Scratchpad

## Convert SQL inserts into mongo 

    while read line; do 
        data=`echo $line | cut -d'(' -f3`; 
        printf "{code:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f1 | tr -d $'\n' >> country.init.mongo;  
        printf ",name:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f2 | tr -d $'\n' >> country.init.mongo;  
        printf ",continent:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f3 | tr -d $'\n' >> country.init.mongo;
        printf ",region:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f4 | tr -d $'\n' >> country.init.mongo;    
        printf ",surfacearea:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f5 | tr -d $'\n' >> country.init.mongo;  
        printf ",independenceyear:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f6 | tr -d $'\n' >> country.init.mongo;  
        printf ",population:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f7 | tr -d $'\n' >> country.init.mongo;  
        printf ",lifeexpentancy:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f8 | tr -d $'\n' >> country.init.mongo;  
        printf ",gnp:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f9 | tr -d $'\n' >> country.init.mongo;  
        printf ",gnpold:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f10 | tr -d $'\n' >> country.init.mongo;  
        printf ",localname:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f11 | tr -d $'\n' >> country.init.mongo;  
        printf ",govermentform:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f12 | tr -d $'\n' >> country.init.mongo;
        printf ",headofstate:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f13 | tr -d $'\n' >> country.init.mongo;    
        printf ",capital:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f14 | tr -d $'\n' >> country.init.mongo;  
        printf ",code2:" >> country.init.mongo
        printf "${data::-1}" | cut -d',' -f15 | tr -d $'\n' >> country.init.mongo;  
        printf "}\n" >> country.init.mongo
        echo $line
    done < country.init.sql
    
    while read line; do 
        data=`echo $line | cut -d'(' -f3`; 
        printf "{name:" >> town2.init.mongo
        printf "${data::-1}" | cut -d',' -f1 | tr -d $'\n' >> town2.init.mongo;  
        printf ",country:" >> town2.init.mongo
        printf "${data::-1}" | cut -d',' -f2 | tr -d $'\n' >> town2.init.mongo;  
        printf ",district:" >> town2.init.mongo
        printf "${data::-1}" | cut -d',' -f3 | tr -d $'\n' >> town2.init.mongo;
        printf ",population:" >> town2.init.mongo
        printf "${data::-1}" | cut -d',' -f4 | tr -d $'\n' >> town2.init.mongo;  
        printf "}\n" >> town2.init.mongo
        echo $line
    done < town2.init.sql #same for town1
    
    while read line; do 
        data=`echo $line | cut -d'(' -f3`; 
        printf "{country:" >> language.init.mongo
        printf "${data::-1}" | cut -d',' -f1 | tr -d $'\n' >> language.init.mongo;  
        printf ",name:" >> language.init.mongo
        printf "${data::-1}" | cut -d',' -f2 | tr -d $'\n' >> language.init.mongo;  
        printf ",isofficial:" >> language.init.mongo
        printf "${data::-1}" | cut -d',' -f3 | tr -d $'\n' >> language.init.mongo;
        printf ",percentage:" >> language.init.mongo
        printf "${data::-1}" | cut -d',' -f4 | tr -d $'\n' >> language.init.mongo;  
        printf "}\n" >> language.init.mongo
        echo $line
    done < language.init.sql
       
add ',' on the end of line
       
    while read line; do
        printf "$line" >> language.init.js;
    done < language.init.mongo   
    
after that replace all 'NULL' with 'null'

add

    db.country.insert([
    
on the beginning of file

and

    ]);
    
on the end

execute

    mongo.exe benchmark country.init.js

same for town and language
            
