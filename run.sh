#!/bin/bash

echo "Compiling Java source files..."
javac src/main/java/org/example/tenet/*.java

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed."
    exit 1
fi

echo "✅ Compilation successful. Running the server..."
java -cp src/main/java org.example.tenet.MyWebServer -document_root "./webSource" -port 8888
