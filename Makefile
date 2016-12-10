all:
	javac -d build src/*.java


tables: all
	cd build && java CreateTables


run: all
	cd build && java Window

clean:
	rm -r build*/*
