import matplotlib.pyplot as plt
import csv

x = []
y = []
z = []

label = []
with open('Matrix.csv','r') as file:
    line = csv.reader(file, delimiter=',')
    count = 0
    for elements in line:
        if count == 0:
            count+=1
            continue
        label.append(elements[0])
        x.append(float(elements[1]))
        y.append(float(elements[2]))
        z.append(float(elements[3]))

plt.plot(label,x ,label='Matrix Multiply')
plt.plot(label,y, label='Matrix Multiply Reccursion')
plt.plot(label,z, label='Strassens')

plt.title('Size of Matrix vs Time\nJava implementation')
plt.legend()
plt.show()
