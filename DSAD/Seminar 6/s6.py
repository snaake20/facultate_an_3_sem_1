import sqlite3
from grafice import *
from pandas import DataFrame, read_sql_query
from pandas.api.types import is_numeric_dtype

def execute():
  t = citire_tabela_din_bd('db5.db', 'Teritorial', 'Indicativ');
  nan_replace(t);
  variabile = list(t);
  variabile_numerice = variabile[3:];
  
  # Grafic linii
  lines(t, ['RataSom', 'RataAbScolar']);
  lines(t, ['EmigrantiDefinitiv', 'EmigrantiTemporar']);
  lines(t, ['PIB', 'Export', 'ExecBVenituri']);
  
  # Grafic scatter
  k1 = variabile_numerice.index('RataSom');
  k2 = variabile_numerice.index('PIB');
  scatter2d(t[variabile_numerice].values, k1, k2, var_x=variabile_numerice[k1], var_y=variabile_numerice[k2], etichete=t.index);
  
  # Grafic boxplot
  boxplot(t, ['RataSom', 'RataAbScolar']);
  boxplot(t, ['PIB'], by1='Regiunea');
  boxplot(t, ['PIB'], by1='Regiunea', by2='Macroregiunea');
  
  #grafic radar
  indicatori = ['PIB', 'Export', 'ExecBVenituri'];
  # radar(t[indicatori].values)
  # radar(t[indicatori].values.T)
  
def nan_replace(t):
  nume_variabile = list(t.columns);
  for each in nume_variabile:
    if any(t[each].isna()):
      if is_numeric_dtype(t[each]):
        media = t[each].mean();
        t[each].fillna(media, inplace=True)
      else:
        modul = t[each].mode()[0];
        t[each].fillna(modul, inplace=True);
  
def citire_tabela_din_bd(db_name, table_name, index):
  connection = sqlite3.connect(db_name);
  t = read_sql_query(f"select * from {table_name}", connection, index_col=index);
  connection.close();
  return t;


if __name__ == '__main__':
  execute();