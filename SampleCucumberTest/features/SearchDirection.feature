@RegressionTesting
Feature: Google Map Scenarios 
 Scenario: verify the coordinated of San Fransisco on GoogleMap
 Given Open the browser and access Google Map Site
 When Search "San Francisco, CA" from the search field
 Then verify "37.757815,-122.5076407" coordinates matches
 When Search for driving direction between "Chico, California" and "San Francisco, California" by "Car"
 Then verify more than 2 results are displaying in Reults
 And 	Save the title, distance and travel time in "routes.txt" file 
 
 

 
 