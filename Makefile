all:
	javac -d build src/*.java

run: all
	cd build && java Window

tables:all
	cd build && java CreateTables

clean:
	rm -r build*/*
