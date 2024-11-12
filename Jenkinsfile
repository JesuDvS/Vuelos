pipeline {
    agent any
    stages {
        stage('Clonar repositorio') {
            steps {
                git branch: 'master', url: "https://github.com/JesuDvS/Vuelos.git"
            }
        }
        stage('Construir aplicación') {
            steps {
                bat './mvnw.cmd clean package'
            }
        }
        stage('Ejecutar aplicación') {
            steps {
                bat '''
                    set JAVA_OPTS=-Djavafx.platform=any
                    java --module-path "C:/Program Files/JavaFX/lib" --add-modules javafx.controls,javafx.fxml -jar target/Vuelos-0.0.1-SNAPSHOT.jar
                '''
            }
        }
    }
}