FROM jenkins/jenkins:lts

USER root

# Install git, Maven, and other useful tools if needed
RUN apt-get update && apt-get install -y git maven

USER jenkins