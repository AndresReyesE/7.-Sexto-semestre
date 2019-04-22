To run:

First window:
1. rmiregistry

Second window:
1. cd out
2. java -Djava.rmi.server.codebase= SubastaModelo

Third window:
1. cd out
2. java Principal [host address] //If host address is empty, it will connect to localhost.