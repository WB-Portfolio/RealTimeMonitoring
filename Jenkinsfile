node {
   try {
       stage('Preparation - Checkout') {
           git branch: 'master',
               url: "https://github.com/WB-Portfolio/RealTimeMonitoring.git"
        }

        stage('Preparation - Launching InfluxDB and GRAFANA and cleaning mvn') {
           sh 'docker-compose up -d'
           sh './mvnw clean'
           sh 'sleep 120'
        }

        stage('Running tests') {
            sh './mvnw -Dbrowser=chrome test'
        }
   }
   catch (e) {
       currentBuild.result = 'FAILED'
       throw e
   }
   finally {
       stage('Stopping and removing the containers') {
           sh 'docker-compose down'
       }
   }
}