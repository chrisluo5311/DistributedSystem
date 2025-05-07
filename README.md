<h1> PA1-JIDUNG-LO Overview </h1>

1. Name: JIDUNG, LO
2. Assignment name: PA1-JIDUNG-LO
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

    | File Path                          | File Name              | Description                                                                                                                             |
    |------------------------------------|------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
    | `src/main/java/org.example/tenet/` | `MyWebServer.java`     | Main class that initializes and starts the web server.                                                                                  |
    | `src/main/java/org.example/tenet/` | `ClientHandler.java`   | Handles client requests and responses.                                                                                                  |
    | `src/main/java/org.example/tenet/` | `MgrResponseDTO.java`  | Generates HTTP responses with status codes and headers.                                                                                 |
    | `src/main/java/org.example/tenet/` | `MgrResponseCode.java` | Defines HTTP response codes and messages.                                                                                               |
    | `src/main/java/org.example/tenet/` | `Log.java`             | Handles logging of server events.                                                                                                       |
    | `./`                               | `tenet.log`            | Log file for server events.                                                                                                             |
    | `webSource/`                       | `400.html`             | **Error page** for bad requests.                                                                                                        |
    | `webSource/`                       | `403.html`             | **Error page** for forbidden access.                                                                                                    |
    | `webSource/`                       | `404.html`             | **Error page** for not found resources.                                                                                                 |
    | `webSource/`                       | `500.html`             | **Error page** for internal server errors.                                                                                              |
    | `webSource/`                       | `index.html`           | Home Page** for https://www.scu.edu/                                                                                                    | 
    | `webSource/`                       | `secret.html`          | Web page for testing access control. Be sure to change permission using `chmod 000 PA1-JIDUNG-LO/webSource/secret.html` before testing. |
    | `webSource/`                       | `assets`               | Directory containing static files.                                                                                                      |
    | `webSource/`                       | `js`                   | Directory containing JavaScript files.                                                                                                  |
    | `webSource/`                       | `livewhale`            | Directory containing JavaScript files.                                                                                                  |
    | `webSource/`                       | `media`                | Directory containing various images                                                                                                     |
    | `webSource/`                       | `public`               | Directory containing images and css                                                                                                     |

    Project Directory Structure:
    ```bash
    PA1-JIDUNG-LO/
    │
    ├── src/
    │   ├── main/
    │       ├── java/
    │       │   └── org/example/tenet/
    │       │       ├── MyWebServer.java       # Main class to initialize and start the web server
    │       │       ├── ClientHandler.java     # Handles client connections in separate threads
    │       │       ├── MgrResponseDTO.java    # Generates HTTP responses with headers and statuses
    │       │       ├── MgrResponseCode.java   # Enum for HTTP response codes and their messages
    │       │       └── Log.java               # Handles logging for server activities
    │       └── resources/
    │           └── static/snapshot/           # Snapshots of the web pages
    │               ├── page1.jpg
    │               ├── page2.jpg
    │               └── ...
    ├── webSource/
    │   ├── 400.html                           # Error page for "Bad Request"
    │   ├── 403.html                           # Error page for "Forbidden"
    │   ├── 404.html                           # Error page for "Not Found"
    │   ├── 500.html                           # Error page for "Internal Server Error"
    │   ├── index.html                         # Home page
    │   ├── secret.html                        # Page for testing access control
    │   ├── assets/                            # Directory for static assets (e.g., images, logos)
    │   ├── js/                                # Directory for JavaScript files
    │   ├── livewhale/                         # Directory for livewhale-related files
    │   ├── media/                             # Directory for images
    │   └── public/                            # Public directory for additional images and CSS
    │
    ├── tenet.log                              # Log file for server activities
    ├── run.sh                                 # Shortcut shell script to compile and run the server
    ├── README.md                              # Documentation for the project
    ```

5. Instructions for running `tenet`
    #### Dependencies
    | Dependency       | Version    | Description                                                 |
    |------------------|------------|-------------------------------------------------------------|
    | OpenJDK          | 17.0.8     | Java Development Kit for compiling and running the program. |
    | Operating System | Any        | Works on Linux, macOS.                                      |
    | CLI/Terminal     | Any        | Command-line interface to execute commands.                 |

    #### Steps to run
    1. **Ensure OpenJDK 17 is installed:**
        
       Run the following command to install OpenJDK 17 if it is not already installed:
   
       ```bash
       sudo apt install openjdk-17-jdk
       ```
       
   2. **Open terminal and navigate to the root directory of the `PA1-JIDUNG-LO` project**: 
       
      ```bash
       cd /path/to/PA1-JIDUNG-LO
       ```

    3. Compile the Java files using the following command:
      
       ```bash
       javac src/main/java/org/example/tenet/*.java
       ```

    4. If the compilation is successful, start the web server by running the following command:
        
        ```bash
        java -cp src/main/java org.example.tenet.MyWebServer -document_root "./webSource" -port 8888
        ```
        
        - `_document_root "./webSource"` specifies the directory containing the web files.
       
        - `_port 8888` specifies the port number on which the server will listen for incoming connections.

        **Note:** `run.sh` combines Step 3 and Step 4 into a single script for convenience. If you want to save time, you can simply run: 
        ```bash
        ./run.sh
        ```
        Make sure you are in the document root when running this script.
   5. **Access the Web Server**:
         - Open a web browser and navigate to `http://localhost:8888/` to access the home page.
           This URL corresponds to the `index.html` file in the `webSource/` directory.

   6. **Additional information—Log and Testing**:
        - Logs for server activities will be stored in the `tenet.log` file located in the project root.
          Open another terminal and run the following command to view the logs:
          ```bash
          tail -f tenet.log
          ```
        - To test `400.html`, try http://localhost:8888 POST.
        - To test `403.html`, first run:
          ```bash
          chmod 000 PA1-JIDUNG-LO/webSource/secret.html
          ```
          Then try http://localhost:8888/secret.html.
        - To test `404.html`, try http://localhost:8888/pokemon.html.
        - To test `500.html`, try http://localhost:8888/../secret.txt.

6. Snapshots of my web browser

    ##### Snapshots of "http://localhost:8888/" Images
    | Image 1                                                | Image 2                                                |
    |--------------------------------------------------------|--------------------------------------------------------|
    | ![page1](src/main/resources/static/snapshot/page1.jpg) | ![page2](src/main/resources/static/snapshot/page2.jpg) |
    | ![page3](src/main/resources/static/snapshot/page3.jpg) | ![page4](src/main/resources/static/snapshot/page4.jpg) |
    | ![page5](src/main/resources/static/snapshot/page5.jpg) | ![page6](src/main/resources/static/snapshot/page6.jpg) |
    | ![page7](src/main/resources/static/snapshot/page7.jpg) | ![page8](src/main/resources/static/snapshot/page8.jpg) |

7. Request Log Snapshot
![request_log](src/main/resources/static/snapshot/request_log.jpg)

8. Know Issues:
   1. Does not support HTTP methods other than GET.
   2. Limited to serving static content only; no dynamic page generation.
   3. Lacks HTTPS support.
   4. Port conflicts may occur; please ensure the specified port is available.

9. Logs Information

   - Client requests:
       - Timestamp.
       - Method (`GET`).
       - File/resource content type.
   - Responses:
       - Status codes.
       - Requested files.
       - Errors encountered (if any).
   - Connection of each Thread's start/shutdown times.