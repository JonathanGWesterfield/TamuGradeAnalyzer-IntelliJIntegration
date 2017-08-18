# TamuGradeAnalyzer-IntelliJIntegration

The only reason this is named IntelliJIntegration is because I am currently trying to learn how to use github through IntelliJ 
(since it is the main IDE that I prefer to use)

# What it does

This is a project which will allow me to anaylze the grades that Texas A&M professors give. A gui 
allows the user to see all the information about the professor by selecting the subject, course number, etc, about the class the are trying to register for. Information seen by user will include: a bar graph of the 
grade distribution  of the class, a line graph of the class with GPA from past 
semesters, the number of A's, B's, C's, Number of Drops, etc for a class, which also tells the user how many years that
professor has taught that course. I plan to distribute this application freely to anyone who wants to use it. I only 
wish decent database hosting services were free.

# The Back End

It takes in the Grade PDF's from the TAMU registrar site, strips the text off of the pdf's and upload them to a database. 
This backend process and file are located in this repo however, the uploading functions do not work due to the Database
permissions given to the average user who will be using this tool. The working backend file is in the TamuGradeAnalyzer-Insert
project repository. There are plans to re-incorporate it into this repository and simply form it as a different module, but for
now, they are separate pieces of the same project.
