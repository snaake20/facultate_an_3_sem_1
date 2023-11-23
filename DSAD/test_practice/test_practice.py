import pandas as pd
import numpy as np

df_agricultura = pd.read_csv('Agricultura.csv', index_col=["Siruta", "Localitate"]);

df_populatie_localitati = pd.read_csv('PopulatieLocalitati.csv', index_col=["Siruta", "Localitate"]);

merged_df = pd.merge(df_populatie_localitati, df_agricultura, on=["Siruta", "Localitate"]);

print(merged_df);

# print(merged_df[0]);
print(merged_df.iloc[1]);

coloane = list(merged_df.columns);

merged_df["Cifra afaceri la nivel de localitate"] = merged_df[coloane[4:]].sum(axis=1);

cerinta2 = merged_df[coloane[:2] + ["Cifra afaceri la nivel de localitate"]];

cerinta2.to_csv("Output1.csv", index=False);

merged_df["Cea mai profitabila activitate"] = merged_df[coloane[4:]].idxmax(axis=1);

cerinta3 = merged_df[coloane[:2] + ["Cea mai profitabila activitate"]];
cerinta3.to_csv("Output2.csv", index=False);

# cifra de afaceri pe locuitor

merged_df["Cifra de afaceri pe locuitor"] = merged_df["Cifra afaceri la nivel de localitate"] / merged_df["Populatie"];

cerinta4 = merged_df[coloane[2:4] + ["Cifra de afaceri pe locuitor"]];
cerinta4.to_csv("Output3.csv", index=False);
