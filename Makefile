all:
	javac -d build srcOracle/*.java


tables: all
	cd build && java CreateTables

database: all
	cd build && java CreateDatabase

insert: all
	cd build && java InsertTables

select: all
	cd build && java QuerySelect

run: all
	cd build && java Window

clean:
	rm -r build*/*
