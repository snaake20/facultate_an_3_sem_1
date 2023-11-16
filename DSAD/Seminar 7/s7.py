import numpy as np
import pandas as pd


def execute():
    tabel_etnii = pd.read_csv("T:\\dsad\\1094\\s7\\Ethnicity.csv", index_col=0)
    nan_replace(tabel_etnii)

    variabile_etnii = list(tabel_etnii.columns)[1:]

    # calcul populatie pe etnii la nivel de judet
    localitati = pd.read_excel("T:\\dsad\\1094\\s7\\CoduriRomania.xlsx", index_col=0)
    t1 = tabel_etnii.merge(right=localitati, right_index=True, left_index=True)
    # print(t1)

    g1 = t1[variabile_etnii + ["County"]].groupby(by="County").agg(sum)
    g1.to_csv("T:\\dsad\\1094\\s7\\EtniiJudete.csv")

    # calcul populatie pe etnii la nivel de regiune
    judete = pd.read_excel("T:\\dsad\\1094\\s7\\CoduriRomania.xlsx", index_col=0, sheet_name="Judete")
    t2 = g1.merge(right=judete, right_index=True, left_index=True)

    g2 = t2[variabile_etnii + ["Regiune"]].groupby(by="Regiune").agg(sum)
    g2.to_csv("T:\\dsad\\1094\\s7\\EtniiRegiuni.csv")

    # calcul populatie pe etnii la nivel de macroregiune
    regiuni = pd.read_excel("T:\\dsad\\1094\\s7\\CoduriRomania.xlsx", index_col=0, sheet_name=2)
    t3 = g2.merge(right=regiuni, right_index=True, left_index=True)

    g3 = t3[variabile_etnii + ["MacroRegiune"]].groupby(by="MacroRegiune").agg(sum)
    g3.to_csv("T:\\dsad\\1094\\s7\\EtniiMacroRegiuni.csv")

    # indici de diversitate la nivel de localitate
    div_loc = tabel_etnii.apply(func=diversitate, axis=1, denumire_coloana="Localitate")
    div_loc.to_csv("T:\\dsad\\1094\\s7\\DiversitateLocalitati.csv")

    # indici de diversitate la nivel de judet
    div_judet = g1.apply(func=diversitate, axis=1)
    div_judet.to_csv("T:\\dsad\\1094\\s7\\DiversitateJudet.csv")

    # indici de diversitate la nivel de regiune
    div_regiune = g2.apply(func=diversitate, axis=1)
    div_regiune.to_csv("T:\\dsad\\1094\\s7\\DiversitateRegiune.csv")


def diversitate(t, denumire_coloana=None):
    if denumire_coloana is not None:
        x = np.array(t.iloc[1:], dtype=float)
    else:
        x = np.array(t.values)

    suma = np.sum(x)
    proportii = x / suma

    idx = proportii == 0
    proportii[idx] = 1

    # definire indice de diversitate Shannon
    shannon = -np.sum(proportii * np.log(proportii))

    # definire indici de diversitate Simpson si Inverse Simpson
    proportii_ = x / suma
    d = np.sum(proportii_ * proportii_)
    simpson = 1 - d
    inverse_simpson = 1 / d

    if denumire_coloana is not None:
        serie_diversitate = pd.Series(data=[t.iloc[0], shannon, simpson, inverse_simpson],
                                      index=[denumire_coloana, "Shannon", "Simpson", "Inverse Simpson"])
    else:
        serie_diversitate = pd.Series(data=[shannon, simpson, inverse_simpson],
                                      index=["Shannon", "Simpson", "Inverse Simpson"])

    return serie_diversitate


def nan_replace(t):
    nume_variabile = list(t.columns)
    for each in nume_variabile:
        if any(t[each].isna()):
            if pd.api.types.is_numeric_dtype(t[each]):
                media = t[each].mean()
                t[each].fillna(media, inplace=True)
            else:
                modul = t[each].mode()[0]
                t[each].fillna(modul, inplace=True)


if __name__ == "__main__":
    execute()
