# RealTimeMonitoring

Real Time Test Monitoring: 
This project aims to provide a simple way to get some live statistics about ongoing tests before getting the reports:

This is a maven project, using Junit 4 and selenium to run E2E tests, and some other libraries like: https://github.com/oshi/oshi
to get an abstraction layer for the hardware part. 

This project is aimed to be coupled with InfluxDB and GRAFANA, which will provide the support to save and visualize data.

Before running Your tests, make sure to:
 
   1- launch the docker-compose file to get InfluxDB and GRAFANA running, using  the command ;
# docker-compose up -d

   2- Create a DataBase using this command (The name here is Selenium, change it if you want):
#  curl -i -XPOST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE Selenium"

   3- Open GRAFANA, select data source and connect InfluxDB instance to GRAFANA.
   
   4- Create a Dashboard on GRAFANA and use the Json configuration file included in this project to set the Dashbord up.
   
   5- Fill the Configuration.properties file with your local properties:
      - Database Name
      - Database URL
      - Driver binaries paths for Windows users


You can run this project using this commands : 
# ./mvnw clean -Dbrowser=chrome test
# ./mvnw clean -Dbrowser=firefox test
# ./mvnw clean -Dbrowser=IE test




