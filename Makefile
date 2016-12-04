all:
	javac -d build src/*.java

tables: all
	cd build && java CreateTables

database: all
	cd build && java CreateDatabase

insert: all
	cd build && java InsertTables

select: all
	cd build && java QuerySelect
clean:
	rm -r build*/*
