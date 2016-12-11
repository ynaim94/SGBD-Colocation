all:
	javac -d build src/*.java

run: all
	cd build && java Window

clean:
	rm -r build*/*
