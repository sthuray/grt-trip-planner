from flask import Blueprint, render_template, request, flash
import subprocess

views = Blueprint('views', __name__)

@views.route('/', methods=['POST', 'GET'])
def home():
    out = ''
    if request.method == 'POST':
        ORIGIN = request.form['origin']
        DESTINATION = request.form['destination']
        if (len(ORIGIN) < 4) or (len(DESTINATION) < 4):
            flash('Stop numbers are 4 digits long', category='error')
        else:
            flash('Calculating fastest route...', category='success')
            print(ORIGIN)
            print(DESTINATION)
            print("Running subprocess")
            out = subprocess.run(["python", "/Users/sayithuray/Documents/GitHub/sayithuray/GRT-trip-planner/main_scripts/find_all_paths"], input=f"{ORIGIN}\n{DESTINATION}\n", capture_output=True, text=True)
            out = repr(out.stdout)
            out = out.split("\\n")
            print("Subprocess finished")
    
    return render_template('index.html', output=out)