#TODO: add this functionality for the user

stoplat = 43.449205
stoplng = -80.30771033

mylat = 43.44953790326778
mylon = -80.3080066973076

# https://cs.nyu.edu/visual/home/proj/tiger/gisfaq.html

from math import cos, asin, sqrt, pi

def distance(lat1, lon1, lat2, lon2):
    r = 6371 # km
    p = pi / 180

    a = 0.5 - cos((lat2-lat1)*p)/2 + cos(lat1*p) * cos(lat2*p) * (1-cos((lon2-lon1)*p))/2
    return 2 * r * asin(sqrt(a))

#print(distance(mylat, mylng, stoplat, stoplng))

rows = []
stop_options = {}
import csv
with open('GRT_GTFS/stops.csv', newline='') as f:
    reader = csv.reader(f)
    header = next(reader)
    for row in reader:
        stop_lat = float(row[4])
        stop_lon = float(row[5])
        dist = distance(mylat, mylon, stop_lat, stop_lon)
        if (dist < 1):
            stop_options[row[0]] = dist
print(stop_options)

closest_dist = 2
dict_array = list(stop_options)
for i in range(len(stop_options)):
    if (stop_options[dict_array[i]] < closest_dist):
        closest_dist = stop_options[dict_array[i]]
        closest_stop = dict_array[i]
print(closest_stop)

print(distance(43.407212, -80.328047, 43.407758, -80.327778))
