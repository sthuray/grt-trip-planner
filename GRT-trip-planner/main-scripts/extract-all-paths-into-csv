import csv

# shape_id: each unique route of a bus
# block_id: each unique bus' route schedule
# trip_id: each unique run of a route (based on departure times)
# route_id: each unique route number
# trip_headsign: each unique route name
# direction_id: forward or reverse direction of route (0, 1)

# holds a {shape_id: [block_id]} for each schedule
blocks_per_shape_schedule = {
                        'Weekday': {},
                        'Saturday': {},
                        'Sunday': {},
                        'Holiday1': {}
                        }
# holds {block_id: [trip_ids]} 
trips_per_block = {
                    'Weekday': {},
                    'Saturday': {},
                    'Sunday': {},
                    'Holiday1': {}
                    }
# holds {shape_id: [trip_id]}
trips_per_route = {} #
trips_per_route_schedule = {
                            'Weekday': {},
                            'Saturday': {},
                            'Sunday': {},
                            'Holiday1': {}
                        }
# holds {shape_id: [route_id]} 
route_ids = {} #
# holds {shape_id: [trip_headsign]} 
trip_headsigns = {} #
# holds {shape_id: [direction_id]} 
direction_ids = {} #
# condense trips.csv into quickly accessible information (save values into above dictionaries)
def organize_trips():
    with open('GRT_GTFS/trips.csv', newline='') as f:
        reader = csv.reader(f)
        next(reader)

        for row in reader:
            route_id = int(row[0])
            service_id = row[1]
            trip_id = int(row[2])
            trip_headsign = row[3]
            direction_id = int(row[4])
            block_id = int(row[5])
            shape_id = int(row[6])

            # determine schedule
            if 'Weekday' in service_id:
                schedule = 'Weekday'
            elif 'Saturday' in service_id:
                schedule = 'Saturday'
            elif 'Sunday' in service_id:
                    schedule = 'Sunday'
            elif 'Holiday1' in service_id:
                    schedule = 'Holiday1'
            
            # save block_id by shape_id in it's respective schedule
            if shape_id not in list(blocks_per_shape_schedule[schedule]):
                blocks_per_shape_schedule[schedule][shape_id] = []
            if block_id not in blocks_per_shape_schedule[schedule][shape_id]:
                blocks_per_shape_schedule[schedule][shape_id].append(block_id)

            # save trip_id by it's block_id
            if block_id not in list(trips_per_block[schedule]):
                trips_per_block[schedule][block_id] = []
            trips_per_block[schedule][block_id].append(trip_id)

            # save trip_id by it's shape
            if shape_id not in list(trips_per_route):
                trips_per_route[shape_id] = []
            trips_per_route[shape_id].append(trip_id)
            if shape_id not in list(trips_per_route_schedule[schedule]):
                trips_per_route_schedule[schedule][shape_id] = []
            trips_per_route_schedule[schedule][shape_id].append(trip_id)

            # save route_ids
            if shape_id not in list(route_ids):
                route_ids[shape_id] = route_id
            
            # save trip_headsigns
            if shape_id not in list(trip_headsigns):
                trip_headsigns[shape_id] = trip_headsign
            
            # save direction id
            if shape_id not in list(direction_ids):
                direction_ids[shape_id] = direction_id

# holds {shape_id: [stop_ids]} for each unique route(shape)
routes = {} #
# holds {trip_id: [start_time, end_time]}
trip_times = {}
# save all routes with their associated shape_id
# gets start time and end time of trip

def get_last_row_in_stop_times():
    with open('GRT_GTFS/stop_times.csv', newline='') as f2:
        reader2 = csv.reader(f2)
        next(reader2)
        file = f2.readlines()

        last_trip_id = int(file[-1][:7]) ### Assumes each trip_id is 7 digits long
        last_time = file[-1][8:16] ### Assumes length of time and trip_id

        array = [last_trip_id, last_time]

        return array

