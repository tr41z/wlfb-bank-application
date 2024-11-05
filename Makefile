clean:
	@rm -rf */*.class

all:
	@javac */*.java

run_server:
	@java server.ActionServer