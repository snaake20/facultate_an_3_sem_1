import numpy as np
import pandas as pd
from sklearn.decomposition import PCA
from utils import *

t = pd.read_csv("Freelancer.csv", index_col=1);
nan_replace(t);

# list(t) e echivalent cu list(t.columns)
# list(t) => intoarce ["Country", "Continent" si restul variabilelor]
# coloana "CountryCode" e rezervata pentru t.index
variable_observate = list(t)[2:];

# standardizare set de date (echivalent cu a apela metoda standardizare_centrare() din seminariile anterioare)
x = (t[variable_observate] - np.mean(t[variable_observate], axis=0)) / np.std(t[variable_observate], axis=0);

n, m = x.shape;

model_acp = PCA();
model_acp.fit(x);

# x = setul de date observate
# alpha = valorile proprii
# a = vectorii proprii
# c = componente principale rezultate in urma ACP: c = x @ a

alpha = model_acp.explained_variance_;
a = model_acp.components_;
c = model_acp.transform(x);
print("alpha", alpha);
print("a", a);
print("componente principale", c);

etichete = ["C" + str(i+1) for i in range(len(c))];
componente_tabelar = tabelare_matrice(c, t.index, etichete, "componente.csv");
plot_componente(componente_tabelar, "C1", "C2");

# Criterii de identificare a numarului de componente semnificative
# Kaiser
where = np.where(alpha > 1);
nr_comp_kaiser = len(where[0]);
print("Numar componente principale semnificative cf crit. Kaiser: ", nr_comp_kaiser);

# Procent de acoperire
ponderi = np.cumsum(alpha/ sum(alpha));
where = np.where(ponderi > 0.8);
nr_comp_procent = where[0][0] + 1;
print("Numar componente principale semnificative cf crit. Procent acoperire: ", nr_comp_procent);

# Cattell
eps = alpha[:(m-1)] - alpha[1:];
sigma = eps[:(m-2)] - eps [1:];
negative = sigma < 0;

if any(negative):
  where = np.where(negative);
  nr_comp_cattell = where[0][0] + 1;
else:
  nr_comp_cattell = 0;
  
print("Numar componente principale semnificative cf crit. Cattell: ", nr_comp_cattell);

# Calcul corelatii intre variabilele observate si componentele principale
corr = np.corrcoef(x, c, rowvar=False);
print(corr);
print (x.shape, corr.shape);

r_x_c = corr[:m, :m];
r_x_c_tabelar = tabelare_matrice(r_x_c, variable_observate, etichete, "corelatii_factoriale.csv");

corelograma(r_x_c_tabelar);
plot_corelatii(r_x_c_tabelar, "C1", "C2");

# Calcul cosinusuri
componente_patrat = c*c;
cosin = np.transpose(componente_patrat.T / np.sum(componente_patrat, axis = 1));
cosin_tabelar = tabelare_matrice(cosin, t.index, etichete, "cosin.csv");

# Calcul comunalitati
r_x_c_patrat = r_x_c * r_x_c;
comunalitati = np.cumsum(r_x_c_patrat, axis=1);
comunalitati_tabelar = tabelare_matrice(comunalitati, variable_observate, etichete, "comunalitati.csv");

afisare();