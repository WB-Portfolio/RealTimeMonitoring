# RealTimeMonitoring

Real Time Test Monitoring: 
This project aims to provide a simple way to get some live statistics about ongoing tests before getting the reports:

This is a maven project, using Junit 4 and selenium to run E2E tests, and some other librarys to get an abstraction layer
for the hardware part. 

This project is aimed to be coupled with InfluxDB and GRAFANA, wich will provide the support to save and visualize data.


You can run this project using this commands : 
# ./mvnw clean -Dbrowser=chrome test
# ./mvnw clean -Dbrowser=firefoxe test
# ./mvnw clean -Dbrowser=opera test




