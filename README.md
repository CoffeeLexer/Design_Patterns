# Example project for TCP and UDP communication with server and multiple clients using Java

# How to run the game:
1. Start the server - run "main" method in /src/main/java/game/server/Server.java
2. Start a client - run "main" method in /src/main/java/game/client/Client.java
* You can run multiple clients

# File descriptions:

# src/main/java/example
* _TCP.java - Example of using TCP sockets
* _UDP.java - Example of using UDP sockets

# src/main/java/game/client
* Client.java - Main client class that is used to open a client connection to the server, start the client engine and open a window where the game is rendered
* Controller.java - Keyboard and Mouse events controller from client side
* DynamicPanel.java - Panel that renders "Dynamic" objects every frame
* Engine.java - Main client engine that renders, updates and removes objects on client side 
* ResponseToServer.java - Describes what the client should do when it communicates with the server
* StaticPanel.java - Panel that renders "Static" objects when they are requested

# src/main/java/game/data
* GameObject_Example.java - Example implementation of customized GameObject "update" method
* GameObject.java - Class that stores main information about a game object and renders it to client 
* MouseEventData.java - Data that gets taken from a Mouse event
* Payload.java - Describes the payload that gets send to the server and its Method types
* Textures.java - Class used for reading a texture from an image file path
* Vector.java - Class used to conveniently store data of X and Y position

# src/main/java/game/protocol
* TCP.java - Describes how TCP protocol works with Client and ConnectionListener classes
* UDP.java - Describes how UDP protocol works with Sender, Receiver and Identifier classes

# src/main/java/game/server
* ClientAttachment.java - Data that is attached to a message
* Engine.java - Main server engine that creates, updates, removes and synchronizes objects on server side
* ResponseToClient.java - Describes what the server should do when it communicates with the client
* ResponseToInput.java - Describes what the server should do when it receives Mouse or Keyboard events from a client
* Server.java - Main server class that is used to start the server
