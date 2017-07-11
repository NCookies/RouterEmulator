CXX = g++
TARGET = RaspiService
OBJECTS = Objects/main.o Objects/connectionservice.o Objects/serverstarter.o Objects/eventservice.o Objects/jsoncpp.o Objects/jsonservice.o Objects/routeraction.o
OPTIONS = -std=c++14 -lpthread
CPPFLAGS = -c -o


$(TARGET) : $(OBJECTS)
	$(CXX) -o $@ $^ $(OPTIONS)


Objects/main.o : main.cpp
	$(CXX) $(CPPFLAGS) $@ $^ $(OPTIONS)

Objects/connectionservice.o : Sources/connectionservice.cpp
	$(CXX) $(CPPFLAGS) $@ $^ $(OPTIONS)

Objects/eventservice.o : Sources/eventservice.cpp
	$(CXX) $(CPPFLAGS) $@ $^ $(OPTIONS)

Objects/serverstarter.o : Sources/serverstarter.cpp
	$(CXX) $(CPPFLAGS) $@ $^ $(OPTIONS)

Objects/jsoncpp.o : Sources/jsoncpp.cpp
	$(CXX) $(CPPFLAGS) $@ $^ $(OPTIONS)

Objects/jsonservice.o : Sources/jsonservice.cpp
	$(CXX) $(CPPFLAGS) $@ $^ $(OPTIONS)

Objects/routeraction.o : Sources/routeraction.cpp
	$(CXX) $(CPPFLAGS) $@ $^ $(OPTIONS)
	

clr :
	rm $(OBJECTS)

clr-out :
	rm $(TARGET) $(OBJECTS)
