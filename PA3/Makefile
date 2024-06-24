# Define the Java compiler
JAVAC = javac

# Define the Java runtime
JAVA = java

# Source files
SRC = MainAir.java

# Default target
all: build run

# Compile the Java source file
build:
	$(JAVAC) $(SRC)

# Run the Java program with different input files
run:
	$(JAVA) MainAir public/Hawaiian-airports.txt public/Hawaiian-flights.txt public/Hawaiian-customers10.txt > my/tickets01.out
	$(JAVA) MainAir public/Hawaiian-airports.txt public/Hawaiian-flights.txt public/Hawaiian-customers100.txt > my/tickets02.out
	$(JAVA) MainAir public/Hawaiian-airports.txt public/Hawaiian-flights.txt public/Hawaiian-customers-bogus.txt > my/tickets03.out
	$(JAVA) MainAir public/Hawaiian-airports.txt public/Hawaiian-flights-singles.txt public/Hawaiian-customers100.txt > my/tickets04.out
	$(JAVA) MainAir public/Alaska-airports.txt public/Alaska-flights.txt public/Alaska-customers200.txt > my/tickets05.out
	$(JAVA) MainAir public/Alaska-airports.txt public/Alaska-flights.txt public/Alaska-customers2000.txt > my/tickets06.out
	$(JAVA) MainAir public/Alaska-airports.txt public/Alaska-flights.txt public/Alaska-customers20000.txt > my/tickets07.out

# Clean up generated files
clean:
	rm -f *.class
	rm -f my/*.out

