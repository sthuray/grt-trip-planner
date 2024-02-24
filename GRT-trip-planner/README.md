Note: find-all-paths is the main script to run, please edit the ORIGIN and DESTINATION constants as suited using the stop ID's of your first and last stop (stop IDs are a 4 digit number, see GRT_GTFS/stops.csv for reference)

- main-scripts: 
    - find-all-paths: Main script to run to determine shortest stop paths with information provided by console output. 
    - adjacent-stops: Run to determine adjacent stops based on geospatial data in stops.csv. Updates adjacent_stops.csv
    - extract-all-paths-into-csv: Run to extract and reorganize data in GRT_GTFS (adjacent_stops.csv/routes_masterlist.csv/trip_times.csv) for efficient use in find-all-paths
    - find-closest-stops: in-progress feature, meant to calculate the ORIGIN and DESTINATION stops without user-input in future updates
- GRT_GTFS: open data provided by GRT
- schedules: schedule data extracted from GRT_GTFS and organized for reference within main-scripts
- adjacent_stops.csv/routes_masterlist.csv/trip_times.csv