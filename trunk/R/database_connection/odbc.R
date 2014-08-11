## install.packages("RODBC")

library(RODBC)

channel <- odbcConnect("PostgreSQL35W", uid="postgres", pwd="123")
sqlTables(channel)
data <- sqlQuery(channel, "SELECT * FROM test")
class(data)
data
odbcClose(channel)