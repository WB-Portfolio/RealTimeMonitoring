node {
   try {
        stage('Preparation - Launching InfluxDB and GRAFANA and cleaning mvn') {
           sh "docker-compose up -d"
           sh "./mvnw clean"
        }

        stage('Running tests') {
            sh "./mvnw -Dbrowser=chrome test"
        }
   }
   catch (e) {
       currentBuild.result = "FAILED"
       throw e
   }
   finally {
       stage('Stopping and removing the containers') {
           sh "docker-compose down"
       }
   }
}