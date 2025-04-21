# DistributedSystem

## Overview
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