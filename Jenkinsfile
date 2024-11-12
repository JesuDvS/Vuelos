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
                    java --module-path "C:/Users/coron/Downloads/openjfx-23.0.1_windows-x64_bin-sdk/javafx-sdk-23.0.1/lib" --add-modules javafx.controls,javafx.fxml -jar target/Vuelos-0.0.1-SNAPSHOT.jar
                '''
            }
        }
    }
}