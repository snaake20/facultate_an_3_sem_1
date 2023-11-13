import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy.stats import shapiro, kstest, chisquare, chi2, norm
import seaborn as sb

def execute():
    tabel = pd.read_csv("Teritorial.csv", index_col=0);
    nume_judete = list(tabel.index);
    nume_variabile = list(tabel.columns);
    variabile_numerice = nume_variabile[3:];
    x = tabel[variabile_numerice].values;
    n, m = x.shape;
    
    # sanitizarea datelor
    
    is_nan = np.isnan(x);
    
    pozitii = np.where(is_nan)
    
    x[pozitii] = np.nanmean(x[:,pozitii[1]], axis=0);
    
    # covarianta si coef de corelatie
    
    cov = np.cov(x, rowvar=False);
    salvare_matrice(cov, variabile_numerice, variabile_numerice, 'cov.csv');
    r = np.corrcoef(x, rowvar=False)
    print(r)
    salvare_matrice(r, variabile_numerice, variabile_numerice, 'r.csv');
    
    # corelograma
    corelograma(r)
    
    x_std = standardizare_centrare(x);
    salvare_matrice(x_std, nume_judete, variabile_numerice, "x_std.csv");
    x_c = standardizare_centrare(x, False)
    salvare_matrice(x_c, nume_judete, variabile_numerice, "x_c.csv");
    
    # teste = teste_concordanta(x);
    # nume_teste = ["s_shapiro", "p_shapiro", "s_ks", "p_ks", "s_chi2", "p_chi2"];
    
    # salvare_matrice(teste, variabile_numerice, nume_teste, "teste.csv");
    
    # vector_variabile = np.array(variabile_numerice);
    # decizie_shapiro = tabel[:,1] > 0.05;
    # print("variabile care urmeaza o distributie normala conform shapiro:", vector_variabile[decizie_shapiro])
    
    # corelograma(r);
    
def corelograma(corr, dec=2, titlu="Corelograma", valMin=-1, valMax=1):
    plt.figure("Grafic corelograma", figsize=(12,8));
    plt.title(titlu, fontsize=12, color="royalblue", verticalalignment="bottom");
    sb.heatmap(data=np.round(corr, decimals=dec), vmin=valMin, vmax=valMax, annot=True, cmap="cool")
    plt.show();
    
def teste_concordanta(x):
    assert isinstance(x, np.ndarray);
    _,m = x.shape;
    teste = np.empty((m,6));
    
    for i in range(m):
        v = x[:,i];
        teste[i, 0:2] = shapiro(v);
        teste[i, 2:4] = kstest(v, 'norm');
        teste[i, 4:6] = chisquare(v);
        
    return teste;
        

def standardizare_centrare(x, scalare=True):
    # standardizare = centrare (Xi-Xmediu) + scalare (Xi-Xmediu/Sigmax)
    medii_coloane = np.mean(x, axis=0);
    x_ = x-medii_coloane;
    if (scalare):
        abateri_standard_coloane = np.std(x, axis=0);
        x_ = x_/abateri_standard_coloane; # valori standardizate
    return x_;

def salvare_matrice(x, nume_randuri=None, nume_coloane=None, nume_fisier="out.csv"):
    # deschidere fisier
    f = open(nume_fisier, "w");
    # afisare primul rand
    if nume_coloane is not None:
        if nume_randuri is not None:
            f.write(',');
        f.write(','.join(nume_coloane) + '\n');
    
    n,_ = x.shape;
    for i in range(n):
        if (nume_randuri is not None):
            f.write(nume_randuri[i] + ',');
        f.write(",".join(str(v) for v in x[i, :])+'\n')
    
    f.close();

if __name__ == "__main__":
    execute();