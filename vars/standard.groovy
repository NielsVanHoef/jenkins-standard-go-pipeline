def call(String goToolName = 'go-1.14', String golangCiVersion = 'v1.17.1') {
    pipeline {
        agent any
        environment {
            GO111MODULES = 'on' 
        }
        tools {
            go '$goToolName'
        }
        stages {
            stage ('Build') {
                steps {
                    sh 'go build'
                }
            }
            stage ('Test') {
                steps {
                    sh 'go test ./... -coverprofile=coverage.txt'
                }
            }
            stage('Code Analysis') {
                steps {
                    sh 'curl -sfL https://install.goreleaser.com/github.com/golangci/golangci-lint.sh | bash -s -- -b $GOPATH/bin v1.17.1'
                    sh 'golangci-lint run'
                }
            } 
        }
    }
}
