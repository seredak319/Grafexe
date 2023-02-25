# Grafexe

Grafexe is a program created as part of a school project that was related to algorithms operating on graphs.

### What can the program do?

The program can:
* Generate a graph with a given number of columns and rows of nodes and edge weights randomly selected in a given range of values and with a percentage density of connections.
* Save such a graph to a file with a fixed format.
* Read a graph from a file with a fixed format.
* Check graph consistency (breadth search algorithm - BFS).
* Find the shortest paths between selected pairs of nodes in this graph using Dijkstra's Algorithm.

In addition, the program is equipped with a graphical interface (shown below), which provides:
* Drawing a graph as a mesh (algorithms adapted to all kinds of graphs).
* Selection of nodes for pathing with the mouse.
* Show the shortest path.

<img width="976" alt="grafexe" src="https://user-images.githubusercontent.com/95620581/186385068-26641a01-cdc0-40f7-873d-25358877ea04.png">

### How did the program come about?
Grafexe was created as a project. First, functional and implementation specifications were created.
Then, using version control, new functionalities were added. The entire process of creating the program was controlled by the teacher.

### How to use the program?
When generating a graph, you must provide the necessary information such as file name, graph size and weight range of generated connections. Then, by clicking on two vertices, the shortest path between them will be determined. What's more, we can load a graph located in the "data" directory, which should contain a strictly defined layout, which we can preview after the first generation of our graph.

### What has the program taught me?
Doing this project has undoubtedly taught me many new things. The first was the new Java programming language. The second important thing was to carefully plan the program, write the specification and then verify it in reality. In addition, while writing the algorithms used by the program, I had the opportunity to compare them with previously written algorithms in the C language. Due to two different implementations, I could see how the memory usage or the operation time changed with the same result. (BFS, Dijkstra and graph storage). A definite novelty for me was the graphical interface, which turned out to be a great help and incredibly satisfying fun. What's more, he taught me to "think for the user" to anticipate various scenarios and secure them properly:

<img width="976" alt="grafexebad" src="https://user-images.githubusercontent.com/95620581/186386821-6871d5fc-917b-4386-9ccc-92469eb58a87.png">

### Is the project finished?
The program works as intended, but there are still a few corrections and modifications to be made.
I'd refactor the 'StartScreenController' file first, as it's over 750 lines long and there's a lot of potential in breaking it down into smaller chunks of code. In addition, I would add multithreading and a button to interrupt an operation that is taking too long.

### Technology versions
* JavaFx - 17.0.1
* JUnit - 5.8.1
* Maven - 3.8.1
* Java - JDK 17

