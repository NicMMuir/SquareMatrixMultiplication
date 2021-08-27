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

plt.plot(label,x)
plt.plot(label,y)
plt.plot(label,z)

plt.title('Interesting Graph\nCheck it out')
plt.legend()
plt.show()
