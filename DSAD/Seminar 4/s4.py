import numpy as np;
import time;
# Folosim np in detrimentul listelor din python deoarece este mai rapid, mai eficient si mai usor de folosit
# 1. In numpy informatia dintr-un ndarray este stocata in forma continua
#     liste python |_x_|___|_y_|___|_z_|
#     ndarray      |_x_|_y_|_z_|___|___|
# 2. Intr-un ndarray avem acelasi tip de date, nu putem amesteca tipuri diferite


a = np.array([1,2,3]);
# print(a);
# print(type(a));
# print(type(a[0]));

a = np.array([1,2,3], dtype=np.int16); # int16 = int 16 biti == 'int16'/ 'short' in C
# print(a);
# print(type(a));
# print(type(a[0]));

b = np.array([[5.4, 3.2], [7.4, 1.6], [6.6, 2.2]]);
# print(b);
# print(b[0]);
# print(b[0][0]);
# print(type(b));
# print(type(b[0]));
# print(type(b[0][0]));


print("Proprietati");
print("Dim A: ", a.ndim);
print("Dim B: ", b.ndim);


print("Data Type A: ", a.dtype);
print("Data type B: ", b.dtype);


print("Item size A: ", a.itemsize);
print("Item size B: ", b.itemsize);


print("Size A: ", a.size);
print("Size B: ", b.size);


print("Total size A: ", a.size * a.itemsize);
print("Total size B: ", b.size * b.itemsize);


print("Number of bytes A: ", a.nbytes);
print("Number of bytes B: ", b.nbytes);


print("Shape A: ", a.shape);
print("Shape B: ", b.shape);


print('Acces elemente');
# slicing = [startIndex : endIndex : step]

a = np.array([[1,2,3,4,5], [6,7,8,9,10]]);

# print(a[1,2], a[1][2]);
# a[1,2] = 20;
# print(a[1,2]);

# print(a[0, 1:-1])
# print(a[0, 1:-1:2])
# print(a[0, ::2])
# print(a[0, :])
# print(a[:, 3])
# print(a[:-1, 3])


print("ndarray predefinite");
z = np.zeros(2);
print(z);

o = np.ones((2,2,3), dtype='int16');
print(o);

f = np.full((2,2), 99, dtype='float32');
print(f);

r = np.random.rand(2,2);
print(r);

ri = np.random.randint(-99, -33, size=(3,3));
print(ri);

i = np.identity(4);
print(i);

a = np.array([1, 2, 3]);
# tile = np.tile(a, 3);
# print(tile);

# rep = np.repeat(a, 3, axis=0);
# print(rep);

print("Copy related issues");
b = a;
c = a.copy();
b[0] = 30;
c[0] = 16;
print(a, b, c);


print("De ce numpy?");
a = [1,2,3,4,5];
b = [10,11,12,13,14];
c = []; # [None] * len(a) for C
for i in range(len(a)):
    # c[i] = a[i] * b[i]; # C way
    c.append(a[i] * b[i]); # python way
    
start_time = time.time();
a = [i for i in range(10000000)];
a_patrat = [i ** 2 for i in a];
duration = time.time() - start_time;
print("Python: ", duration);

start_time = time.time();
a = np.arange(10000000);
a_patrat = a ** 2;
duration = time.time() - start_time;
print("NP: ", duration);

# broadcasting
c = np.array([[1,2,3], [4,5,6]])
print(c * 2)

# element wise vs matrix
m = np.array([[1, 2], [3, 4]])
n = np.array([[2, 2], [2, 2]])
# element wise
print(m + n)
print(m - n)
print(m * n)
print(m / n)
# matrix wise
print(m @ n)

y = np.array([[1, 2], [4, 5], [7, 8]])
z = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]])
try:
  print(y * z)
except:
  print(y @ z)
