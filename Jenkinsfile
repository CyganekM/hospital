pipeline {
  environment {
    REGISTRY_DEPS = "jfrog.it-academy.by/public/hospital/deps"
    REGISTRY_APP = "jfrog.it-academy.by/public/hospital/app"
    registryCredential = 'jfrog_sa'
    DOCKERFILE_DEPS = 'Dockerfile_deps'
    DOCKERFILE_APP = 'Dockerfile_from_deps'
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

    stage('Building image dependency') {
      when {
        expression {
          env.POM_CHANGED == 'CHANGED' || env.LATEST_EXISTS == "NO_EXIST"
        }
      }
      steps {
        script {
          echo "Building Docker image dependency..."
          echo "Reason: ${env.POM_CHANGED == 'CHANGED' ? 'pom.xml changed' : 'latest image not found in registry'}"

          dockerImage = docker.build(
            "${REGISTRY_DEPS}:${BUILD_NUMBER}",
            "-f ${DOCKERFILE_DEPS} ."
          )
          sh "docker tag ${REGISTRY_DEPS}:${BUILD_NUMBER} ${REGISTRY_DEPS}:latest"
        }
      }
    }

    stage('Push Image dependency') {
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
          echo "✅ Image dependency pushed successfully:"
          echo "   ${REGISTRY_DEPS}:${BUILD_NUMBER}"
          echo "   ${REGISTRY_DEPS}:latest"
        }
      }
    }

    stage('Building image application') {
      steps {
        script {
          echo "Building Docker image application..."
          dockerImage = docker.build(
            "${REGISTRY_APP}:${BUILD_NUMBER}",
            "-f ${DOCKERFILE_APP} ."
          )
          sh "docker tag ${REGISTRY_APP}:${BUILD_NUMBER} ${REGISTRY_APP}:latest"
        }
      }
    }

    stage('Push Image application') {
      steps {
        script {
          docker.withRegistry('https://jfrog.it-academy.by/', registryCredential) {
            dockerImage.push()
            dockerImage.push('latest')
          }

          echo "✅ Image application pushed successfully:"
          echo "   ${REGISTRY_APP}:${BUILD_NUMBER}"
          echo "   ${REGISTRY_APP}:latest"
        }
      }
    }

    stage('Trigger K8S Update Pipeline') {
      steps {
        script {
          // Запускаем отдельный пайплайн для обновления K8S манифестов
          build(
            job: 'hospital_edit_k9s',
            parameters: [
 //             string(name: 'IMAGE_TAG', value: "${REGISTRY_APP}:${BUILD_NUMBER}"),
                string(name: 'IMAGE_TAG', value: "${REGISTRY_APP}:44"),
                string(name: 'SOURCE_BUILD', value: "44"),
 //             string(name: 'SOURCE_BUILD', value: "${BUILD_NUMBER}"),
              string(name: 'TRIGGERED_BY', value: "${env.JOB_NAME}#${env.BUILD_NUMBER}")
            ],
            wait: true,  // Ждем завершения
            propagate: true  // падаем если K8S пайплайн упадет
          )
        }
      }
    }

    stage('Remove Unused docker image') {
      steps {
        sh '''
          docker rmi $(docker images --filter=reference="jfrog.it-academy.by/*" -q) 2>/dev/null || true
          docker image prune -f
        '''
      }
    }
  }

  post {
    always {
      echo "=== Build Pipeline completed ==="
      echo "Build number: ${BUILD_NUMBER}"
      echo "Image: ${REGISTRY_APP}:${BUILD_NUMBER}"
      echo "Result: ${currentBuild.currentResult}"
    }
  }
}