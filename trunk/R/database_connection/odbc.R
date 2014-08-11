## 1. Install ODBC for reqiured database
## 2. Add users DSN (see http://stackoverflow.com/questions/6796252/setting-up-postgresql-odbc-on-windows) 

## install.packages("RODBC")

library(RODBC)

channel <- odbcConnect("PostgreSQL35W", uid="postgres", pwd="123")
sqlTables(channel)
data <- sqlQuery(channel, "SELECT * FROM test")
class(data)
data
odbcClose(channel)