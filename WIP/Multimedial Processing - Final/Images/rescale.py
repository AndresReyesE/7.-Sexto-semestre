import cv2
import os

names = []

p = '/home/reyes/7.-Sexto-semestre/WIP/Multimedial Processing - Final/Images/DSC_5363/'
# path = p + 'Positivos/'
path = p + 'Negativos/'
def read():
    result = []
    #nombre de la carpeta a leer
    for r,d,f in os.walk(path):
        for name in f:
            #cambiar nombre aquí también
            result.append(cv2.imread(path+name, 0))
            names.append(name)
    return result

def convert(focas):
    result = []
    for foca in focas:
        # cambiar escala
        result.append(cv2.resize(foca,(50,50)))
    return result

def save(focas):
    for foca,name in zip(focas,names):
        # nombre de la carpeta dónde guardar las nuevas imágenes
        cv2.imwrite(path + name, foca)

res = read()
# res = convert(res)
save(res)
