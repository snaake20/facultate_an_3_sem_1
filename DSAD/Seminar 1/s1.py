a:int = 1
b = 2
print(a+b)
print(id(a)) # id - functie care intoarce adresa de memorie a unei variabile

c = input("C: ")
d = input('D: ')
print(type(c))
print(type(d))
print(c+d)

c = int(c)
print(type(c))
print(c + int(d))

try:
    print(c+d)
except TypeError or Exception as e:
    print(e)

d = int(d)
if b < a:
    print('a <b')
elif b < c and c < d:
    print('b < c and c < d')
elif a < b or b <c:
    print('a < b or b <c')
else:
    print('idk what happened')