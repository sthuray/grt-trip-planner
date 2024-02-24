# find adjacent stops and save these relations in a csv
# mainly looking at stops.csv
# also checking which routes are on stops (if at least 1 different route at a stop)
from math import cos, asin, sqrt, pi
import csv

def find_routes_w_stop(stop_id):

    # new route index
    new_routes = []

    with open('routes_masterlist.csv', newline='') as f:
        reader = csv.reader(f)
        next(reader)

        # iterates through each row for a route that includes the stop_id
        for row in reader:
            index = int(row[0])
            route = row[5].strip('][').split(', ')

            # if found a route containing stop_id
            if f"{stop_id}" in route:

                # add index to new routes
                new_routes.append(index)

                # convert into int arrays
                for i in range(len(route)):
                    route[i] = int(route[i])
    
    return new_routes


def distance(lat1, lon1, lat2, lon2):
    r = 6371 # km
    p = pi / 180

    a = 0.5 - cos((lat2-lat1)*p)/2 + cos(lat1*p) * cos(lat2*p) * (1-cos((lon2-lon1)*p))/2
    return 2 * r * asin(sqrt(a))


# {stop_id: [lat, lon]}
stop_points = {}

with open('GRT_GTFS/stops.csv', newline='') as file:
    reader = csv.reader(file)
    next(reader)

    for row in reader:
        try:
            stop_id = int(row[0])
        except:
            continue
            
        stop_lat = float(row[4])
        stop_lon = float(row[5])
        stop_points[stop_id] = (stop_lat, stop_lon)




adj_stops = {}
checked_stops = []
for i in range(len(stop_points)):
    stop1_id = list(stop_points)[i]
    stop1 = stop_points[stop1_id]
    for j in range(len(stop_points)):
        stop2_id = list(stop_points)[j]
        if (stop1_id != stop2_id) and (stop2_id not in checked_stops):
            stop2 = stop_points[stop2_id]

            dist = distance(stop1[0], stop1[1], stop2[0], stop2[1])
            
            if dist < 0.2:
                diff_route_counter = 0
                
                # given stop_id, get routes
                route_arr1 = find_routes_w_stop(stop1_id)
                route_arr2 = find_routes_w_stop(stop2_id)

                adj_bool = False
                if len(route_arr1) != len(route_arr2):
                    adj_bool = True
                else:
                    for k in range(len(route_arr1)):
                        if route_arr1[k] not in route_arr2:
                            diff_route_counter = diff_route_counter + 1
                    if diff_route_counter > 0:
                        adj_bool = True
                if adj_bool:
                    if stop1_id not in adj_stops:
                        adj_stops[stop1_id] = []
                    if stop2_id not in adj_stops:
                        adj_stops[stop2_id] = []
                    adj_stops[stop1_id].append(stop2_id)
                    adj_stops[stop2_id].append(stop1_id)

    checked_stops.append(stop1_id)

with open('adjacent_stops.csv', 'w', newline='') as f:
    writer = csv.writer(f)

    field = ['stop_id', 'adj_stop_ids']
    writer.writerow(field)

    for i in range(len(adj_stops)):
        stop_id = list(adj_stops)[i]
        writer.writerow([stop_id, adj_stops[stop_id]])

# 43.407212, -80.328047