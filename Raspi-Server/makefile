CC = g++
TARGET = server
OBJECTS = Objects/main.o Objects/connectionservice.o Objects/serverstarter.o Objects/eventservice.o Objects/jsoncpp.o Objects/jsonservice.o Objects/equipment.o
OPTIONS = -O -Wall -W -pedantic-errors -std=c++14 -lpthread
FLAGS = -c -o


$(TARGET) : $(OBJECTS)
	$(CC) -o $@ $^ $(OPTIONS)


Objects/main.o : main.cpp
	$(CC) $(FLAGS) Objects/main.o main.cpp $(OPTIONS)

Objects/connectionservice.o : Sources/connectionservice.cpp
	$(CC) $(FLAGS) Objects/connectionservice.o Sources/connectionservice.cpp $(OPTIONS)

Objects/eventservice.o : Sources/eventservice.cpp
	$(CC) $(FLAGS) Objects/eventservice.o Sources/eventservice.cpp $(OPTIONS)

Objects/serverstarter.o : Sources/serverstarter.cpp
	$(CC) $(FLAGS) Objects/serverstarter.o Sources/serverstarter.cpp $(OPTIONS)

Objects/jsoncpp.o : Sources/jsoncpp.cpp
	$(CC) $(FLAGS) Objects/jsoncpp.o Sources/jsoncpp.cpp $(OPTIONS)

Objects/jsonservice.o : Sources/jsonservice.cpp
	$(CC) $(FLAGS) Objects/jsonservice.o Sources/jsonservice.cpp $(OPTIONS)

Objects/equipment.o : Sources/equipment.cpp
	$(CC) $(FLAGS) Objects/equipment.o Sources/equipment.cpp $(OPTIONS)


clr :
	rm $(OBJECTS)

clr-out :
	rm $(TARGET) $(OBJECTS)

