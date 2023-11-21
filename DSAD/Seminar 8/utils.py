import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

def nan_replace(t):
  nume_variabile = list(t.columns);
  for each in nume_variabile:
    if any(t[each].isna()):
      if pd.api.types.is_numeric_dtype(t[each]):
        media = t[each].mean();
        t[each].fillna(media, inplace=True)
      else:
        modul = t[each].mode()[0];
        t[each].fillna(modul, inplace=True);

def tabelare_matrice(c, observatii, variabile, nume_fisier):
  pass

def plot_componente(componente_tabelar, axaX, axaY):
  pass

def corelograma(t):
  pass

def plot_corelatii(t, C1, C2):
  pass

def afisare():
  plt.show();