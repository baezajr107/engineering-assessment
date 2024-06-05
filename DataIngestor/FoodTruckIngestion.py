from sqlalchemy import create_engine
import pandas as pd
import base64
import json
import requests
from datetime import datetime
from datetime import timedelta

# Get base data from the REST API
api_url = "https://data.sfgov.org/resource/rqzj-sfat.json"

try:

    api_response = requests.get(api_url)
    response_data = api_response.json()

except:
    print(api_response)

print(response_data)

output_data=[]
date_format='%Y-%m-%dT%H:%M:%S.%f'
# Transform the data into the proper types and column names
for data in response_data:
    output_data.append(
    {
        'location_id' : data['objectid'],
        'applicant' : data['applicant'],
        'facility_type' : data['facilitytype'] if 'facilitytype' in data else None,
        'cnn' : int(data['cnn']),
        'location_description' : data['locationdescription'] if 'locationdescription' in data else None,
        'address' : data['address'],
        'blocklot' : data['blocklot'] if 'blocklot' in data else None,
        'block' : data['block'] if 'block' in data else None,
        'lot' : data['lot'] if 'lot' in data else None,
        'permit' : data['permit'],
        'status' : data['status'],
        'food_items' : data['fooditems'] if 'fooditems' in data else None,
        'x' : float(data['x']) if 'x' in data else None,
        'y' : float(data['y']) if 'y' in data else None,
        'latitude' : float(data['latitude']),
        'longitude' : float(data['longitude']),
        'schedule' : data['schedule'],
        'dayshours' : data['dayshours'] if 'dayshours' in data else None,
        'approved' : datetime.strptime(data['approved'], date_format) if 'approved' in data else None,
        'received' : data['received'],
        'prior_permit' : True if data['priorpermit'] == '1' else False,
        'expiration_date' : datetime.strptime(data['expirationdate'], date_format) if 'expirationdate' in data else None
    }
    )



# Connect to the DB and insert data
engine = create_engine('mysql+mysqlconnector://root:root@localhost/food_trucks', connect_args={'connect_timeout': 10000})
df = pd.DataFrame(output_data)
df.set_index('location_id')
df.to_sql('food_trucks', con=engine, if_exists='append', index=False, method='multi')


# In[ ]:




