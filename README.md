# DockTailor Template

This project serves as a template to quickly start a structured JavaFX application with Maven.  
It includes basic configuration, recommended project structure, and essential dependencies for running a modern JavaFX interface.

## Prerequisites

To build and run the project, it is **required** to use a JDK that includes JavaFX.  
Recommended:

- **Azul Zulu 21 FX**  
  (JDK 21 with JavaFX included)

Download: [Azul Zulu Downloads](https://www.azul.com/downloads/)

> ⚠️ Any other JDK **without integrated JavaFX** may cause runtime errors.

## Building and Running

```bash
# Compile the project
mvn clean install

# Run the application
mvn javafx:run
