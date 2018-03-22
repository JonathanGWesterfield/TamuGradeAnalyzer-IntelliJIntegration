# TamuGradeAnalyzer-IntelliJIntegration

The only reason this is named IntelliJIntegration is because I am currently trying to learn how to use github through IntelliJ 
(since it is the main IDE that I prefer to use)

# Update as of 8/27/17
This project is now finished (for the most part). It is fully functioning and has been put into an executable jar. All that is needed to
run the jar is the JRE version 1.8.

# The Runnable Jar
The runnable .jar file is located in out/Jars/Tamu Grade Analyzer.jar. Feel free to download it by clicking "Download."

# Documentation

Documentation for this can be found here:
https://jonathangwesterfield.github.io/TamuGradeAnalyzer-IntelliJIntegration/


# What it does

This is a project which will allow me to anaylze the grades that Texas A&M professors give. A gui
allows the user to see all the information about the professor by selecting the subject, course number, etc, about the class the are trying to register for. Information seen by user will include: a bar graph of the
grade distribution  of the class, a line graph of the class with GPA from past
semesters, the number of A's, B's, C's, Number of Drops, etc for a class, which also tells the user how many years that
professor has taught that course. I plan to distribute this application freely to anyone who wants to use it. I only
wish decent database hosting services were free. The data for the classes is only up to date
for up to Spring 2016. The records go back to fall 2013 as I thought that anything further back than that would be outdated and
irrelevant.

# The Back End

It takes in the Grade PDF's from the TAMU registrar site, strips the text off of the pdf's and upload them to a database.
This backend process and file are located in this repo however, the uploading functions do not work due to the Database
permissions given to the average user who will be using this tool. The working backend file is in the TamuGradeAnalyzer-Insert
project repository. There are plans to re-incorporate it into this repository and simply form it as a different module, but for
now, they are separate pieces of the same project.

# Further Work to be Done (as of 8/27/17)

The app needs a different way to read in the grade data. Texas A&M reformatted their grade pdfs so that stripping the text off of them
using PDFBox no longer outputs usable information. What is needed to fix this might be an OCR but they are very complicated to work with
and I did not have time to implement this (in time for the upcoming career fair). As a result, the data for the classes is only up to date
for up to Spring 2016. The records go back to fall 2013. The code needs to be cleaned up (There is a lot of code that has been commented out
that is still in there. Also there are plenty of ways to make the app better, like allowing people
to write reviews for the professors they have, making the UI look nicer and presents information more effectively.
