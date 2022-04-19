go:
	go run main.go

java:
	docker run --rm -v $(CURDIR):/usr/src/myapp -w /usr/src/myapp openjdk:11 javac -classpath .:mysql.jar Main.java
	docker run --rm -v $(CURDIR):/usr/src/myapp -w /usr/src/myapp openjdk:11 java -classpath .:mysql.jar Main

node:
	docker run -it --rm --name my-running-script -v $(CURDIR):/usr/src/app -w /usr/src/app node:8 node main.js

python:
	./main.py

