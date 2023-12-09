import numpy as np
import pandas as pd
import matplotlib.pylab as plt
import seaborn as sb

def nan_replace(t):
  nume_variabile = list(t.columns);
  
  for var in nume_variabile:
    if (any(t[var].isna())):
        if (pd.api.types.is_numeric_dtype(t[var])):
          t[var].fillna(t[var].mean(), inplace=True);
        else:
          t[var].fillna(t[var].mode()[0], inplace=True);
      
      
def tabelare_matrice(x, nume_linii=None, nume_coloane=None, nume_fisier="out.csv"):
  t = pd.DataFrame(x, nume_linii, nume_coloane);
  t.to_csv(nume_fisier);
  return t;

def tabelare_varianta(alpha, etichete):
  procente = alpha * 100/ sum(alpha);
  tabel_varianta = pd.DataFrame(
    data={
      "Varianta": alpha,
      "Varianta cumulata": np.cumsum(alpha),
      "Procent": procente,
      "Procent cumulat": np.cumsum(procente)
      }, index=etichete);
  return tabel_varianta;

def corelograma(x, val_min=-1, val_max=1, titlu="Corelatii factoriale"):
  fig = plt.figure(titlu, figsize=(9,9));
  ax = fig.add_subplot(1,1,1);
  
  ax_ = sb.heatmap(data=x, vmin=val_min, vmax=val_max, annot=True, cmap="RdYlBu", ax=ax);
  
  ax_.set_xticklabels(x.columns, ha='right', rotation=30);

def plot_componente(x, var_x, var_y, titlu="Plot componente"):
  fig = plt.figure(titlu, figsize=(9,9));
  ax = fig.add_subplot(1,1,1);
  
  ax.set_xlabel(var_x, fontdict={"fontsize": 12, "color": "b"});
  ax.set_ylabel(var_y, fontdict={"fontsize": 12, "color": "b"});
  
  ax.scatter(x[var_x], v[var_y], color='r');
  for i in range(len(x)):
    ax.text(x[var_x].iloc[i], x[var_y].iloc[i], x.index[i])

def harta(shape, x, camp_legatura, nume_instante, titlu="Harta scoruri"):
  n,m = x.shape;
  tabel = pd.DataFrame(data={"Coduri": nume_instante});
  for i in range(m):
    tabel["v"+str(i+1)] = x[:,i]
  
  shape_ = pd.merge(shape, tabel, left_on=camp_legatura, right_on="Coduri");
  
  for i in range(m):
    fig = plt.figure(titlu + " - " + str(i+1), figsize=(9,9));
    ax = fig.add_subplot(1,1,1);
    
    shape_.plot("V"+ str(i+1), cmap="Reds", ax=ax, legend=True);

def show():
  plt.show();
