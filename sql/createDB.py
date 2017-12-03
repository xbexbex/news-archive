import os
import sys

import sqlite3 

sql_commands = 	{
				"LUKUVINKKI":open("lukuvinkki.sql", 'r').read(),
				"KIRJA":open("kirja.sql", 'r').read(), 
				"TAG":open("tag.sql", 'r').read()
				}

if os.path.isfile("lukuvinkit.db"):
	print("Database already exists!")
	sys.exit()

db1 = sqlite3.connect("lukuvinkit.db")
cursor = db1.cursor()

try:
	cursor.execute(sql_commands["LUKUVINKKI"])
	cursor.execute(sql_commands["KIRJA"])
	cursor.execute(sql_commands["TAG"])
except sqlite3.Error as er:
	print ('sqlite error: {0}'.format(er.message))

db1.commit()
db1.close()

print("Database created!")
