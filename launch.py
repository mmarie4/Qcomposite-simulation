import os
import sys

ring_size = [26, 41, 51, 61, 68] #Pour avoir Pc = 0.5 pour les differents Q
list = (1, 2, 3, 4, 5)
for j in list:
    print("========================= Resilience for Q = " + str(j) + " and ring_size = " + str(ring_size[j-1]) + " - Pc = 0.5 ===========================")
    # number of node attacked
    for i in range(101):
        command = "java Qcomposite " + str(ring_size[j-1]) + " " + str(j) + " " + str(i)
        os.system(command)
