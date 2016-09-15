# Running Things Locally

```bash
$ docker-compose up
```

To query the dev database:
```bash
$ mysql -u gaudi --host=localhost -p --protocol=tcp --port=3306
```

To query the test database:
```bash
$ mysql -u gaudi --host=localhost -p --protocol=tcp --port=3307
```