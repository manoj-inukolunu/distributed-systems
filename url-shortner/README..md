# Generate 1 billion rows and insert to postgres.

* Generate 10 files each containing 10 million urls , each url in a single line
* Insert into postgres using ``` copy urls (url) from '/Users/manoj/100mil/100million-3.csv' ```

```
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-1.csv'
Executed with code = 100000000
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-0.csv'
Executed with code = 100000000
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-2.csv'
Executed with code = 100000000
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-3.csv'
Executed with code = 100000000
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-7.csv'
Executed with code = 100000000
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-6.csv'
Executed with code = 100000000
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-4.csv'
Executed with code = 100000000
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-5.csv'
Executed with code = 100000000
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-8.csv'
Executed with code = 100000000
Executing sql=copy urls (url) from '/Users/manoj/100mil/100million-9.csv'
Executed with code = 100000000
6499
```

It took 6499 secs around 1.8 hrs to insert this way there maybe faster ways . Query is blazing fast 

```
psql (13.3)
Type "help" for help.

mydb=# \timing on
Timing is on.
mydb=# select * from urls where id = 762354723;
    id     |                                        url
-----------+-----------------------------------------------------------------------------------
 762354723 | https://stackoverflow.com/questions/19167349/c5524d1c-9172-473b-ad38-87e2e3751456
(1 row)

Time: 5.902 ms
mydb=#
```
