Determine the optimal series of transfers between any 2 stops in the Waterloo region using GRT's open data!

Note: For a terminal-based usage of the algorithm, run find_all_paths.py. Please edit the ORIGIN and DESTINATION constants as suited using the stop ID's of your first and last stop (stop IDs are a 4 digit number, see GRT_GTFS/stops.csv for reference). 
Otherwise, run the flask app for a minimal interface.

**Folder Structure**
- main_scripts: 
    - find_all_paths: Main script to run to determine shortest stop paths with information provided by console output. 
    - adjacent_stops: Run to determine adjacent stops based on geospatial data in stops.csv. Updates adjacent_stops.csv
    - process_paths_into_csv: Run to extract and reorganize data in GRT_GTFS (adjacent_stops.csv/routes_masterlist.csv/trip_times.csv) for efficient use in find_all_paths
    - find_closest_stops: in-progress feature, meant to calculate the ORIGIN and DESTINATION stops without user-input in future updates
- GRT_GTFS: open data provided by GRT
- schedules: schedule data extracted from GRT_GTFS and pre-processed for efficient retrieval by python scripts
- adjacent_stops.csv/routes_masterlist.csv/trip_times.csv: additional pre-processed data