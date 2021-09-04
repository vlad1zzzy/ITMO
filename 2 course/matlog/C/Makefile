CC          = g++
CFLAGS      = -c -Wall -std=c++14
LDFLAGS     =
SOURCES     = main.cpp Parser.cpp Expression.cpp
OBJECTS     = $(SOURCES:.cpp=.o)
EXECUTABLE  = Parser

all: $(SOURCES) $(EXECUTABLE)

run: $(SOURCES) $(EXECUTABLE)
	./$(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	$(CC) $(LDFLAGS) $(OBJECTS) -o $@

.cpp.o:
	$(CC) $(CFLAGS) $< -o $@

clear:
	rm *.o