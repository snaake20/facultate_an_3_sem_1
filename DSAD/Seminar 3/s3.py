# liste, tupluri, dictionare
# lambda, map, filter, reduce
# list comprehensions
# operatii pe fisiere
# sortari

fisier = open("date.csv", "r");
print(type(fisier));
randuri = fisier.readlines();
for rand in randuri:
  rand = rand[:-1] + 'A' + rand[-1]
  print(rand[:-1]);
  
lista = [1, 2, 3, 4, 5, 6, 7, 8, 9];
print(lista);
tuplu = (1,1,343,23); # tuplurile sunt imutabile, colectie (lista) immutable

dictionar = {
  "a": 1,
  "b": 2,
  "c": 3
}
print(type(dictionar), dictionar, dictionar.keys(), dictionar.values(), dictionar.items())

lista_tupluri=[]
for idx in range(len(randuri)):
  cuvinte = randuri[idx][:-1].split(",");
  lista_tupluri.append(tuple(cuvinte));
  
print(lista_tupluri);

lista_comprensiva = [tuple(rand[:-1].split(",")) for rand in randuri];


numere = [nr for nr in range(20) if nr % 2 == 0];

fa = lambda *x: sum(x);
print(fa(1,2,3,4,5,6,7,8,9,10));



# map

secventa = (1,2,3,4);
rez = list(map(lambda x: x * x, secventa));
print(rez);


a = [1,2,3];
b = [10, 20, 30];

rez = list(map(lambda x, y: x * y, a, b)); 
print(rez);

# filter
secventa = ['q', 'w', 'e', 'a', 't'];

vocale = list(filter(lambda x: x in ['a', 'e', 'i', 'o', 'u'], secventa));
print(vocale);

