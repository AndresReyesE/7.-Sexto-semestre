import cv2 as cv
import numpy as np
import math
import copy
from functools import reduce


# def brightness (img, value):
#     newImg = cv.imread(img, 0)
#     height, width = newImg.shape
#     for i in range (height):
#         for j in range (width):
#             newImg[i][j] += value

#     return newImg

def brightness (img, value):
    newImg = copy.copy(img)
    height, width = newImg.shape
    for i in range (height):
        for j in range (width):
            newImg[i][j] += value

    return newImg


img = cv.imread("/home/reyes/7.-Sexto-semestre/WIP/circle-detection/imagenes/LE070330422002010701T1/TOA/LE070330422002010701T1_b5.png", 0)

newi = brightness(img, 100)

cv.imshow("orgigi", img)
cv.imshow("bb", newi)
cv.waitKey(0)
cv.destroyAllWindows()
