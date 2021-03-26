Title: 
	-METCS622 Homework 4 - DBLP Search

Description: 
	-This application allows a user to search a subset of the DBLP file for certain keywords using either a Lucene index
or a standard brute force search. The results of the search are printed to the console and show an article title and year
of publication. Also, the program prints search performance data to an Excel file called "SearchMetrics.xlsx" so the user 
can see how Lucene's response time is far superior to brute force searching when the requested number of returned records
increases.

	-The dblp.xml file was split using xml_split which is a command line tool and part of the xml-twig-tools shell script
package. Xml_split allows the user to split a large xml file at: the node level, size level, condition level, or many other options.
I split the dblp.xml file into 5mb chunks and chose one of those files to run my program. You find more information for xml_split
at: https://metacpan.org/pod/distribution/XML-Twig/tools/xml_split/xml_split

	-Be careful using large XML files when running with application as DOM parsers (used in Parser.java) are very memory intensive.
Consider breaking your file into smaller chunks if you run into memory problems or implement a stream based parser.

	-You can use the smallXML.xml file to test application execution


Author:
	-Michael Battaglia (03/2021)
