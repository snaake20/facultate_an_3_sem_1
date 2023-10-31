# clase si instante
# variabile ale clasei si ale instantelor
# metode: instance, class, static
# dunder methods
# mostenire
# getters, setters

def func():
    a = 2
    print(a)
    if False:
        print('inside if')
    print("This is a function")

    return 2,3.5,True,'un string',None

# a,b,c,_,e = func()
# print(a,b,c,e)

class Persoana:
    no_of_pers = 0
    bonus = 1.1

    # in python, daca o var sau method incep cu 1 singur _, se presupune ca sunt protected
    _cnp = None
    # in python, daca o var sau method incep cu 2 , se presupune ca sunt private
    __numar_tel = None

    def __init__(self, prenume, nume, salariu, cnp, tel):
        self.prenume = prenume
        self.nume = nume
        self.salariu = salariu
        self._cnp = cnp
        self.__numar_tel = tel
        Persoana.no_of_pers += 1
        
    def __str__(self) -> str:
        return "{} {} - {}".format(self.prenume, self.nume, self.salariu)
    
    def __lt__(self, other):
        return self.salariu < other.salariu
    
    @property
    def numar_tel(self):
        return self.__numar_tel
      
    @numar_tel.setter
    def numar_tel(self, value):
        self.__numar_tel = value
        
    @classmethod
    def set_bonus(cls, value):
        cls.bonus = value
        
    @staticmethod
    def este_weekend(zi):
        return zi.weekday() in [5,6]

    def get_email(self):
        return self.prenume + "." + self.nume + "@email.com"

    def fullname(self):
        return "{} {}".format(self.prenume, self.nume)

    def apply_bonus(self):
        self.salariu *= Persoana.bonus

    def get_numar_persoane(self):
        return Persoana.no_of_pers

class Gamer(Persoana):
    def __init__(self, prenume, nume, salariu, cnp, tel, rank, games):
        super().__init__(prenume, nume, salariu, cnp, tel)
        self.rank = rank
        self.games = games

    def get_games(self):
        s = ""
        for game in self.games:
            s += game + " "
        return s.strip()

    def say(self):
        print(self._cnp)

g1 = Gamer("Andrea", "Ionescu", 4000, "12347", "021 123 123 123", 1, ["CS", "LOL"])

p1 = Persoana("Andrei", "Popescu", 4000, "021 123 123 345", "12345")
p2 = Persoana("Maria", "Toma", 4000,"021 123 123 567",  "12346")

print(p1.salariu, p2.salariu)
p1.bonus = 2
p1.adresa = "Calea Dorobanti"
p1.apply_bonus()
p2.apply_bonus()
print(p1.salariu, p2.salariu, p1.adresa)

# g1.get_email() este translat de catre interpretor in Persoana.get_email(g1) =>
# g1 e trimis pe post de self in semnatura lui get_email()
print(g1.get_email(), g1.fullname(), g1.salariu, g1.get_games())

# print(g1._Persoana__numar_tel)
# genereaza exceptie, intrucat __numar_tel e variabila privata
# print(g1.__numar_tel)

# print(p1.__dict__)
# print(dir(p1)) # echivalent cu: print(p1.__dir__())
# print(help(g1))