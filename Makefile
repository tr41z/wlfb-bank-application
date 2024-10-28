delete_class:
	@rm -rf */*.class

compile_class:
	@javac */*.java

run_server:
	@java server.ActionServer