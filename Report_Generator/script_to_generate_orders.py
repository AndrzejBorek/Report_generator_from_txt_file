from math import prod
import random as r
from secrets import randbits
from tkinter.font import names

open("Orders.txt", "w").close()

client_ids = [1, 2, 3, 4, 5,
              6, 7, 8, 9, 10
              ]

products = {
    1: 10.00,
    2: 20.05,
    3: 30.99,
    4: 40.99,
    5: 81.05,
    6: 21.99,
    7: 43.99,
    8: 10.00,
    9: 1.99
}

with open("Orders.txt", "w") as f:
    for i in range(5000):
        client_choice = r.choice(client_ids)
        product_choice = r.randint(1, 9)
        product_price = products[product_choice]
        f.write(str(i + 1) + " " + str(client_choice) + " " +
                str(product_choice) + " " + str(product_price) + " " + str(r.randint(0, 10) / 100))

        f.write("\n")
