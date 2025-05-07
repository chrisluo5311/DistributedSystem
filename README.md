# DistributedSystem

<details>
<summary> 1. JSLP Overview </summary>

The **DistributedSystem** project is a Java-based application that demonstrates the use of the Service Location Protocol (SLP) for service discovery and advertisement. It consists of two main components:
- **PrinterSA**: A Service Agent that advertises printer services with specific attributes.
- **PrinterUA**: A User Agent that discovers and retrieves information about available printer services.

The project uses the `jslp` library to implement SLP functionality.

## Features
- Advertises printer services with attributes such as type, power status, and location.
- Discovers available printer services of a specific type.
- Retrieves and displays service attributes.

## Requirements
- **Java Version**: 8 or higher
- **Build Tool**: Apache Maven

## Dependencies
| Dependency                  | Version       | Scope  |
|-----------------------------|---------------|--------|
| `net.sourceforge.jslp:jslp` | `1.0.0.RC5`   | System |

## How to Run
1. Replace `${project.basedir}` with the actual path to `jslp-1.0.0.RC5.jar` in the `pom.xml` file.
2. Run the PrinterSA class to advertise services
3. Run the PrinterUA class to discover services
</details>

<details>
<summary> 2. PA1 Overview </summary>

1. Name: JIDUNG, LO
2. Assignment name: PA1-MyWebServer
3. Description of my assignment:

    | Aspect            | Description                                                                                                                                                                                                                                                                                                                                                                                  |
    |-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | `MyWebServer`     | - Initializes the server by parsing command-line arguments (document root and port). <br/>- Listens for incoming connections using `ServerSocket`. <br/>- Spawns a new thread (`ClientHandler`) for each client connection to handle requests concurrently. <br/>- Tracks active connections using `AtomicInteger`                                                                           |
    | `ClientHandler`   | - Implements `Runnable` to handle client requests in a separate thread. <br/>- Parse and validates HTTP requests (e.g., method, file path) <br/>- Sends requested file if they exist and accessible <br/> - Sends error pages for invalid or unauthorized requests (e.g., 404, 403, 400, 500) <br/> - Handles persistent connections (HTTP keep-alive) based on request headers and protocol |
    | `MgrResponseDTO`  | - Generates HTTP responses with correct status code, heaeders, and body. <br/>- Handles different connection types (close/persistent) <br/> - Follow HTTP standards for responses                                                                                                                                                                                                            |
    | `MgrResponseCode` | - Enum class that defines HTTP response codes and their corresponding messages.(e.g., 200 OK, 404 Not Found) <br/> - Ensure consistency in response code                                                                                                                                                                                                                                     |
    | `Log`             | - Logs events such as server start, client connections, request processing, and errors. <br/>- Writes logs to a file (`tenet.log`) with timestamps for tracking and debugging <br/> - Provides methods for logging different levels of messages (info, error) <br/>- Supports multithread-safe logging using synchronized methods                                                            |
    | Error Handling    | - Provides error pages (e.g., `400.html`,`403.html`,`404.html`,`500.html`) for invalid requests, file not found, or unauthorized access <br/>- Sends appropriate HTTP error responses to clients. <br/>- Logs errors for debugging and monitoring purposes                                                                                                                                   |
    | Multithreading    | - Handles concurrent client requests by running each in a separate thread (). `ClientHandler`<br/> - Ensures efficient connections even when multiple clients access the server simultaneously.                                                                                                                                                                                              |

4. A list of submitted files 

    | File Path                          | File Name              | Description                                             |
    |------------------------------------|------------------------|---------------------------------------------------------|
    | `src/main/java/org.example/tenet/` | `MyWebServer.java`     | Main class that initializes and starts the web server.  |
    | `src/main/java/org.example/tenet/` | `ClientHandler.java`   | Handles client requests and responses.                  |
    | `src/main/java/org.example/tenet/` | `MgrResponseDTO.java`  | Generates HTTP responses with status codes and headers. |
    | `src/main/java/org.example/tenet/` | `MgrResponseCode.java` | Defines HTTP response codes and messages.               |
    | `src/main/java/org.example/tenet/` | `Log.java`             | Handles logging of server events.                       |
    | `./`                               | `tenet.log`            | Log file for server events.                             |
    | `webSource/`                       | `400.html`             | **Error page** for bad requests.                        |
    | `webSource/`                       | `403.html`             | **Error page** for forbidden access.                    |
    | `webSource/`                       | `404.html`             | **Error page** for not found resources.                 |
    | `webSource/`                       | `500.html`             | **Error page** for internal server errors.              |
    | `webSource/`                       | `index.html`           | Home Page** for https://www.scu.edu/                    | 
    | `webSource/`                       | `secret.html`          | Secret page for testing access control.                 |
    | `webSource/`                       | `assets`               | Directory containing static files.                      |
    | `webSource/`                       | `js`                   | Directory containing JavaScript files.                  |
    | `webSource/`                       | `livewhale`            | Directory containing JavaScript files.                  |
    | `webSource/`                       | `media`                | Directory containing various images                     |
    | `webSource/`                       | `public`               | Directory containing images and css                     |

5. Instructions for running `tenet`
    ### Dependencies
    | Dependency       | Version    | Description                       |
    |------------------|------------|-----------------------------------|
    | OpenJDK          | 17.0.8     | Java Development Kit for compiling and running the program. |
    | Operating System | Any        | Works on Linux, macOS, or Windows. |
    | CLI/Terminal     | Any        | Command-line interface to execute commands. |

    ### Steps to run
    1. Open terminal and navigate to the root directory of the `DistributedSystem` project.
        ```bash
        cd /path/to/DistributedSystem
        ```
    2. Compile the Java files using the following command:
        ```bash
        javac src/main/java/org/example/tenet/*.java
        ```
    3. If the compilation is successful, start the web server by running the following command
        ```bash
        java -cp src/main/java org.example.tenet.MyWebServer -document_root "./webSource" -port 8888
        ```
       where
       -  `_document_root ./webSource` specifies the directory containing the web files
       - `_port 8888` specifies the port number on which the server will listen for incoming connections.
    4. Access the Web Server
         - Open a web browser and navigate to `http://localhost:8888/` to access the home page.
           This URL corresponds to the `index.html` file in the `webSource/` directory.
         - To test `400.html`, use http://localhost:8888 POST
         - To test readable `403.html`, first run `chmod 000 DistributedSystem/webSource/secret.html` to remove read, write, and execute permissions for everyone, then use http://localhost:8888/secret.html
         - To test `404.html`, use http://localhost:8888/pokemon.html
         - To test `500.html`, use http://localhost:8888/../secret.txt
    5. Additional information
       - Logs for server activities will be stored in the `tenet.log` file located in the project root.

6. Snapshots of my web browser
    
    
</details>