pipeline {
  environment {
    registry = "jfrog.it-academy.by/public/hospital/deps"
    registryCredential = 'jfrog_sa'
    DOCKERFILE = 'Dockerfile_deps'
  }

  agent {
    label 'docker'
  }

  stages {
    stage('Clone repository') {
      steps {
        deleteDir()
        git(
          url: 'https://github.com/CyganekM/hospital.git',
          credentialsId: "token_github",
          branch: 'main',
          changelog: true,
          poll: true
        )
      }
    }

    stage('Check image exists in registry') {
      steps {
        script {
          withCredentials([usernamePassword(
            credentialsId: 'jfrog_sa',
            usernameVariable: 'JFROG_USER',
            passwordVariable: 'JFROG_PASSWORD'
          )]) {
            def fileExists = sh(script: '''
              # Проверяем HTTP статус код
              STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
                -u $JFROG_USER:$JFROG_PASSWORD \
                "https://jfrog.it-academy.by/artifactory/public/hospital/deps/latest/list.manifest.json")

              if [ "$STATUS" = "200" ]; then
                echo "EXIST"
              else
                echo "NO_EXIST"
              fi
            ''', returnStdout: true).trim()

            env.LATEST_EXISTS = fileExists
            echo "Manifest file exists: ${env.LATEST_EXISTS}"
          }
        }
      }
    }

    stage('Check for pom.xml changes') {
      when {
        expression {
          env.LATEST_EXISTS == "EXIST"
        }
      }
      steps {
        script {
          def changes = sh(script: '''
            if git diff --name-only HEAD~1 HEAD 2>/dev/null | grep -q "pom.xml"; then
              echo "CHANGED"
            else
              echo "UNCHANGED"
            fi
          ''', returnStdout: true).trim()

          env.POM_CHANGED = changes
          echo "pom.xml changed: ${env.POM_CHANGED}"
        }
      }
    }

    stage('Building image') {
      when {
        expression {
          env.POM_CHANGED == 'CHANGED' || env.LATEST_EXISTS == "NO_EXIST"
        }
      }
      steps {
        script {
          echo "Building Docker image..."
          echo "Reason: ${env.POM_CHANGED == 'CHANGED' ? 'pom.xml changed' : 'latest image not found in registry'}"

          dockerImage = docker.build(
            "${registry}:${BUILD_NUMBER}",
            "-f ${DOCKERFILE} ."
          )
          sh "docker tag ${registry}:${BUILD_NUMBER} ${registry}:latest"
        }
      }
    }

    stage('Push Image') {
      when {
        expression {
          env.POM_CHANGED == 'CHANGED' || env.LATEST_EXISTS == "NO_EXIST"
        }
      }
      steps {
        script {
          docker.withRegistry('https://jfrog.it-academy.by/', registryCredential) {
            dockerImage.push()
            dockerImage.push('latest')
          }

          echo "✅ Image pushed successfully:"
          echo "   ${registry}:${BUILD_NUMBER}"
          echo "   ${registry}:latest"
        }
      }
    }

    stage('Remove Unused docker image') {
      when {
        expression {
          env.POM_CHANGED == 'CHANGED' || env.LATEST_EXISTS == "NO_EXIST"
        }
      }
      steps {
        sh '''
          # Безопасное удаление всех образов из нашего registry
          docker rmi $(docker images --filter=reference="jfrog.it-academy.by/*" -q) 2>/dev/null || true

          # Очищаем dangling images
          docker image prune -f
        '''
      }
    }

    stage('Skip build notification') {
      when {
        expression {
          env.POM_CHANGED == 'UNCHANGED' && env.LATEST_EXISTS == "EXIST"
        }
      }
      steps {
        echo "⏭️ Skipping Docker build"
        echo "Reason: pom.xml unchanged AND latest image already exists in registry"
        echo "Latest image exists at: ${registry}:latest"
      }
    }
  }

  post {
    always {
      echo "=== Pipeline completed ==="
      echo "Build number: ${BUILD_NUMBER}"
      echo "Result: ${currentBuild.currentResult}"
    }

    success {
      echo "✅ Pipeline executed successfully"
    }
    failure {
      echo "❌ Pipeline failed"
    }
  }
}