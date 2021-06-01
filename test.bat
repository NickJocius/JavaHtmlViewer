echo off
cls 
del *.class

javac -cp .; Proj03.java 
java -cp .; Proj03 

pause