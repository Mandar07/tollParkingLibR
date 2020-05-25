# Toll ParkingLibR

## REST EndPoints :

1. initialize -Parking: initialize toll parking with a given toll parking configuration, the configuration has the number of parking slots divided in three categories

- STANDARD for Standard Car
- EltCar20kw for ELECTRIC CAR with 20KW
- EltCar50kw for  ELECTRIC CAR with 50KW
and a pricing policy. 
The pricing policy consist of a fixed amount and a hourly price.

2. update a PricingPolicy: The pricing policy to update (values must be greater than 0).

3. entry Parking: A parking slot is returned if available 
for the car type :
- STANDARD for Standard Car
- EltCar20kw for ELECTRIC CAR with 20KW
- EltCar50kw for  ELECTRIC CAR with 50KW

   ### Example: /toll-parking/entryparking/car_platenumber/car_type

4. leaveParking: The parking slot is freed and a parking bill is created for the current pricing policy

   ### Example: /toll-parking/exitparking/car_platenumber