def get_routes_and_times():
    with open('GRT_GTFS/stop_times.csv', newline='') as f:
        reader = csv.reader(f)
        next(reader)

        last_row = get_last_row_in_stop_times()

        last_trip_id = last_row[0]
        last_time = last_row[1]
        route = []
        trip_id = 0
        maybe_end_time = 0
        first_trip_id = True

        for row in reader:

            # if still on first trip_id, do this until past it
            if first_trip_id == True:

                if trip_id == 0:
                    start_time = row[1]
                    trip_id = int(row[0])
                
                if trip_id != int(row[0]):
                    first_trip_id = False
                else:
                    # appends this row's stop_id
                    stop_id = int(row[3])
                    route.append(stop_id)
                    
                    # gets a possible end_time
                    maybe_end_time = row[1]
                    continue

            # if moved onto next trip_id in stop_times.csv (or trip_id is uninitialized)
            if (trip_id != int(row[0])):
                
                # confirms end_time 
                end_time = maybe_end_time

                # adds current trip_id to trip_times
                trip_times[trip_id] = [start_time, end_time]

                # determine shape_id (unique route)
                for i in range(len(trips_per_route)):
                    if trip_id in trips_per_route[list(trips_per_route)[i]]:
                        shape_id = list(trips_per_route)[i]
                        break

                # if new route, add it 
                if shape_id not in list(routes):
                    routes[shape_id] = route

                # clear route
                route = []

                # initializes trip_id for next  
                trip_id = int(row[0])

                # gets start time of new trip_id
                start_time = row[1]

            # appends this row's stop_id
            stop_id = int(row[3])
            route.append(stop_id)

            maybe_end_time = row[1]

            # if last line, do most of other commands
            if (trip_id == last_trip_id) and (maybe_end_time == last_time):
                end_time = maybe_end_time
                trip_times[trip_id] = [start_time, end_time]
                # determine shape_id (unique route)
                for i in range(len(trips_per_route)):
                    if trip_id in trips_per_route[list(trips_per_route)[i]]:
                        shape_id = list(trips_per_route)[i]
                        break
                if shape_id not in list(routes):
                    routes[shape_id] = route

adj_stops_per_route = {}
def get_adj_stops():
    shape_ids = list(routes)

    # get all adjacent stops 
    for i in range(len(routes)):
        shape_id = shape_ids[i]
        route = routes[shape_id]

        adj_stops = {}
        # create dictionary
        for j in range(len(route)):
            adj_stops[route[j]] = []

        # look for stops in route
        with open('adjacent_stops.csv', newline='') as file:
            reader = csv.reader(file)
            next(reader)

            for row in reader:
                stop_id = int(row[0])
                    
                if stop_id in route:
                    
                    # get adj stop ids
                    adj_stop_ids = row[1].strip('][').split(', ')
                    for k in range(len(adj_stop_ids)):
                        adj_stop_ids[k] = int(adj_stop_ids[k])
                    
                    # add to dictionary
                    adj_stops[stop_id].extend(adj_stop_ids)
        
        adj_stops_per_route[shape_id] = adj_stops

# connections in terms of row index    
conn_arrays = {} #
def get_connections():
    get_adj_stops()
    shape_ids = list(routes)

    # get connected routes
    # iterate through each unique route
    for i in range(len(routes)):
        conn_array = []

        shape_id = shape_ids[i]
        route = routes[shape_id]

        # iterate through each stop_id in this route
        for j in range(len(route)):
            stop_id = route[j]
            
            # iterate through each unique route
            for k in range(len(routes)):
                new_shape_id = shape_ids[k]

                # if route is not the same
                if shape_id != new_shape_id:

                    # if stop_id is in this new route
                    if stop_id in routes[new_shape_id]:
                        conn_array.append(k)
            
            # now check adj_stop_ids from this stop_id
            for m in range(len(adj_stops_per_route[shape_id][stop_id])):
                adj_stop_id = adj_stops_per_route[shape_id][stop_id][m]

                for k in range(len(routes)):
                    new_shape_id = shape_ids[k]

                    # if route is not the same
                    if shape_id != new_shape_id:

                        # if stop_id is in this new route
                        if adj_stop_id in routes[new_shape_id]:
                            conn_array.append(k)
        
        # remove duplicates and sort conn_array
        conn_array = list(set(conn_array))
        conn_array.sort()
            
        # adds item to dictionary, associates conn_array with respective route
        conn_arrays[shape_id] = conn_array

# make sure to note this in routes_masterlist.csv
block_routes_trips = {}
block_routes = {}
def get_block_routes(schedule):

    # make sure to only compare different shapes and different headsigns
    block_ids = list(trips_per_block[schedule])
    # iterate through each block_id
    for i in range(len(block_ids)):
        block_id = block_ids[i]
        
        # iterate through each trip in block_id
        for j in range(len(trips_per_block[schedule][block_id])):
            trip_1 = trips_per_block[schedule][block_id][j]

            # finds a second trip
            for k in range(len(trips_per_block[schedule][block_id])):
                # if different trip
                if k != j:
                    # initialize trip_2
                    trip_2 = trips_per_block[schedule][block_id][k]

                    # if end time of trip_1 == start time of trip_2:
                    if trip_times[trip_1][1] == trip_times[trip_2][0]:

                        # get shape_ids
                        for m in range(len(trips_per_route)):
                            if trip_1 in trips_per_route[list(trips_per_route)[m]]:
                                shape_1 = list(trips_per_route)[m]
                            if trip_2 in trips_per_route[list(trips_per_route)[m]]:
                                shape_2 = list(trips_per_route)[m]
                            
                        # if different shape and name
                        if (shape_1 != shape_2) and (trip_headsigns[shape_1] != trip_headsigns[shape_2]):

                            # if last stop of trip_1 and first stop of trip_2 are the same
                            if routes[shape_1][-1] == routes[shape_2][0]:
                                if block_id not in list(block_routes_trips):
                                    block_routes_trips[block_id] = []
                                block_routes_trips[block_id].append([trip_1, trip_2])
                                
                                # get all stop_ids in connected for connected trips
                                stops1 = routes[shape_1]
                                stops2 = routes[shape_2]
                                del stops1[-1]
                                stops1.extend(stops2)
                                block_route = stops1
                                if block_id not in block_routes:
                                    block_routes[block_id] = []
                                block_routes[block_id].append(block_route)

def write_block_routes(schedule):
    get_block_routes(schedule)
    datasheet = 'block_routes.csv'

    if schedule == 'Weekday':
        datasheet = 'weekday_' + datasheet
    elif schedule == 'Saturday':
        datasheet = 'saturday_' + datasheet
    elif schedule == 'Sunday':
        datasheet = 'sunday_' + datasheet
    elif schedule == 'Holiday1':
        datasheet = 'holiday1_' + datasheet

    datasheet = 'block_routes/' + datasheet

    with open(datasheet, 'w', newline='') as file:
        writer = csv.writer(file)

        field = ['block_id', 'trip_ids', 'routes']
        writer.writerow(field)

        for i in range(len(block_routes)):
            block_id = list(block_routes)[i]

            writer.writerow([block_id, block_routes_trips[block_id], block_routes[block_id]])

def write_trip_times():
    with open('trip_times.csv', 'w', newline='') as file:
        writer = csv.writer(file)

        field = ['trip_id', 'start_time', 'end_time']
        writer.writerow(field)

        for i in range(len(trip_times)):
            trip_id = list(trip_times)[i]
            writer.writerow([trip_id, trip_times[trip_id][0], trip_times[trip_id][1]])

def write_block_trips(schedule):
    datasheet = 'block_trips.csv'
    if schedule == 'Weekday':
        datasheet = 'weekday_' + datasheet
    elif schedule == 'Saturday':
        datasheet = 'saturday_' + datasheet
    elif schedule == 'Sunday':
        datasheet = 'sunday_' + datasheet
    elif schedule == 'Holiday1':
        datasheet = 'holiday1_' + datasheet
    datasheet = 'block_trips/' + datasheet

    with open(datasheet, 'w', newline='') as file:
        writer = csv.writer(file)

        field = ['block_id', 'trip_ids']
        writer.writerow(field)

        for i in range(len(trips_per_block[schedule])):
            block_id = list(trips_per_block[schedule])[i]
            writer.writerow([block_id, trips_per_block[schedule][block_id]])

def write_routes_masterlist():
    with open('routes_masterlist.csv', 'w', newline='') as file:
        writer = csv.writer(file)

        field = ['index', 'shape_id', 'route_id', 'direction_id', 'trip_headsigns', 'routes', 'connected routes', 'adjacent stops']
        writer.writerow(field)
        
        for i in range(len(routes)):
            shape_id = list(routes)[i]
            writer.writerow([i, shape_id, route_ids[shape_id], direction_ids[shape_id], trip_headsigns[shape_id], routes[shape_id], conn_arrays[shape_id], adj_stops_per_route[shape_id]])

# blocks_per_shape_schedule -> all block_ids for a route in a schedule
# for a schedule, createa csv containing each possible route and it's associated block_ids and trip_ids
def write_schedule(csv_name, schedule):
    with open(csv_name, 'w', newline='') as file:
        writer = csv.writer(file)

        field = ['shape_id', 'block_ids', 'trip_ids']
        writer.writerow(field)

        for i in range(len(blocks_per_shape_schedule[schedule])):
            shape_id = list(blocks_per_shape_schedule[schedule])[i]
            block_ids = blocks_per_shape_schedule[schedule][shape_id]
            trip_ids = trips_per_route_schedule[schedule][shape_id]

            writer.writerow([shape_id, block_ids, trip_ids])

def write_all_schedules():
    write_schedule('schedules/weekday.csv', 'Weekday')
    write_schedule('schedules/saturday.csv', 'Saturday')
    write_schedule('schedules/sunday.csv', 'Sunday')
    write_schedule('schedules/holiday1.csv', 'Holiday1')

def main():

    organize_trips()
    get_routes_and_times()
    get_connections()

    write_routes_masterlist()
    write_all_schedules()

main()