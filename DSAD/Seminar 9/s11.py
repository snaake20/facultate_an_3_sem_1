from util import * 
import numpy as np
import pandas as pd
import factor_analyzer as fact
from geopandas import GeoDataFrame

def execute():
  tabel = pd.read_csv("Freelancer.csv", index_col=0);
  nan_replace(tabel);
  nume_variabile = list(tabel)[2:];
  x= tabel[nume_variabile].values;
  
  n,m = x.shape;
  
  # validam modelul => verificam daca var initiale au in spate factori comuni
  # comparam matricea de corelatie cu matricea unitate I
  # H0 (ipoteza nula): nu exista factori comuni, H1: exista factori comuni
  # test Bartlett
  
  test_bartlett = fact.calculate_bartlett_sphericity(x);
  print("test Bartlett", test_bartlett);
  
  if test_bartlett[1] > 0.001:
    print("nu exista factori comuni");
    exit(0)
    
  # calcul index Kaiser-Meyer-Olkin
  
  kmo = fact.calculate_kmo(x);
  print("KMO", kmo);
  
  tabel_kmo = pd.DataFrame(data={"Index KMO": np.append(kmo[0], kmo[1])}, index=nume_variabile + ["Total"])
  corelograma(tabel_kmo, val_min=0, val_max=1, titlu="Index KMO");
  if (all(tabel_kmo["Index KMO"] < 0.6)):
    print("Nu exista factori comuni")
    exit(0)
  
  # construire model
  rotatie = None;
  model_fact = fact.FactorAnalyzer(n_factors=m, rotation=rotatie)
  model_fact.fit(x);
  
  # interpretarea rezultatelor
  alpha = model_fact.get_factor_variance()[0];
  etichete_factori = ["F" + str(i+1) for i in range(m)];
  
  tabel_varianta = tabelare_varianta(alpha, etichete_factori);
  tabel_varianta.to_csv("Varianta_Factorilor.csv");
  
  # corelatii variabile - factori
  ponderi = model_fact.loadings_
  tabel_ponderi = tabelare_matrice(ponderi, nume_variabile, etichete_factori, "PonderiFactori.csv");
  
  # scoruri factoriale
  scoruri = model_fact.transform(x);
  tabel_scoruri = tabelare_matrice(scoruri, tabel.index, etichete_factori, "ScoruriFactoriale.csv");
  
  plot_componente(tabel_scoruri, "F1", "F2", "Plot scoruri factoriale");
  
  # calcul comunalitati
  comunalitati = model_fact.get_communalities();
  tabel_comunalitati = pd.DataFrame(data={"Comunalitati": comunalitati}, index=nume_variabile);
  corelograma(tabel_comunalitati, 0, 1, "Comunalitati");
  
  # harta
  shape = GeoDataFrame()
  # inca o linie aici
  show()
  

if __name__ == '__main__':
  execute()