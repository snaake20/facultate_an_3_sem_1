import numpy as np
import matplotlib.pylab as plt
import seaborn as sb

def scatter2d(t, k1=0, k2=1, var_x="X", var_y="Y", etichete=None, titlu="Scatter plot"):
  fig = plt.figure(figsize=(12,8));
  ax = fig.add_subplot(1,1,1);
  ax.set_title(titlu, fontdict={'fontsize': 12, 'color': 'orange'});
  ax.set_xlabel(var_x, fontdict={'fontsize': 12, 'color': 'black'});
  ax.set_ylabel(var_y, fontdict={'fontsize': 12, 'color': 'black'});
  
  ax.scatter(t[:,k1], t[:,k2], color='royalblue');
  if etichete is not None:
    for i in range(len(etichete)):
      ax.text(t[i,k1], t[i,k2], etichete[i]);
      
  plt.show();

def boxplot(t, variabile, titlu="Boxplot", by1=None, by2=None):
  fig = plt.figure(figsize=(12,8));
  ax = fig.add_subplot(1,1,1);
  
  if by1 is None:
    sb.boxplot(data=t[variabile], ax=ax);
  else:
    if by2 is None:
      titlu = titlu + ". Grupare dupa " + by1;
    else:
      titlu = titlu + ". Grupare dupa " + by1 + ' si ' + by2;
    
    sb.boxplot(x=variabile[0], y=by1, hue=by2, data=t, ax=ax);
  
  ax.set_title(titlu, fontdict={'fontsize': 12, 'color': 'darkorange'});
  
  plt.show();
  
def radar(t, nume_indicatori, nume_instante):
  fig = plt.figure(figsize=(12,8));
  ax = fig.add_subplot(1,1,1);
  
  n = len(nume_instante);
  m = len(nume_indicatori);
  
  unghiuri = [i*2*np.pi/m for i in range(m)];
  unghiuri += unghiuri[:1];
  
  ax.set_title("Radar");
  ax.set_xticks(unghiuri[:-1]);
  ax.set_xticklabels(nume_indicatori);
  
  for i in range(n):
    valori = list(t[i,:]);
    valori += valori[:1];
    
    ax.plot(unghiuri, valori);
    ax.fill(unghiuri, valori, label=nume_instante[i], alpha=0.5);
    
  ax.legend()
  
  plt.show();
  
def lines(t, variabile, titlu="Lines"):
  fig = plt.figure(figsize=(12,8));
  ax = fig.add_subplot(1,1,1);
  
  ax.set_title(titlu);
  for each in variabile:
    ax.plot(t[each], label=each);
  ax.legend();
  plt.show();